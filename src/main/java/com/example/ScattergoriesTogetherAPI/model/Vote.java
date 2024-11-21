package com.example.ScattergoriesTogetherAPI.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection = "votes")
public class Vote {
    @Id
    private String id;

    private String gameId;
    private String responseId;
    private String voterUsername;
    private boolean isValidVote;

    public Vote(){}

    public Vote(String gameId, String responseId, String voterUsername, boolean isValidVote){
        this.gameId = gameId;
        this.responseId = responseId;
        this.voterUsername = voterUsername;
        this.isValidVote = isValidVote;
    }

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

    public String getResponseId() {
        return responseId;
    }

    public void setResponseId(String responseId) {
        this.responseId = responseId;
    }

    public String getVoterUsername() {
        return voterUsername;
    }

    public void setVoterUsername(String voterUsername) {
        this.voterUsername = voterUsername;
    }

    public boolean isValidVote() {
        return isValidVote;
    }

    public void setValidVote(boolean isValidVote) {
        this.isValidVote = isValidVote;
    }

    
}
