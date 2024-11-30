package com.example.ScattergoriesTogetherAPI.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.ScattergoriesTogetherAPI.model.Game;
import com.example.ScattergoriesTogetherAPI.model.Response;
import com.example.ScattergoriesTogetherAPI.repository.GameRepository;
import com.example.ScattergoriesTogetherAPI.repository.ResponseRepository;
import com.example.ScattergoriesTogetherAPI.service.GameService;

@RestController
@RequestMapping("/games")
public class GameRestController {

    @Autowired 
    private GameService gameService;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @GetMapping("/{gameCode}")
    public ResponseEntity<?> getGameDetails(@PathVariable String gameCode){
        try{
            Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
            if (gameOpt.isPresent()) {
                return ResponseEntity.ok(gameOpt.get());
            } else{
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
            }
        } catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occured: " + e.getMessage());
        }
    }

    @PostMapping("/create")
    public Map<String, String> createGame(@RequestParam Map<String, String> requestBody){
        String hostUsername = requestBody.get("hostUsername");
        String gameCode = gameService.createGame(hostUsername);
        Map<String, String> response = new HashMap<>();
        response.put("gameId", gameCode);
        return response;
    }

    @PostMapping("/{gameCode}/start")
    public ResponseEntity startGame(@PathVariable String gameCode){
        try {
            gameService.startGame(gameCode);
            Map<String, String> response = new HashMap<>();
            response.put("success", "true");
            return ResponseEntity.ok(response);
        } catch(Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/{gameCode}/join")
    public ResponseEntity<?> joinGame(@PathVariable String gameCode, @RequestBody Map<String, String> payload){
        System.out.println("Game Code: " + gameCode);
        System.out.println("Payload: " + payload);
        String username = payload.get("username");
        if (username == null || username.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is required");
        }
        boolean success = gameService.joinGame(gameCode, username);
        if (success) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Game not found or not joinable.");
        }
    }

    @GetMapping("/{gameCode}/players")
    public ResponseEntity<List<String>> getPlayers(@PathVariable String gameCode){
        Optional<Game> gameOpt = gameService.getGameByCode(gameCode);
        if (gameOpt.isPresent()) {
            Game game = gameOpt.get();
            return ResponseEntity.ok(game.getPlayers());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
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
    public ResponseEntity<?> endGame(@PathVariable String gameCode){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        if (gameOpt.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Game game = gameOpt.get();
        Map<String, Object> result = gameService.endGame(game);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/{gameCode}/round/{round}/prompt/{promptText}/responses")
    public ResponseEntity<?> getResponsesForPrompt(@PathVariable String gameCode, @PathVariable int round, @PathVariable String promptText) {
        try {
            Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
            if (gameOpt.isPresent()) {
                Game game = gameOpt.get();
                List<Response> responses = responseRepository.findByGameIdAndRoundAndPromptText(game.getId(), round, promptText);
                return ResponseEntity.ok(responses);
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Game not found");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{gameCode}/responses/{responseId}/validate")
    public ResponseEntity<?> validateResponse(@PathVariable String gameCode, @PathVariable String responseId, @RequestParam boolean isValid){
        try {
            Optional<Response> responseOpt = responseRepository.findById(responseId);
            if (responseOpt.isPresent()){
                Response response = responseOpt.get();
                response.setValid(isValid);
                responseRepository.save(response);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Response not found.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/{gameCode}/nextPrompt")
    public ResponseEntity<?> nextPrompt(@PathVariable String gameCode) {
        try {
            gameService.startNextPrompt(gameCode);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }
    
}
