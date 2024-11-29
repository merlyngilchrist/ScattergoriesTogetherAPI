package com.example.ScattergoriesTogetherAPI.controller;

import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/login")
public class LoginRestController {

    private final UserService userService;

    @Autowired
    public LoginRestController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping(value = "verify/{username}/{password}",method = RequestMethod.GET)
    public String verifyUser(@PathVariable String username,@PathVariable String password, HttpServletRequest request) {
        User currentUser;
        try{
            currentUser = userService.findUser(username);

        }catch(Exception e){
            return "User does not exist";
        }

        if(!password.equals(currentUser.getPassword())){
            return "Password is Incorrect";
        }
        
        
        HttpSession session = request.getSession();
        session.setAttribute("username", username);
        return "Success";
    }

    @RequestMapping(value = "/{username}/{password}",method = RequestMethod.GET)
    public User loginUser(@PathVariable String username,@PathVariable String password, HttpServletRequest request) {
        User currentUser = null;
        try{
            currentUser = userService.findUser(username);

        }catch(Exception e){
            return null;
        }

        if(!password.equals(currentUser.getPassword())){
            return null;
        }
        
        

        return currentUser;
    }

    @RequestMapping(value = "/{username}", method = RequestMethod.GET)
    public User getUserProfile(@PathVariable String username, HttpServletRequest request){
        HttpSession session = request.getSession(false);
        if (session == null || !username.equals(session.getAttribute("username"))) {
            throw new RuntimeException("Unathorized access");
        }
        return userService.findUser(username);
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public static String logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // Invalidate session
        }
        return "Success"; // Redirect to login page
    }
}
