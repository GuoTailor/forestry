package org.gyh.forestry.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.DelayQueue;

/**
 * create by GYH on 2024/5/24
 */
@Slf4j
@Component
public class MyHandler extends TextWebSocketHandler implements InitializingBean, DisposableBean {
    private final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();
    private final DelayQueue<RetryEntity> retryQueue = new DelayQueue<>();
    // 重试消息 毫秒
    private final static long delayTime = 1000L;
    // 最大重试次数
    private final static int maxRetries = 3;
    
    @Autowired
    private DispatcherServlet servlet;
    @Autowired
    private ObjectMapper json;
    
    private Thread retryThread;

    //成功连接时
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        // 设置最大报文大小，如果报文过大则会报错的,可以将限制调大。
        // 会覆盖config中的配置。
        session.setBinaryMessageSizeLimit(8 * 1024);
        session.setTextMessageSizeLimit(8 * 1024);
        webSocketSessionMap.put(session.getId(), new ConcurrentWebSocketSessionDecorator(session, 10_000, Integer.MAX_VALUE));
        log.info("WebSocket连接已建立，当前连接数: {}", webSocketSessionMap.size());
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken) session.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(principal);
        ServiceRequestInfo serviceRequestInfo = json.readValue(message.getPayload(), ServiceRequestInfo.class);
        if (serviceRequestInfo.getOrder().equals("/ping")) {
            return;
        }
        if (serviceRequestInfo.getOrder().equals("/ok")) {
            retryQueue.removeIf(it -> it.req == serviceRequestInfo.getReq());
            return;
        }
        
        log.info("处理WebSocket消息: {}", message.getPayload());
        SocketServletRequest socketServletRequest = new SocketServletRequest(HttpMethod.POST.name(), serviceRequestInfo.getOrder());
        socketServletRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
        socketServletRequest.setContent(json.writeValueAsBytes(serviceRequestInfo.getBody()));
        socketServletRequest.setUserPrincipal(session.getPrincipal());
        InetSocketAddress remoteAddress = session.getRemoteAddress();
        if (remoteAddress != null) {
            socketServletRequest.setRemoteAddr(remoteAddress.getAddress().getHostAddress());
            socketServletRequest.setRemoteHost(remoteAddress.getHostName());
            socketServletRequest.setRemotePort(remoteAddress.getPort());
        }
        InetSocketAddress localAddress = session.getLocalAddress();
        if (localAddress != null) {
            socketServletRequest.setLocalAddr(localAddress.getAddress().getHostAddress());
            socketServletRequest.setLocalPort(localAddress.getPort());
            socketServletRequest.setLocalName(localAddress.getHostName());
            socketServletRequest.setServerPort(localAddress.getPort());
        }

        SocketServletResponse socketServletResponse = new SocketServletResponse();
        ServiceResponseInfo serviceResponseInfo = new ServiceResponseInfo();
        serviceResponseInfo.setOrder(0);
        serviceResponseInfo.setReq(serviceRequestInfo.getReq());
        try {
            servlet.service(socketServletRequest, socketServletResponse);
            serviceResponseInfo.setBody(socketServletResponse.getContentAsString());
        } catch (Exception e) {
            log.error("处理WebSocket请求异常: {}", serviceRequestInfo.getOrder(), e);
            serviceResponseInfo.setBody(e.getMessage());
        }
        byte[] bytes;
        if (serviceResponseInfo.getBody() instanceof String body && StringUtils.hasLength(body)) {
            serviceResponseInfo.setBody(null);
            String s = json.writeValueAsString(serviceResponseInfo);
            bytes = s.replace("\"body\":null", "\"body\":" + body).getBytes();
        } else {
            bytes = json.writeValueAsBytes(serviceResponseInfo);
        }
        
        WebSocketSession decoratedSession = webSocketSessionMap.get(session.getId());
        if (decoratedSession != null && decoratedSession.isOpen()) {
            TextMessage textMessage = new TextMessage(bytes);
            RetryEntity entity = new RetryEntity(delayTime, serviceRequestInfo.getReq(), maxRetries, decoratedSession, textMessage);
            decoratedSession.sendMessage(textMessage);
            retryQueue.add(entity);
            log.debug("添加重试消息: req={}", serviceRequestInfo.getReq());
        }
    }

    //报错时
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        log.warn("WebSocket传输错误: sessionId={}", session.getId(), exception);
        webSocketSessionMap.remove(session.getId());
        if (session.isOpen()) {
            try {
                session.close(CloseStatus.SERVER_ERROR);
            } catch (Exception ex) {
                log.error("关闭WebSocket会话异常", ex);
            }
        }
    }

    //连接关闭时
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessionMap.remove(session.getId());
        log.info("WebSocket连接已关闭: sessionId={}, status={}", session.getId(), status);
    }

    @Override
    public void afterPropertiesSet() {
        retryThread = Thread.ofVirtual().name("socketRetryThread").start(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    RetryEntity take = retryQueue.take();
                    WebSocketSession session = take.session;
                    if (session.isOpen()) {
                        session.sendMessage(take.textMessage);
                        if (take.retryCount - 1 > 0) {
                            RetryEntity newEntity = new RetryEntity(delayTime, take.req, take.retryCount - 1, take.session, take.textMessage);
                            retryQueue.add(newEntity);
                            log.debug("添加重试消息: req={}, 剩余重试次数: {}", take.req, take.retryCount - 1);
                        }
                    } else {
                        log.debug("会话已关闭，取消重试: req={}", take.req);
                    }
                } catch (InterruptedException e) {
                    log.info("重试线程被中断");
                    Thread.currentThread().interrupt();
                    break;
                } catch (Exception e) {
                    log.error("处理重试消息异常", e);
                }
            }
            log.info("重试线程已退出");
        });
    }
    
    @Override
    public void destroy() {
        log.info("销毁WebSocket处理器");
        
        // 关闭所有WebSocket连接
        for (WebSocketSession session : webSocketSessionMap.values()) {
            try {
                if (session.isOpen()) {
                    session.close();
                }
            } catch (Exception e) {
                log.error("关闭WebSocket会话异常", e);
            }
        }
        webSocketSessionMap.clear();
        
        // 中断重试线程
        if (retryThread != null && retryThread.isAlive()) {
            retryThread.interrupt();
        }
        
        retryQueue.clear();
    }
}