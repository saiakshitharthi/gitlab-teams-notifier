package com.sprinklr.InternProjectBot.Payload.MergeRequestPackage;

import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.StringUtilities.MessageTypeID;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.HTML.HtmlContentCreator;
import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.MessageUtils.MessageBody;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class MergeRequestUpdatedPayloadParser extends MergeRequestPayloadParser{

    public MergeRequestUpdatedPayloadParser(GitLabAPI gitLabAPI){
        super(gitLabAPI);
    }

    @Override
    public void parsePayload(Payload payload) {
        super.parsePayload(payload);
    }



    @Override
    public List<Message> getMessagesToBeSent() {
        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }
        MessageBody messageBody = constructMessageBody();
        messageBody.setMessageTypeID(MessageTypeID.MERGE_REQUEST_UPDATED);
        Set<String> allReceivers = getAllReceivers();
        for(String s: this.assigneeHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s) ,s, messageBody));
            }

        }
        for(String s: this.reviewerHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s) ,s, messageBody));
            }
        }
        if(allReceivers.remove(this.mergeRequestAuthorHandle)){
            messages.add(new Message(gitlabAPI.getEmailFromUsername(this.mergeRequestAuthorHandle), this.mergeRequestAuthorHandle, messageBody));
        }
        return messages;
    }

    @Override
    public List<Message> getMessagesToBeSentInHtml() {
        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;

        }
        MessageBody messageBody = constructMessageBodyInHtml();
        Set<String> allReceivers = getAllReceivers();
        for(String s: this.assigneeHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s) ,s, messageBody));
            }
        }
        for(String s: this.reviewerHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBody));
            }
        }
        if(allReceivers.remove(this.mergeRequestAuthorHandle)){
            messages.add(new Message(gitlabAPI.getEmailFromUsername(this.mergeRequestAuthorHandle),this.mergeRequestAuthorHandle, messageBody));
        }
        return messages;
    }

    private Set<String> getAllReceivers(){
        Set<String> allReceivers = new HashSet<>();
        allReceivers.addAll(this.assigneeHandles);
        allReceivers.addAll(this.reviewerHandles);
        allReceivers.add(this.mergeRequestAuthorHandle);
        allReceivers.remove(this.authorHandle);
        return allReceivers;
    }

    private MessageBody constructMessageBody(){

        String title = "Merge request :" + this.mergeRequestTitle
                + " has updated";
        String messageToSend = "Hello! , [" + this.authorName + "]("
                +  this.authorURL + ")"
                + " has updated the merge request: [" +this.mergeRequestTitle
                + "](" + this.viewLink + ") "
                +  ", in the project : [" +this.projectName+ "]("
                + this.projectLink + ").";

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyInHtml(){
        String title = "Merge request has updated";
        String messageToSend = "Hello! , " + HtmlContentCreator.getAnchor(this.authorURL, this.authorName)
                + " has updated the merge request: " + HtmlContentCreator.getAnchor(this.viewLink, this.mergeRequestTitle)
                + ", in the project : " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName);
        return new MessageBody(title, messageToSend);
    }
}
