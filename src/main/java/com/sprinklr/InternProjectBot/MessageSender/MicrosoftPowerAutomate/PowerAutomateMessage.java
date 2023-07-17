package com.sprinklr.InternProjectBot.MessageSender.MicrosoftPowerAutomate;

public class PowerAutomateMessage {

    private String title;
    private String receiverEmail;
    private String messageToSend;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public String getMessageToSend() {
        return messageToSend;
    }

    public void setMessageToSend(String messageToSend) {
        this.messageToSend = messageToSend;
    }
}
