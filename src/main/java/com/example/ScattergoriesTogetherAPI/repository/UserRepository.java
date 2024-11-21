package com.example.ScattergoriesTogetherAPI.repository;

import com.example.ScattergoriesTogetherAPI.model.User;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String>, UserRepositoryCustom{
    //Find a user by username
    Optional<User> findByUsername(String username); 
}