package com.example.ScattergoriesTogetherAPI.utility;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

@Component
public class WebSocketSessionManager {
    private final Map<String, WebSocketSession> userSessions = new ConcurrentHashMap<>();

    public void addSession(String username, WebSocketSession session) {
        userSessions.put(username, session);
    }

    public void removeSession(String username) {
        userSessions.remove(username);
    }

    public WebSocketSession getSession(String username) {
        return userSessions.get(username);
    }
}
