package com.example.ScattergoriesTogetherAPI.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ScattergoriesTogetherAPI.model.Game;
import com.example.ScattergoriesTogetherAPI.repository.GameRepository;
import com.example.ScattergoriesTogetherAPI.service.GameService;

@RestController
@RequestMapping("/games")
public class GameRestController {

    @Autowired 
    private GameService gameService;

    private GameRepository gameRepository;

    @GetMapping("/{gameCode}")
    public Game getGameDetails(@PathVariable String gameCode){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        return gameOpt.orElseThrow(() -> new IllegalArgumentException("Game not found"));
    }

    @PostMapping("/create")
    public Map<String, String> createGame(@RequestParam String hostUsername){
        String gameCode = gameService.createGame(hostUsername);
        Map<String, String> response = new HashMap<>();
        response.put("gameId", gameCode);
        return response;
    }

    @PostMapping("/{gameCode}/start")
    public void startGame(@RequestParam String gameCode){
        gameService.startGame(gameCode);
    }

    @PostMapping("/{gameCode}/join")
    public boolean joinGame(@PathVariable String gameCode, @RequestParam String username){
        return gameService.joinGame(gameCode, username);
    }

    @PostMapping("/{gameCode}/startRound")
    public void startRound(@PathVariable String gameCode){
        gameService.startRound(gameCode);
    }

    @PostMapping("/{gameCode}/submitResponse")
    public void submitResponse(@PathVariable String gameCode, @RequestParam String username, @RequestParam String promptText, @RequestParam String answer){
        gameService.submitResponse(gameCode, username, promptText, answer);
    }

    @GetMapping("/{gameCode}/scores")
    public Map<String, Integer> getScores(@PathVariable String gameCode){
        return gameService.calculateRoundScores(gameCode);
    }

    @PostMapping("/{gameCode}/end")
    public void endGame(@PathVariable String gameCode){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        Game game = gameOpt.get();
        gameService.endGame(game);
    }
    
}
