package com.sprinklr.InternProjectBot.Payload.MergeRequestPackage;

import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.HTML.HtmlContentCreator;
import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.MessageUtils.MessageBody;
import com.sprinklr.InternProjectBot.StringUtilities.MessageTypeID;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static com.sprinklr.InternProjectBot.HTML.HtmlContentCreator.bold;

public class MergeRequestOpenedPayloadParser extends MergeRequestPayloadParser{

    public MergeRequestOpenedPayloadParser(GitLabAPI gitLabAPI){
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
        MessageBody messageBodyForAssignee = constructMessageBodyForAssignee();
        messageBodyForAssignee.setMessageTypeID(MessageTypeID.MERGE_REQUEST_OPENED);
        MessageBody messageBodyForReviewer = constructMessageBodyForReviewer();
        messageBodyForAssignee.setMessageTypeID(MessageTypeID.MERGE_REQUEST_OPENED);
        Set<String> allReceivers = getAllReceivers();

        for(String s: this.reviewerHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForReviewer));
            }
        }
        for(String s: this.assigneeHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForAssignee));
            }

        }
        return messages;
    }

    @Override
    public List<Message> getMessagesToBeSentInHtml() {
        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }
        MessageBody messageBodyForAssignee = constructMessageBodyForAssigneeInHtml();
        MessageBody messageBodyForReviewer = constructMessageBodyForReviewerInHtml();
        Set<String> allReceivers = getAllReceivers();

        for(String s: this.reviewerHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForReviewer));
            }
        }
        for(String s: this.assigneeHandles){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForAssignee));
            }

        }
        return messages;
    }

    private Set<String> getAllReceivers(){
        Set<String> allReceivers = new HashSet<>();
        allReceivers.addAll(this.assigneeHandles);
        allReceivers.addAll(this.reviewerHandles);
        allReceivers.remove(this.authorHandle);
        return allReceivers;
    }

    private MessageBody constructMessageBodyForAssignee(){
        String title = "Merge request opened!";
        String messageToSend = "Hello! , [" + this.authorName + "]("
                +  this.authorURL + ")"
                + " has opened the [merge request](" + this.viewLink + ") "
                + "to merge from " +this.sourceBranch + " to " + this.targetBranch
                +  ", in the project link: [" +this.projectName+ "]("
                + this.projectLink + "). You role is **Assignee**.";
        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForReviewer(){
        String title = "Merge request opened!";
        String messageToSend = "Hello! , [" + this.authorName + "]("
                +  this.authorURL + ")"
                + " has opened the [merge request](" + this.viewLink + ") "
                + "to merge from " +this.sourceBranch + " to " + this.targetBranch
                +  ", in the project link: [" +this.projectName+ "]("
                + this.projectLink + "). You role is **Reviewer**.";
        return new MessageBody(title, messageToSend);
    }


    private MessageBody constructMessageBodyForAssigneeInHtml(){
        String title = "Merge request opened!";
        String messageToSend = "Hello! , " + HtmlContentCreator.getAnchor(this.authorURL, this.authorName)
                + " has created a new Merge Request: " + HtmlContentCreator.getAnchor(this.viewLink, this.mergeRequestTitle)
                + " to merge from " + bold(this.sourceBranch) + " to " + bold(this.targetBranch)
                +  ", in the project link: " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName)
                + "."
                + HtmlContentCreator.newLine(1)
                + "You role is " + bold("Assignee") + "."
                + HtmlContentCreator.newLine(2)
                + HtmlContentCreator.bold("Description: ") + this.payload.getObject_attributes().getDescription();

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForReviewerInHtml(){
        String title = "Merge request opened!";
        String messageToSend = "Hello! , "+ HtmlContentCreator.getAnchor(this.authorURL, this.authorName)
                + " has created a new Merge Request: " + HtmlContentCreator.getAnchor(this.viewLink, this.mergeRequestTitle)
                + "to merge from " +bold(this.sourceBranch) + " to " + bold(this.targetBranch)
                +  ", in the project link: " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName)
                + "."
                + HtmlContentCreator.newLine(1)
                + "You role is "+ bold("Reviewer") + "."
                + HtmlContentCreator.newLine(2)
                + HtmlContentCreator.bold("Description: ") + this.payload.getObject_attributes().getDescription();
        return new MessageBody(title, messageToSend);
    }
}
