package com.example.ScattergoriesTogetherAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private static UserRepository userRepository;

    public static User addToCollection(String username, String pass) {
        User newUser = new User(username, pass);
        return userRepository.save(newUser); // Saves to the collection
    }



}
