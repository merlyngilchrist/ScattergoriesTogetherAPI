package com.example.ScattergoriesTogetherAPI.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.example.ScattergoriesTogetherAPI.utility.WebSocketSessionManager;

@Service
public class NotificationService {
    
    @Autowired  
    private WebSocketSessionManager sessionManager;

    public void notifyPlayersToVote(String gameId, String responseId, String intiatorUsername, List<String> playersToNotify) {
        String message = "Please vote on response: " + responseId;

        for (String player : playersToNotify) {
            WebSocketSession session = sessionManager.getSession(player);
            if (session != null && session.isOpen()) {
                try {
                    session.sendMessage(new TextMessage(message));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
