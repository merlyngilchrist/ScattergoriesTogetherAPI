package com.example.ScattergoriesTogetherAPI.controller;

import com.example.ScattergoriesTogetherAPI.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("")
public class LoginController {


    @RequestMapping(value = "/login/{username}/{password}",method = RequestMethod.GET)
    public static String login(@PathVariable String username,@PathVariable String password, HttpServletRequest request) {

        //Validate and logins in user
        User currentUser = UserRestMethods.loginUser(username, password);

        // Get or create session
        HttpSession session = request.getSession(true);
        if(currentUser == null) {
            session.setAttribute("username", null);
            return "nope";
        }

        // Set session attribute
        session.setAttribute("username", username);

        // Redirect to dashboard or home page
        return "1";
    }

    @RequestMapping(value = "/logout",method = RequestMethod.GET)
    public static String logout(HttpSession session) {
        if (session != null) {
            session.invalidate(); // Invalidate session
        }
        return "redirect:/login"; // Redirect to login page
    }
}
