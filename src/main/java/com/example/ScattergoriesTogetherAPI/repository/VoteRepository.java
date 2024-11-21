package com.example.ScattergoriesTogetherAPI.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.ScattergoriesTogetherAPI.model.Vote;

public interface VoteRepository extends MongoRepository<Vote, String>{
    List<Vote> findByGameIdAndResponseId(String gameId, String responseId);
}
