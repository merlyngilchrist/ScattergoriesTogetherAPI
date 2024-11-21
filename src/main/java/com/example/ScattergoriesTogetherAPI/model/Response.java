package com.example.ScattergoriesTogetherAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "responses")
public class Response {
    @Id
    private String id;

    private String gameId;
    private String userId;
    private String promptText;
    private String answer;
    private boolean isValid = false;
    private int round;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getGameId() {
        return gameId;
    }
    public void setGameId(String gameId) {
        this.gameId = gameId;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getPromptText() {
        return promptText;
    }
    public void setPromptText(String promptId) {
        this.promptText = promptId;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public boolean isValid() {
        return isValid;
    }
    public void setValid(boolean isValid) {
        this.isValid = isValid;
    }
    public int getRound() {
        return round;
    }
    public void setRound(int round) {
        this.round = round;
    }

}
