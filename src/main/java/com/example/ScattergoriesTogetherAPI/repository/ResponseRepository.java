package com.example.ScattergoriesTogetherAPI.repository;

import com.example.ScattergoriesTogetherAPI.model.Response;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ResponseRepository extends MongoRepository<Response, String>{
    // List<Response> findByGameIdandUserId(String gameId, String userId);

    List<Response> findByGameIdAndRound(String gameId, int round);
} 
