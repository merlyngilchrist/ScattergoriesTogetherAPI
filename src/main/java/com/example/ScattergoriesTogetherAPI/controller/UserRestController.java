package com.example.ScattergoriesTogetherAPI.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.service.UserService;


@RestController
@RequestMapping("/users")
public class UserRestController {

    private final UserService userService;

    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }



    @RequestMapping(value = "/getAll",method = RequestMethod.PUT)
    public List<User> GeneratePrompt() {
        return userService.findAll();
    }
}
