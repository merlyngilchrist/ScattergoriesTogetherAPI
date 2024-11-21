package com.example.ScattergoriesTogetherAPI.controller;

import com.example.ScattergoriesTogetherAPI.utility.PromptGenerator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompts")
public class PromptRestController {

    @RequestMapping(value = "/generate",method = RequestMethod.GET)
    public String[] GeneratePrompt() {
        return PromptGenerator.GeneratePromptList();
    }

    @RequestMapping(value = "",method = RequestMethod.GET)
    public String Generate() {
        return "sup";
    }


}
