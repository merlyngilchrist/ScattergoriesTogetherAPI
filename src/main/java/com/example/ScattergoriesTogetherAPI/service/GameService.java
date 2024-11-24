package com.example.ScattergoriesTogetherAPI.service;

import com.example.ScattergoriesTogetherAPI.model.Game;
import com.example.ScattergoriesTogetherAPI.model.Player;
import com.example.ScattergoriesTogetherAPI.model.Prompt;
import com.example.ScattergoriesTogetherAPI.model.Response;
import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.model.Vote;
import com.example.ScattergoriesTogetherAPI.repository.GameRepository;
import com.example.ScattergoriesTogetherAPI.repository.PromptRepository;
import com.example.ScattergoriesTogetherAPI.repository.ResponseRepository;
import com.example.ScattergoriesTogetherAPI.repository.UserRepository;
import com.example.ScattergoriesTogetherAPI.repository.VoteRepository;
import com.swabunga.spell.engine.SpellDictionaryHashMap;
import com.swabunga.spell.engine.Word;
import com.swabunga.spell.event.SpellCheckEvent;
import com.swabunga.spell.event.SpellCheckListener;
import com.swabunga.spell.event.SpellChecker;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class GameService implements SpellCheckListener{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PromptRepository promptRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private VoteRepository voteRepository;

    @Autowired
    private NotificationService notificationService;

    @Value("classpath:words.utf-8.txt")
    private Resource wordFile;

    private SpellChecker spellChecker;

    private static final Set<String> ARTICLES = new HashSet<>(Arrays.asList("A", "An", "The"));

    private final int NUM_ROUNDS = 3;
    private final int ROUND_DURATION_SECONDS = 60;

    public GameService() throws IOException{
        this.spellChecker = null;
    }

    @PostConstruct
    public void initialize(){
        try{
            Path tempFile = Files.createTempFile("words", ".txt");
            Files.copy(wordFile.getInputStream(), tempFile, StandardCopyOption.REPLACE_EXISTING);
            SpellDictionaryHashMap dictionary = new SpellDictionaryHashMap(tempFile.toFile());
            spellChecker = new SpellChecker(dictionary);
            spellChecker.addSpellCheckListener(this);
        }catch (IOException e){
            throw new RuntimeException("Failed to initialize GameService: unable to load word file", e);
        }
    }
    
    public boolean isResponseValid(String response, String requiredLetter){
        //Step 1: Tokenize and remove articles
        String[] words = response.split("\\s");
        String mainWord = words[0];
        if (ARTICLES.contains(mainWord)) {
            mainWord = words.length > 1 ? words[1] : mainWord;
        }

        //Step 2: Check if main word starts with the required letter (case insensitive)
        if (!mainWord.substring(0, 1).equalsIgnoreCase(requiredLetter)) {
            return false;
        }

        //Step 3: Spell-check the main word
        if (!spellChecker.isCorrect(mainWord)) {
            return false;
        }

        return true;
    }

    //Initiate a vote on a response
    public void initiateVote(String gameId, String responseId, String initiatorUsername){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameId);
        if (gameOpt == null) throw new IllegalArgumentException("Invalid game");

        Game game = gameOpt.get();
        Optional<Response> responseOpt = responseRepository.findById(responseId);
        if (responseOpt == null) throw new IllegalArgumentException("Response not found");

        Response response = responseOpt.get();

        List<String> playersToNotify = game.getPlayers().stream()
            .filter(username -> !username.equals(initiatorUsername) && !username.equals(response.getUserId()))
            .collect(Collectors.toList());


        notificationService.notifyPlayersToVote(gameId, responseId, initiatorUsername, playersToNotify);
    }

    //Cast a vote on a response
    public void castVote(String gameId, String responseId, String voterUsername, boolean isValidVote){
        Vote vote = new Vote(gameId, responseId, voterUsername, isValidVote);
        voteRepository.save(vote);

        if (allVotesCast(gameId, responseId)) {
            finalizeVote(gameId, responseId);
        }
    }

    //Check if all vote are cast
    private boolean allVotesCast(String gameId, String responseId){
        List<Vote> votes = voteRepository.findByGameIdAndResponseId(gameId, responseId);
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameId);
        Game game = gameOpt.get();
        int playerCount = game.getPlayers().size() - 1;
        return votes.size() >= playerCount;
    }

    private void finalizeVote(String gameId, String responseId){
        List<Vote> votes = voteRepository.findByGameIdAndResponseId(gameId, responseId);
        long validCount = votes.stream().filter(Vote::isValidVote).count();
        long invalidCount = votes.size() - validCount;

        boolean isResponseValid = validCount > invalidCount;

        Optional<Game> gameOpt = gameRepository.findByGameCode(gameId);
        Game game = gameOpt.get();
        game.getResponses().stream().filter(response -> response.getId().equals(responseId)).findFirst().ifPresent(response -> response.setValid(isResponseValid));

        gameRepository.save(game);
    }

    @Override
    public void spellingError(SpellCheckEvent event){
        List<Object> suggestions = event.getSuggestions();
        for (Object suggestion : suggestions){
            if (suggestion instanceof Word) {
                Word word = (Word) suggestion;
                System.out.println("Suggestion: " + word.getWord());
            }else{
                System.out.println("Unexpected suggestion type: " + suggestion.getClass().getName());
            }
        }
    }

    public String createGame(String hostUsername){
        String gameCode = generateGameCode();
        Game game = new Game(gameCode, hostUsername);
        game.setPlayersIDs(new ArrayList<>());
        gameRepository.save(game);
        return gameCode;
    }

    public void startGame(String gameCode){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        if (gameOpt.isPresent() && "LOBBY".equals(gameOpt.get().getStatus())) {
            Game game = gameOpt.get();
            game.setCurrentRound(1);
            game.setStatus("IN_PROGRESS");
            gameRepository.save(game);
        }
    }

    private String generateGameCode(){
        return UUID.randomUUID().toString().substring(0, 6).toUpperCase();
    }

    public boolean joinGame(String gameCode, String username){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        if (gameOpt.isPresent() && "LOBBY".equals(gameOpt.get().getStatus())) {
            Game game = gameOpt.get();
            game.getPlayers().add(username);
            gameRepository.save(game);
            return true;
        }
        return false;
    }

    public void startRound(String gameCode){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        if (gameOpt.isPresent()) {
            Game game = gameOpt.get();
            int currentRound = game.getCurrentRound();

            if (currentRound < NUM_ROUNDS) {
                //Roll a letter for this round
                char letter = rollLetter();
                game.setCurrentLetter(String.valueOf(letter));

                //Fetch random prompts for the round
//                List<Prompt> prompts = promptRepository.findRandomPrompts(PageRequest.of(0, 12));
//                game.setCurrentPrompts(prompts.stream().map(Prompt::getPromptText).collect(Collectors.toList()));

                game.setStatus("IN_PROGRESS");
                game.setCurrentRound(currentRound + 1);
                gameRepository.save(game);
            }else{
                endGame(game);
            }
        }
    }

    private char rollLetter(){
        Random random = new Random();
        return (char) ('A' + random.nextInt(26));
    }

    public void submitResponse(String gameCode, String username, String promptText, String answer){
        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        if (gameOpt.isPresent() && "IN_PROGRESS".equals(gameOpt.get().getStatus())) {
            Game game = gameOpt.get();

            boolean isValid = isResponseValid(answer, game.getCurrentLetter());

            //Validate that answer starts with the correct letter
            if (isValid) {
                Response response = new Response();
                response.setGameId(game.getId());
                response.setUserId(username);
                response.setPromptText(promptText);
                response.setAnswer(answer);
                response.setRound(game.getCurrentRound());
                responseRepository.save(response);
            }
        }
    }

    public Map<String, Integer> calculateRoundScores(String gameCode){
        Map<String, Integer> scores = new HashMap<>();

        Optional<Game> gameOpt = gameRepository.findByGameCode(gameCode);
        if (gameOpt.isPresent()) {
            Game game = gameOpt.get();
            int round = game.getCurrentRound();

            List<Response> responses = responseRepository.findByGameIdAndRound(game.getId(), round);

            //Group respones by prompt to find duplicates
            Map<String, List<Response>> responseByPrompt = responses.stream().collect(Collectors.groupingBy(Response::getPromptText));

            for (String prompt : responseByPrompt.keySet()){
                Map<String, List<Response>> responsesByAnswer = responseByPrompt.get(prompt).stream().collect(Collectors.groupingBy(Response::getAnswer));

                //Process each unique answer
                for (String answer : responsesByAnswer.keySet()){
                    List<Response> matchingResponses = responsesByAnswer.get(answer);

                    //Award points only if answer is unique (no duplicates)
                    if (matchingResponses.size() == 1) {
                        Response response = matchingResponses.get(0);
                        scores.put(response.getUserId(), scores.getOrDefault(response.getUserId(), 0) + 10);
                    }
                }
            }
        }
        return scores;
    }

    public void endGame(Game gameOpt){
        gameOpt.setStatus("COMPLETED");
        Map<String, Integer> totalScores = new HashMap<>();

        //Calculate total scores across all rounds
        for(int round = 1; round <= NUM_ROUNDS; round++){
            Map<String, Integer> roundScores = calculateRoundScores(gameOpt.getGameCode());
            for (Map.Entry<String, Integer> entry : roundScores.entrySet()){
                totalScores.put(entry.getKey(), totalScores.getOrDefault(entry.getKey(), 0) + entry.getValue());
            }
        }

        //Sort players by score to determine winners
        List<Map.Entry<String, Integer>> sortedScores = new ArrayList<>(totalScores.entrySet());
        sortedScores.sort((a, b) -> b.getValue() - a.getValue());

        //Update each user's stats
        for(int i = 0; i < sortedScores.size(); i++){
            String username = sortedScores.get(i).getKey();
            int score = sortedScores.get(i).getValue();
            if (i == 0) {
                userRepository.incrementGamesWon(username);
            }

            //Update high scores if applicable
            Optional<User> userOpt = userRepository.findByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                user.setRecordScore(Math.max(user.getRecordScore(), score));
                userRepository.save(user);
            }
        }
        gameRepository.save(gameOpt);
    }

}