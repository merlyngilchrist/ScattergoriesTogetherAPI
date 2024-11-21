package com.example.ScattergoriesTogetherAPI.repository;

public interface GameRepositoryCustom {
    void incrementRound(String gameId);

    void setCurrentLetter(String gameId, String letter);
}
