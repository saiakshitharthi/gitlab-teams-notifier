package com.sprinklr.InternProjectBot.MessageSender.MicrosoftPowerAutomate;

import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.MessageSender.MessageSenderInterface;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
@Component
public class MicrosoftMessageSenderPowerAutomate implements MessageSenderInterface {


    @Value("${powerAutomateURL}")
    private String powerAutomateURL;

    @Override
    public void sendMessage(Message message, Payload payload) {

        URL url;
        HttpURLConnection httpURLConnection = null;
        try {
            url = new URL(powerAutomateURL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");
            httpURLConnection.setDoOutput(true);
        } catch (Exception e) {
            System.out.println("Error in creating Initial setup in MicrosoftMessageSenderPowerAutomate: " + e.getMessage());

        }
        OutputStream outputStream = null;
        try {
            outputStream = httpURLConnection.getOutputStream();
        } catch (Exception e) {
            System.out.println("Error in getting output stream in MicrosoftMessageSenderPowerAutomate: " + e.getMessage());
        }
        PowerAutomateMessage powerAutomateMessage = new PowerAutomateMessage();
        powerAutomateMessage.setTitle(message.getMessageBody().getTitle());
        powerAutomateMessage.setReceiverEmail(message.getReceiverEmail());
        powerAutomateMessage.setMessageToSend(message.getMessageBody().getText());
        ObjectMapper objectMapper = new ObjectMapper();
        String json = null;
        try {
            json = objectMapper.writeValueAsString(powerAutomateMessage);
        } catch (Exception e) {
            System.out.println("Error in converting to json in MicrosoftMessageSenderPowerAutomate: " + e.getMessage());
        }

        try {
            outputStream.write(json.getBytes());
            outputStream.flush();
            outputStream.close();
        } catch (Exception e) {
            System.out.println("Error in writing to output stream in MicrosoftMessageSenderPowerAutomate: " + e.getMessage());
        }

        int responseCode = 0;
        try {
            responseCode = httpURLConnection.getResponseCode();
        } catch (Exception e) {
            System.out.println("Error in getting response code in MicrosoftMessageSenderPowerAutomate: " + e.getMessage());
        }
        System.out.println("Response Code : " + responseCode);

    }
    @Override
    public void sendMessages(List<Message> messages, Payload payload) {
        for (Message message : messages) {
            this.sendMessage(message, payload);
        }
    }

}
