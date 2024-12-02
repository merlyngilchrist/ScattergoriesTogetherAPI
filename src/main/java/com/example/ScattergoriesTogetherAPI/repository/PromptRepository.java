package com.example.ScattergoriesTogetherAPI.repository;

import com.example.ScattergoriesTogetherAPI.model.Prompt;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.domain.Pageable;

public interface PromptRepository extends MongoRepository<Prompt, String>{
    //Custom query to get a list of random prompts
//    @Query(value = "{}")
    Prompt findBy_id(long promptId);

    @Query(value = "{}", count = true)
    Long countDocuments();
    
}
