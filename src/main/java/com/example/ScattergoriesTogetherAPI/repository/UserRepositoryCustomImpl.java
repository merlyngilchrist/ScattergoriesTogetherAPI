package com.example.ScattergoriesTogetherAPI.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

public class UserRepositoryCustomImpl implements UserRepositoryCustom{
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void incrementGamesWon(String username){
        Query query = new Query(Criteria.where("username").is(username));
        Update update = new Update().inc("gamesWon", 1);
        mongoTemplate.updateFirst(query, update, User.class);
    }

}
