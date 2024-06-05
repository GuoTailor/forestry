package org.gyh.forestry.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
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
import java.util.concurrent.atomic.AtomicLong;

/**
 * create by GYH on 2024/5/24
 */
@Slf4j
@Component
public class MyHandler extends TextWebSocketHandler implements InitializingBean {
    private final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();
    private final DelayQueue<RetryEntity> retryQueue = new DelayQueue<>();
    private final AtomicLong sequencer = new AtomicLong();
    // 重试消息 毫秒
    private final static long delayTime = 1000L;
    @Autowired
    private DispatcherServlet servlet;
    @Autowired
    private ObjectMapper json;

    //成功连接时
    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        super.afterConnectionEstablished(session);
        // 设置最大报文大小，如果报文过大则会报错的,可以将限制调大。
        // 会覆盖config中的配置。
        session.setBinaryMessageSizeLimit(8 * 1024);
        session.setTextMessageSizeLimit(8 * 1024);
        webSocketSessionMap.put(session.getId(), new ConcurrentWebSocketSessionDecorator(session, 10_000, Integer.MAX_VALUE));
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
        log.info(message.getPayload());
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
            log.error("socket异常", e);
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
        session = webSocketSessionMap.get(session.getId());
        TextMessage textMessage = new TextMessage(bytes);
        RetryEntity entity = new RetryEntity(delayTime, serviceRequestInfo.getReq(),3, session, textMessage);
        session.sendMessage(textMessage);
        boolean add = retryQueue.add(entity);
        log.info("socket添加重试{}", add);
    }

    //报错时
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
        log.warn("handleTransportError:: sessionId: {} {}", session.getId(), exception.getMessage(), exception);
        if (session.isOpen()) {
            try {
                session.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    //连接关闭时
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        super.afterConnectionClosed(session, status);
        webSocketSessionMap.remove(session.getId());
        if (session.isOpen()) {
            try {
                session.close();
            } catch (Exception ex) {
                // ignore
            }
        }
    }

    @Override
    public void afterPropertiesSet() {
        Thread.ofVirtual().name("socketRetryThread").start(() -> {
            while (true) {
                try {
                    RetryEntity take = retryQueue.take();
                    take.session.sendMessage(take.textMessage);
                    if (take.retryCount - 1 > 0) {
                        boolean add = retryQueue.add(new RetryEntity(delayTime, take.req, take.retryCount - 1, take.session, take.textMessage));
                        log.info("socket添加重试{} 第{}次", add, 3 - take.retryCount);
                    }
                } catch (Exception e) {
                    log.info("socket重试异常", e);
                }
            }
        });
    }
}
