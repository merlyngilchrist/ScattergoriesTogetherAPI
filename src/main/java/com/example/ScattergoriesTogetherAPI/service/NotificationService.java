package com.example.ScattergoriesTogetherAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void notifyPlayersToVote(String gameId, String resposneId, String initiatorUsername, List<String> playersToNotify){
        for (String player : playersToNotify){
            messagingTemplate.convertAndSendToUser(
                player,
                "/topic/vote/" + gameId,
                "Please vote on response: " + resposneId
            );
        }
    }
}
