package com.sprinklr.InternProjectBot.StringUtilities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StringOperations {

    public static List<String> findAllWordsFromString(String input){
        List<String> allWords = new ArrayList<>();
        StringBuilder lastWord = new StringBuilder();
        for(int i = 0;i<input.length();i++){
            if(input.charAt(i) == ' '){
                if(lastWord.toString().equals("")){
                    continue;
                }
                else{
                    allWords.add(lastWord.toString());
                }
                lastWord = new StringBuilder();
            }
            else {
                lastWord.append(input.charAt(i));
            }

        }
        if(!lastWord.toString().equals("")){
            allWords.add(lastWord.toString());
        }
        return allWords;
    }
    /*
    * This function returns the first and last word of a string, where first word is upto the word before the first space
     */

    public static List<String> getTheFirstAndLastWords(String input){

        List<String> allWords = new ArrayList<>();
        StringBuilder firstWord = new StringBuilder();
        StringBuilder lastWord = new StringBuilder();
        if(input == null){
            allWords.add(firstWord.toString());
            allWords.add(lastWord.toString());
            return allWords;
        }
        int i;
        for(i = 0;i<input.length();i++){
            if(input.charAt(i) == ' '){
                break;
            }
            else {
                firstWord.append(input.charAt(i));
            }
        }
        for(;i<input.length();i++){
            if(input.charAt(i) != ' '){
                break;
            }
        }
        for(;i<input.length();i++){
            lastWord.append(input.charAt(i));
        }
        allWords.add(firstWord.toString());
        allWords.add(lastWord.toString());
        return allWords;
    }

    public static List<String> findStringsStartWithAt(String input){

        List<String> list = new ArrayList<>();
        if(input==null){
            return list;
        }
        else{
            for(int i = 0;i<input.length();i++){
                if(input.charAt(i) == '@'){
                    i++;
                    StringBuilder member = new StringBuilder();
                    for(;i<input.length();i++){
                        if(input.charAt(i) <= 32 || input.charAt(i)==','){
                            list.add(member.toString());
                            break;
                        }
                        else{
                            member.append(input.charAt(i));
                        }
                    }
                    if(i==input.length()){
                        list.add(member.toString());
                    }
                }
            }
        }
        return list;
    }

    public static List<String> splitStringIntoList(String input){
        if(input==null){
            return new ArrayList<>();
        }
        String[] values = input.split(",");

        // Convert the array to a list
        List<String> stringList = Arrays.asList(values);
        return stringList;

    }

}
