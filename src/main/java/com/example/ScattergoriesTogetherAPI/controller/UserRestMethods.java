package com.example.ScattergoriesTogetherAPI.controller;

import com.example.ScattergoriesTogetherAPI.model.User;
import org.antlr.v4.runtime.misc.Pair;


import java.util.HashMap;




public class UserRestMethods {

    private final static HashMap<String,User> users = new HashMap<>();

    public static HashMap<String,User> getUsers() {
        return users;
    }
    public static User getUser(String username) {
        if(!users.containsKey(username)) {
            System.out.println("IDIOT");
        
            return null;
            
        }
        return users.get(username);
    }

    public static void addUser(User user) {

        users.put(user.getUsername(),user);

    }
    public static void removeUser(String username) {
        users.remove(username);
    }
    public static Pair<Boolean,String> validateLogin(String username, String password) {
        if(!users.containsKey(username)) {
            return new Pair<>(false,"User not found");
        }
        if(!users.get(username).getPassword().equals(password)) {
            return new Pair<>(false,"Wrong password");
        }

        return new Pair<>(true,"User logged in");
    }

    public static Pair<Boolean,String> validateSignUp(String username, String password) {
        if(users.containsKey(username)) {
            return new Pair<>(false,"Username is already in use");
        }
        User newUser = new User(username,password);
        addUser(newUser);
        return new Pair<>(true,"User Created");
    }

    public static User loginUser(String username, String password) {
        Pair<Boolean,String> result = validateLogin(username,password);
        boolean resultBool = result.a;
        String resultString = result.b;

        if(!resultBool) {
            System.err.println(resultString);
            return null;
        }
        return users.get(username);
    }





}
