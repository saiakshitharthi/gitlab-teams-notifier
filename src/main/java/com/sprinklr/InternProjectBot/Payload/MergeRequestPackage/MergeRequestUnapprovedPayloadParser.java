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

public class MergeRequestUnapprovedPayloadParser extends MergeRequestPayloadParser{

    public MergeRequestUnapprovedPayloadParser(GitLabAPI gitLabAPI){
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
        messageBody.setMessageTypeID(MessageTypeID.MERGE_REQUEST_UNAPPROVED);
        Set<String> allReceivers = getAllReceivers();

        for (String s : this.assigneeHandles) {
            if (allReceivers.remove(s)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBody));
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
        MessageBody messageBody = constructMessageBodyInHtml();
        Set<String> allReceivers = getAllReceivers();

        for (String s : this.assigneeHandles) {
            if (allReceivers.remove(s)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBody));
            }
        }

        return messages;
    }

    private Set<String> getAllReceivers() {
        Set<String> allReceivers = new HashSet<>();
        allReceivers.addAll(this.assigneeHandles);
        allReceivers.remove(this.authorHandle);
        return allReceivers;
    }

    private MessageBody constructMessageBody() {
        String title = "Merge request :" + this.mergeRequestTitle
                + " has unapproved";
        String messageToSend = "Hello! , [" + this.authorName + "]("
                + this.authorURL + ")"
                + " has unapproved the merge request: [" + this.mergeRequestTitle
                + "](" + this.viewLink + ") "
                + ", in the project : [" + this.projectName + "]("
                + this.projectLink + ").";

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyInHtml(){
        String titile = "Merge request has unapproved";
        String messageToSend = "Hello! , " + HtmlContentCreator.getAnchor(this.authorURL, this.authorName)
                + " has unapproved the merge request: " + HtmlContentCreator.getAnchor(this.viewLink, this.mergeRequestTitle)
                + ", in the project : " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName);

        return new MessageBody(titile, messageToSend);
    }
}
