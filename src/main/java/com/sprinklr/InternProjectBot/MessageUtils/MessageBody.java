package com.sprinklr.InternProjectBot.MessageUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class MessageBody {
    private String title;
    private String text;
    private String discussionThreadURL;
    private String commentBetweenLines;
    private String messageTypeID;
    private String discussionThreadID;

    public String getDiscussionThreadID() {
        return discussionThreadID;
    }

    public void setDiscussionThreadID(String discussionThreadID) {
        this.discussionThreadID = discussionThreadID;
    }

    private boolean isDiscussionThread = false;
    private boolean containsResolveThread = false;


    public boolean isContainsResolveThread() {
        return containsResolveThread;
    }

    public void setContainsResolveThread(boolean containsResolveThread) {
        this.containsResolveThread = containsResolveThread;
    }

    public boolean isDiscussionThread() {
        return isDiscussionThread;
    }

    public void setDiscussionThread(boolean discussionThread) {
        isDiscussionThread = discussionThread;
    }

    public String getMessageTypeID() {
        return messageTypeID;
    }

    public void setMessageTypeID(String messageTypeID) {
        this.messageTypeID = messageTypeID;
    }

    public String getCommentBetweenLines() {
        return commentBetweenLines;
    }

    public void setCommentBetweenLines(String commentBetweenLines) {
        this.commentBetweenLines = commentBetweenLines;
    }

    public MessageBody(String title, String messageToSend) {
        this.title = title;
        this.text = messageToSend;
    }


    public String toJson() {

        ObjectMapper objectMapper = new ObjectMapper();
        String valueAsString = null;
        try{

            valueAsString = objectMapper.writeValueAsString(this);
        }
        catch (Exception exception){
            System.out.println("Error due to Object Mapper in converting message to Message Body: " + exception.getMessage());
        }
        return valueAsString;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }

    public void setText(String messageToSend) {
        this.text = messageToSend;
    }

    public String getDiscussionThreadURL() {
        return discussionThreadURL;
    }

    public void setDiscussionThreadURL(String discussionThreadURL) {
        this.discussionThreadURL = discussionThreadURL;
    }
}
