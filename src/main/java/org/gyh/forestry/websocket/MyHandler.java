package org.gyh.forestry.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.ConcurrentWebSocketSessionDecorator;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * create by GYH on 2024/5/24
 */
@Slf4j
@Component
public class MyHandler extends TextWebSocketHandler {
    private final Map<String, WebSocketSession> webSocketSessionMap = new ConcurrentHashMap<>();
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
    public void handleTextMessage(WebSocketSession session, TextMessage message) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken principal = (UsernamePasswordAuthenticationToken)session.getPrincipal();
        SecurityContextHolder.getContext().setAuthentication(principal);
        log.info(message.getPayload());
        ServiceRequestInfo serviceRequestInfo = json.readValue(message.getPayload(), ServiceRequestInfo.class);
        if (serviceRequestInfo.getOrder().equals("/ping")) {
            return;
        }
        if (serviceRequestInfo.getOrder().equals("/ok")) {
            return;
        }
        SocketServletRequest socketServletRequest = new SocketServletRequest(HttpMethod.POST.name(), serviceRequestInfo.getOrder());
        socketServletRequest.setContentType(MediaType.APPLICATION_JSON_VALUE);
        socketServletRequest.setContent(json.writeValueAsBytes(serviceRequestInfo.getBody()));

        SocketServletResponse socketServletResponse = new SocketServletResponse();
        socketServletResponse.setStatus(HttpStatus.OK.value());
        ServiceResponseInfo serviceResponseInfo = new ServiceResponseInfo();
        serviceResponseInfo.setOrder(0);
        serviceResponseInfo.setReq(serviceRequestInfo.getReq());
        try {
            servlet.service(socketServletRequest, socketServletResponse);
            serviceResponseInfo.setBody(socketServletResponse.getContentAsString());
        } catch (Exception e) {
            log.error("socket异常",e);
            serviceResponseInfo.setBody(e.getMessage());
        }
        session.sendMessage(new TextMessage(json.writeValueAsBytes(serviceResponseInfo)));
        // 有消息就广播下
        /*for (Map.Entry<String, WebSocketSession> entry : webSocketSessionMap.entrySet()) {
            String s = entry.getKey();
            WebSocketSession webSocketSession = entry.getValue();
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(s + ":" + message.getPayload()));
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                log.info("send to {} msg:{} isVirtual:{}, user:{}", s, message.getPayload(), Thread.currentThread().isVirtual(), user.getUsername());
            }
        }*/
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
}
