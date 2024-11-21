package com.example.ScattergoriesTogetherAPI.repository;

import com.example.ScattergoriesTogetherAPI.model.Prompt;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Pageable;

public interface PromptRepository extends MongoRepository<Prompt, String>{
    //Custom query to get a list of random prompts
    @Query("{ }")
    List<Prompt> findRandomPrompts(Pageable pageable);
}
