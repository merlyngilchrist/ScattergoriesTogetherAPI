package com.example.ScattergoriesTogetherAPI.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.repository.UserRepository;

@Service
public class UserService {
    
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User addToCollection(String username, String pass) {
        User newUser = new User(username, pass);
        return userRepository.save(newUser); // Saves to the collection
    }

    public User findUser(String username) {
        return userRepository.findByUsername(username).get();
    }



}
