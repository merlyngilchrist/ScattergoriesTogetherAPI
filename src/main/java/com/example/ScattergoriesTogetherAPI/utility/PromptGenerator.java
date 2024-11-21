package com.example.ScattergoriesTogetherAPI.utility;

import java.util.ArrayList;
import java.util.Random;

public class PromptGenerator {


    private static String[] prompts = {
            "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M",
            "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    };




    public static String[] GeneratePromptList(){
        Random rand = new Random();
        ArrayList<String> returnPrompts = new ArrayList<>();
        String prompt;
        while(returnPrompts.size() < 12){
            prompt = prompts[rand.nextInt(prompts.length -1)];

            if(returnPrompts.contains(prompt)){
                continue;
            }
            returnPrompts.add(prompt);
        }



        return returnPrompts.toArray(new String[returnPrompts.size()]);
    }



}
