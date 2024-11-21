package com.example.ScattergoriesTogetherAPI.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.example.ScattergoriesTogetherAPI.model.Response;

public class ResponseRepositoryCustomImpl implements ResponseRepositoryCustom{
    
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public void markResponseAsValid(String responseId){
        Query query = new Query(Criteria.where("id").is(responseId));
        Update update = new Update().set("isValid", true);
        mongoTemplate.updateFirst(query, update, Response.class);
    }

    @Override
    public void markResponseAsInvalid(String responseId){
        Query query = new Query(Criteria.where("id").is(responseId));
        Update update = new Update().set("isValid", false);
        mongoTemplate.updateFirst(query, update, Response.class);
    }

}
