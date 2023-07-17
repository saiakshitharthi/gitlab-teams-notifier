package com.sprinklr.InternProjectBot;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Diff;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class GitLabAPI {

    @Value("${GitLabApiURL}")
    private String gitLabApiURL;

    @Value("${GitLabWebsiteURL}")
    private String gitlabWebsiteURL;

    @Value("${GitLabAccessToken}")
    private String gitlabAccessToken;



    private Map<Integer ,ObjectNode > userIDToUserCache = new HashMap<>();


    public void replyToAMessage(String accessToken,String discussionThreadURL, String message){
        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
            System.out.println("Starting to send the reply.");
            System.out.println("This is the reply URL: " + discussionThreadURL + "/notes");
            url = new URL(discussionThreadURL + "/notes");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("PRIVATE-TOKEN", accessToken);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
        } catch (Exception e) {
            System.out.println("Error in creating Initial setup" + e.getMessage());

        }
        OutputStream outputStream = null;
        try {
            outputStream = httpURLConnection.getOutputStream();
        } catch (Exception e) {
            System.out.println("Error in getting output stream" + e.getMessage());
        }
        GitLabAPIPostRequestPayload messageToSend = new GitLabAPIPostRequestPayload();
        messageToSend.setBody(message);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(messageToSend);
        } catch (Exception e) {
            System.out.println("Error in converting to json" + e.getMessage());
        }

        try {
            assert outputStream != null;
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error in writing to output stream " + e.getMessage());
        }
        int responseCode = 0;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (Exception e) {
            System.out.println("Error in getting response code" + e.getMessage());
        }
        System.out.println("Response Code : " + responseCode);
    }


    public void resolveThread(String accessToken,String discussionThreadURL) {

        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
            System.out.println("Resolving the thread.");
            System.out.println("This is the resolve thread URL:" + discussionThreadURL + "/?resolved=true");
            url = new URL(discussionThreadURL + "?resolved=true");
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("PUT");
            httpURLConnection.setRequestProperty("PRIVATE-TOKEN", accessToken);
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
        } catch (Exception e) {
            System.out.println("Error in creating Initial " + e.getMessage());

        }
        OutputStream outputStream = null;
        try {
            outputStream = httpURLConnection.getOutputStream();
        } catch (Exception e) {
            System.out.println("Error in getting output stream in GitLabAPI: " + e.getMessage());
        }
        GitLabAPIPostRequestPayload messageToSend = new GitLabAPIPostRequestPayload();
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(messageToSend);
        } catch (Exception e) {
            System.out.println("Error in converting to json in GitLabAPI: " + e.getMessage());
        }

        try {
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error in writing to output stream in GitLabAPI: " + e.getMessage());
        }

        int responseCode = 0;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (Exception e) {
            System.out.println("Error in getting response code in GitLabAPI: " + e.getMessage());
        }
        System.out.println("Response Code : " + responseCode);

    }

    private ObjectNode getUserFromID(Integer ID){

        ObjectNode user = null;
        if(userIDToUserCache.containsKey(ID)){
            return userIDToUserCache.get(ID);
        }
        try{
            System.out.println("Making an API call to gitlab API, to get the User details of the user with ID: " + ID);
            URL url = new URL(this.gitLabApiURL + "users/" + ID);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("PRIVATE-TOKEN", gitlabAccessToken);
            Integer responseCode = connection.getResponseCode();
            InputStream inputStream =(InputStream) connection.getContent();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader input = new BufferedReader(reader);
            String inputString;
            String output = "";
            while((inputString = input.readLine())!=null){
                output += inputString;
            }
            ObjectMapper obj = new ObjectMapper();
            user =  obj.readValue(output, ObjectNode.class);
            System.out.println("Response Code of this call " + responseCode);
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        if(user!=null){
            System.out.println("User name of the user: " + user.get("username").asText());
            userIDToUserCache.put(ID,user);
            return user;
        }
        else{
            userIDToUserCache.put(ID,null);
            return null;
        }

    }

    public String getHandleFromID(Integer ID){
        ObjectNode user = getUserFromID(ID);
        if(user==null || user.get("username")==null){
            return null;
        }
        return user.get("username").asText();
    }

    public List<String>getHandleFromIDs(List<Integer> IDs){
        List<String> handles = new ArrayList<>();
        for(Integer ID : IDs){
            String handle = getHandleFromID(ID);
            if(handle!=null){
                handles.add(handle);
            }
        }
        return handles;
    }

    public String getEmailFromUsername(String username){
        if(username == null){
            return null;
        }
        if(username.equals("ebenezerrahul")){
            return "ebenezerrahuldeepak.vangur@sprinklr.com";
        }
        else{
            return username + "@sprinklr.com";
        }

    }

    public String getUsernameFromEmail(String email) {
        if (email == null) {
            return null;
        }
        if (email.equals("ebenezerrahuldeepak.vangur@sprinklr.com")) {
            return "ebenezerrahul";
        } else {
            return email.substring(0, email.indexOf("@"));
        }
    }

    public String getEmailFromID(Integer ID){
        return getEmailFromUsername(getHandleFromID(ID));
    }

    public String getURLFromID(Integer ID){

        ObjectNode user = getUserFromID(ID);
        if(user == null || user.get("web_url")==null){
            return null;
        }
        return user.get("web_url").asText();
    }

    public String getDiff(Payload payload, String gitlabAccessToken){
        Diff [] diff = null;
        if(payload == null){
            return null;
        }
        try{
            URL url = new URL(gitLabApiURL+"projects/" + payload.getProject().getId() + "/merge_requests/" + payload.getMerge_request().getIid() + "/diffs");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("PRIVATE-TOKEN", gitlabAccessToken);
            Integer responseCode = connection.getResponseCode();
            InputStream inputStream =(InputStream) connection.getContent();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader input = new BufferedReader(reader);
            String inputString;
            String output = "";
            while((inputString = input.readLine())!=null){
                output += inputString;
            }
            ObjectMapper obj = new ObjectMapper();
            diff =  obj.readValue(output, Diff[].class);
            for(int i = 0;i<diff.length;i++){
                System.out.println(diff[i].getDiff());
                System.out.println(diff[i].getNew_path());
                if(diff[i].getNew_path().equals(payload.getObject_attributes().getPosition().getNew_path())){
                    System.out.println("This is the DIFF:::  \n\n");
                    return diff[i].getDiff();
                }
            }
            System.out.println("Response Code of this call " + responseCode);
            return null;
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;

    }

    private boolean isValidPayload(Payload payload){
        if(payload == null){
            return false;
        }
        if(payload.getObject_attributes()==null){
            return false;
        }
        if(payload.getObject_attributes().getPosition()==null){
            return false;
        }
        if(payload.getObject_attributes().getPosition().getLine_range() == null){
            return false;
        }
        if(payload.getObject_attributes().getPosition().getLine_range().getStart() == null || payload.getObject_attributes().getPosition().getLine_range().getEnd() == null){
            return false;
        }
        if(payload.getObject_attributes().getPosition().getLine_range().getStart().getLine_code() == null || payload.getObject_attributes().getPosition().getLine_range().getEnd().getLine_code() == null){
            return false;
        }
        return true;
    }

    public String getCommentBetweenLines(Payload payload, String gitlabAccessToken){

        if(!isValidPayload(payload)){
            return null;
        }
        String startLineCode = payload.getObject_attributes().getPosition().getLine_range().getStart().getLine_code();
        String endLineCode = payload.getObject_attributes().getPosition().getLine_range().getEnd().getLine_code();
        List<Integer> startNumbers = lastTwoNumbersInLineCode(startLineCode);
        List<Integer> endNumbers = lastTwoNumbersInLineCode(endLineCode);
        if(startNumbers == null || endNumbers == null || startNumbers.size()!=2 || endNumbers.size()!=2){
            return null;
        }

        String diff = getDiff(payload, gitlabAccessToken);
        if(diff == null){
            return null;
        }
        int oldStart = startNumbers.get(0);
        int oldEnd = endNumbers.get(0);

        int newStart = startNumbers.get(1);
        int newEnd = endNumbers.get(1);

        StringBuilder codeSnippet = new StringBuilder();
        String[] diffLines = diff.split("\n");
        if(diffLines.length == 0){
            return null;
        }
        List<Integer> diffStarLineNumbers = getStartingNumbersLineOfDiff(diffLines[0]);
        if(diffStarLineNumbers.size()!=2){
            return null;
        }
        int newCurrentLineNumber = diffStarLineNumbers.get(1), oldCurrentLineNumber = diffStarLineNumbers.get(0);
        System.out.println("Diff new version line number: " + newCurrentLineNumber);
        System.out.println("Diff old version line number: " + oldCurrentLineNumber);

        List<String> diffLinesString = Arrays.asList(diffLines);


        for(int i = 1;i<diffLinesString.size();i++){

            boolean addThisLine = (liesInBetween(newCurrentLineNumber,newStart,newEnd)
                    && liesInBetween(oldCurrentLineNumber,oldStart,oldEnd));
            if(addThisLine){
                codeSnippet.append(diffLinesString.get(i));
                codeSnippet.append("\n");
            }
            if(diffLinesString.get(i).length() == 0){
                continue;
            }
            if(diffLinesString.get(i).charAt(0) == '+'){
                newCurrentLineNumber++;
            }else if(diffLinesString.get(i).charAt(0) == '-'){
                oldCurrentLineNumber++;
            } else if(diffLinesString.get(i).charAt(0) == '@'){
                List<Integer> startingNumbers = getStartingNumbersLineOfDiff(diffLinesString.get(i));
                if(startingNumbers.size()!=2){
                    //As it is not valid.
                    return null;
                }
                newCurrentLineNumber = startingNumbers.get(1);
                oldCurrentLineNumber = startingNumbers.get(0);
            }
            else {
                newCurrentLineNumber++;
                oldCurrentLineNumber++;
            }
        }

        System.out.println("This is the codeSnippet " + codeSnippet);
        return codeSnippet.toString();
    }

    private boolean liesInBetween(int number, int leftNumber, int rightNumber){
        return (number>=leftNumber)&&(number<=rightNumber);
    }

    private List<Integer> getStartingNumbersLineOfDiff(String diffLine){

        Pattern pattern = Pattern.compile("@@ -(\\d+)");
        Matcher matcher = pattern.matcher(diffLine);
        int oldLineNumber = 0;
        int newLineNumber = 0;
        if (matcher.find()) {
            oldLineNumber = Integer.parseInt(matcher.group(1));
        }
        pattern = Pattern.compile("\\+(\\d+)");
        matcher = pattern.matcher(diffLine);
        if (matcher.find()) {
            newLineNumber = Integer.parseInt(matcher.group(1));
        }
        List <Integer> list = new ArrayList<>();
        list.add(oldLineNumber);
        list.add(newLineNumber);
        return list;
    }

    public String getURLFromUsername(String username){
        if(username == null){
            return null;
        }
        else{
            return gitlabWebsiteURL + username;
        }
    }

    public List<Integer> lastTwoNumbersInLineCode(String input) {
        if(input == null){
            return null;
        }
        String[] parts = input.split("_");
        if(parts.length<2){
            return null;
        }
        String secondLastNumber = parts[parts.length - 2];
        String lastNumber = parts[parts.length - 1];
        List<Integer> numbers = new ArrayList<>();
        numbers.add(Integer.parseInt(secondLastNumber));
        numbers.add(Integer.parseInt(lastNumber));
        return numbers;
    }

    public String getGitLabHandleGivenGitLabAccessToken(String gitlabAccessToken){

        if(gitlabAccessToken == null){
            return null;
        }
        try{
            URL url = new URL(gitLabApiURL+"user");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("PRIVATE-TOKEN", gitlabAccessToken);
            Integer responseCode = connection.getResponseCode();
            InputStream inputStream =(InputStream) connection.getContent();
            InputStreamReader reader = new InputStreamReader(inputStream);
            BufferedReader input = new BufferedReader(reader);
            String inputString;
            String output = "";
            while((inputString = input.readLine())!=null){
                output += inputString;
            }
            ObjectMapper obj = new ObjectMapper();
            ObjectNode objectNode =  obj.readValue(output, ObjectNode.class);
            System.out.println("Response Code of this call " + responseCode);
            return objectNode.get("username").asText();
        }
        catch (Exception exception){
            System.out.println(exception.getMessage());
        }
        return null;

    }

    public List<String> getEmailsFromIDs(List<Integer> IDs){

        List<String> emails = new ArrayList<>();
        for(Integer ID : IDs){
            String email = getEmailFromID(ID);
            if(email!=null){
                emails.add(email);
            }
        }
        return emails;
    }

}
