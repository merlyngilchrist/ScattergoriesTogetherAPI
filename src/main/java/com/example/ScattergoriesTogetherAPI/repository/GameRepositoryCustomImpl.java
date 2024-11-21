package com.example.ScattergoriesTogetherAPI.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.ScattergoriesTogetherAPI.model.Game;

public class GameRepositoryCustomImpl implements GameRepositoryCustom{

    @Autowired
    private MongoTemplate mongoTemplate;

    //Increment the current round of a game
    @Override
    public void incrementRound(String gameId){
        Query query = new Query(Criteria.where("id").is(gameId));
        Update update = new Update().inc("currentRound", 1);
        mongoTemplate.updateFirst(query, update, Game.class);
    }

    @Override
    public void setCurrentLetter(String gameId, String letter){
        Query query = new Query(Criteria.where("id").is(gameId));
        Update update = new Update().set("currentLetter", letter);
        mongoTemplate.updateFirst(query, update, Game.class);
    }
    
}
