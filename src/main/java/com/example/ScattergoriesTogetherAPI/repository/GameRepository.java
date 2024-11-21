package com.example.ScattergoriesTogetherAPI.repository;

import com.example.ScattergoriesTogetherAPI.model.Game;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

public interface GameRepository extends MongoRepository<Game, String>, GameRepositoryCustom{
    //Find a game by its game code
    Optional<Game> findByGameCode(String gameCode);

    //Find active games
    @Query("{'status': 'ACTIVE'}")
    List<Game> findActiveGames();
}