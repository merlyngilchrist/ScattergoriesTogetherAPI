package com.example.ScattergoriesTogetherAPI.controller;

import com.example.ScattergoriesTogetherAPI.model.Prompt;
import com.example.ScattergoriesTogetherAPI.service.PromptService;
import com.example.ScattergoriesTogetherAPI.utility.PromptGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/prompts")
public class PromptRestController {

    private final PromptService promptService;



    @Autowired
    public PromptRestController(PromptService promptService) {
        this.promptService = promptService;
    }

    @RequestMapping(value = "/generate",method = RequestMethod.GET)
    public String[] GeneratePrompt() {
        String[] rList = new String[12];
        int[] promptIDs = PromptGenerator.GeneratePromptList(110);
        for (int i = 0; i < promptIDs.length; i++) {
            rList[i] = promptService.getPrompt(promptIDs[i]).getCategory();
        }

        return rList;
    }

    

    @RequestMapping(value = "/get",method = RequestMethod.GET)
    public String GetPrompt() {
        Prompt prompt = promptService.getPrompt(20);
        return prompt.getCategory();
    }

    

}
