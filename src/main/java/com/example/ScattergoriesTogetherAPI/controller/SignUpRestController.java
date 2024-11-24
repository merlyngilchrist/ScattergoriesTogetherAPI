package com.example.ScattergoriesTogetherAPI.controller;

import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.service.UserService;
import com.mongodb.DuplicateKeyException;
import com.mongodb.MongoWriteException;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/signup")
public class SignUpRestController {

    private final UserService userService;

    @Autowired
    public SignUpRestController(UserService userService) {
        this.userService = userService;
    }



    @RequestMapping(value = "/create/{username}/{password}",method = RequestMethod.POST)
    public String signup(@PathVariable String username,@PathVariable String password, HttpServletRequest request) {
        try{
            userService.addToCollection(username, password);
        }catch(DataIntegrityViolationException k){
            return "User already exist";
        
        }catch(Exception e){
            System.out.println(e);
            return "Something went wrong";
        }
        
        return "User Made";
    }
}
