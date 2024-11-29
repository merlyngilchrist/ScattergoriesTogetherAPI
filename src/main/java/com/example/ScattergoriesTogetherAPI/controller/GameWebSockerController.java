package com.example.ScattergoriesTogetherAPI.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class GameWebSockerController {
    
    @MessageMapping("/start-game")
    @SendTo("/topic/game-updates")
    public String startGame(String gameId) {
        return "Game " + gameId + " has started!";
    }
}
