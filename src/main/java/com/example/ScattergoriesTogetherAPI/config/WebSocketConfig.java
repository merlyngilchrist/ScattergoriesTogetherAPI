package com.example.ScattergoriesTogetherAPI.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.*;

import com.example.ScattergoriesTogetherAPI.controller.GameWebSockerController;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer{
    
    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(new GameWebSocketHandler(), "/scattergories-websocket").setAllowedOrigins("https://romacosta.github.io");
    }
}