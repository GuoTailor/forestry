package org.gyh.forestry.config;

import lombok.extern.slf4j.Slf4j;
import org.gyh.forestry.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
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
        // 有消息就广播下
        for (Map.Entry<String, WebSocketSession> entry : webSocketSessionMap.entrySet()) {
            String s = entry.getKey();
            WebSocketSession webSocketSession = entry.getValue();
            if (webSocketSession.isOpen()) {
                webSocketSession.sendMessage(new TextMessage(s + ":" + message.getPayload()));
                User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                log.info("send to {} msg:{} isVirtual:{}, user:{}", s, message.getPayload(), Thread.currentThread().isVirtual(), user.getUsername());
            }
        }
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
