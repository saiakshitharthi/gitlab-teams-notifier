package com.sprinklr.InternProjectBot.MessageUtils;


public class Message {
    private String receiverEmail;
    private String receiverGitLabHandle;
    private MessageBody messageBody;

    public String getReceiverGitLabHandle() {
        return receiverGitLabHandle;
    }

    public void setReceiverGitLabHandle(String receiverGitLabHandle) {
        this.receiverGitLabHandle = receiverGitLabHandle;
    }

    public Message(String receiverEmail, String receiverGitLabHandle, MessageBody messageBody){
        this.receiverGitLabHandle = receiverGitLabHandle;
        this.receiverEmail = receiverEmail;
        this.messageBody = messageBody;
    }

    public String getReceiverEmail() {
        return receiverEmail;
    }

    public void setReceiverEmail(String receiverEmail) {
        this.receiverEmail = receiverEmail;
    }

    public MessageBody getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(MessageBody messageBody) {
        this.messageBody = messageBody;
    }

}
