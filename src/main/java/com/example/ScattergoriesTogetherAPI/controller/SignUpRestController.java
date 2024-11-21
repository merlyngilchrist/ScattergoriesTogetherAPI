package com.example.ScattergoriesTogetherAPI.controller;

import com.example.ScattergoriesTogetherAPI.model.User;
import com.example.ScattergoriesTogetherAPI.repository.UserRepository;
import com.example.ScattergoriesTogetherAPI.service.UserService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/signup")
public class SignUpRestController {
    @RequestMapping(value = "/create/{username}/{password}",method = RequestMethod.POST)
    public static boolean signup(@PathVariable String username,@PathVariable String password, HttpServletRequest request) {
        try{
            UserService.addToCollection(username, password);
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
        
        
        // Redirect to dashboard or home page
        return true;
    }
}
