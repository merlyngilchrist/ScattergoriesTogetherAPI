package com.example.ScattergoriesTogetherAPI.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ScattergoriesTogetherAPI.model.Prompt;
import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.repository.PromptRepository;
import com.example.ScattergoriesTogetherAPI.repository.UserRepository;

@Service
public class PromptService {

    @Autowired
    private PromptRepository promptRepository;

    public long getDocumentCount() {
        return promptRepository.countDocuments();
    }

    public Prompt getPrompt(long id){
        return promptRepository.findBy_id(id);
    }
}
