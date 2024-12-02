package com.example.ScattergoriesTogetherAPI.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.example.ScattergoriesTogetherAPI.utility.WebSocketSessionManager;

public class GameWebSocketHandler extends TextWebSocketHandler {
    
    @Autowired
    private WebSocketSessionManager sessionManager;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String username = getUsernameFromSession(session);
        if (username != null) {
            sessionManager.addSession(username, session);
        }
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String username = getUsernameFromSession(session);
        if (username != null) {
            sessionManager.removeSession(username);
        }
    }

    private String getUsernameFromSession(WebSocketSession session) {
        return (String) session.getAttributes().get("username");
    }
}
