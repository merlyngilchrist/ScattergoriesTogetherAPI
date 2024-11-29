package com.example.ScattergoriesTogetherAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "games")
public class Game {
    @Id
    private String id;

    private String gameCode;
    private int currentRound = 1;
    private int currentPromptIndex;
    private String currentLetter;
    private String hostUsername;
    private String status;

    private List<String> players;
    private String[] currentPrompts;
    private List<Response> responses;

    public Game(){}

    public Game(String gameCode, String hostUsername){
        this.gameCode = gameCode;
        this.hostUsername = hostUsername;
        this.status = "LOBBY";
        this.currentRound = 1;
        this.currentPromptIndex = 0;
        this.currentLetter = "";
        this.currentPrompts = new String[12];
        this.responses = new ArrayList<Response>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGameCode() {
        return gameCode;
    }

    public void setGameCode(String gameCode) {
        this.gameCode = gameCode;
    }

    public int getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(int currentRound) {
        this.currentRound = currentRound;
    }

    public String getCurrentLetter() {
        return currentLetter;
    }

    public void setCurrentLetter(String currentLetter) {
        this.currentLetter = currentLetter;
    }

    public List<String> getPlayers() {
        return players;
    }

    public void setPlayersIDs(List<String> playersIDs) {
        this.players = playersIDs;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String[] getCurrentPrompts() {
        return currentPrompts;
    }

    public void setCurrentPrompts(String[] currentPrompts) {
        this.currentPrompts = currentPrompts;
    }

    public void setPlayers(List<String> players) {
        this.players = players;
    }

    public List<Response> getResponses() {
        return responses;
    }

    public void setResponses(List<Response> responses) {
        this.responses = responses;
    }

    public int getCurrentPromptIndex() {
        return currentPromptIndex;
    }

    public void setCurrentPromptIndex(int currentPromptIndex) {
        this.currentPromptIndex = currentPromptIndex;
    }
    
    
    
}
