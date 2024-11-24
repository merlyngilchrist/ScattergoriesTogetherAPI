package com.example.ScattergoriesTogetherAPI.utility;

import java.util.ArrayList;
import java.util.Random;

public class PromptGenerator {





    public static int[] GeneratePromptList(int total){
        Random rand = new Random();
        ArrayList<Integer> returnPrompts = new ArrayList<>();
        while(returnPrompts.size() < 12){
            int newInt = rand.nextInt(total) + 1;
            if(!returnPrompts.contains(newInt)){
                returnPrompts.add(newInt);
            }
        }

        int[] result = new int[returnPrompts.size()];
        for (int i = 0; i < returnPrompts.size(); i++) {
            result[i] = returnPrompts.get(i);
        }

        return result;
    }



}
