package com.sprinklr.InternProjectBot.Payload.CommentPackage;

import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
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

public class CommentOnSnippetPayloadParser extends CommentPayloadParser {



    private String snippetTitle;
    private String snippetAuthorHandle;

    public CommentOnSnippetPayloadParser(GitLabAPI gitlabAPI, DataAccessLayer dataAccessLayer){
        this.gitlabAPI = gitlabAPI;
        this.dataAccessLayer = dataAccessLayer;
    }
    @Override
    public void parsePayload(Payload payload) {
        if(isValidPayload(payload)){
            super.parsePayload(payload);
            if(payload.getSnippet() == null || payload.getSnippet().getTitle() == null ){
                this.isValidPayload = false;
                return;
            }
            this.commentAuthorURL = gitlabAPI.getURLFromUsername(payload.getUser().getUsername());
            this.snippetTitle = payload.getSnippet().getTitle();
            this.snippetAuthorHandle = gitlabAPI.getHandleFromID(payload.getSnippet().getAuthor_id());
            this.discussionThreadURL = "https://gitlab.com/api/v4/projects/"+payload.getProject().getId()+ "/snippets/"
                    + payload.getSnippet().getId()+ "/discussions/"+this.discussionId;
            dataAccessLayer.pushHandleIntoDiscussion(this.commentAuthorHandle,this.discussionId );

        }

    }

    @Override
    public List<Message> getMessagesToBeSent() {

        Set<String> allReceivers = getAllReceivers();
        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }

        MessageBody messageBodyForMentions = constructMessageBodyForMentions();
        MessageBody messageBodyForSnippetAuthor = constructMessageBodyForSnippetAuthor();
        for (String s: this.mentionsHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s), s, messageBodyForMentions));
            }
        }
        if(allReceivers.remove(this.snippetAuthorHandle)){
            messages.add(new Message(gitlabAPI.getEmailFromUsername(this.snippetAuthorHandle),this.snippetAuthorHandle, messageBodyForSnippetAuthor));
        }

        return messages;
    }
    @Override
    public List<Message> getMessagesToBeSentInHtml(){
        Set<String> allReceivers = getAllReceivers();
        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }
        MessageBody messageBodyForMentions = constructMessageBodyForMentionsInHtml();
        MessageBody messageBodyForSnippetAuthor = constructMessageBodyForSnippetAuthorInHtml();
        for (String s: this.mentionsHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s) ,s, messageBodyForMentions));
            }
        }
        if(allReceivers.remove(this.snippetAuthorHandle)){
            messages.add(new Message(gitlabAPI.getEmailFromUsername(this.snippetAuthorHandle),this.snippetAuthorHandle, messageBodyForSnippetAuthor));
        }
        return messages;
    }

    private Set<String> getAllReceivers(){
        Set<String> allReceivers = new HashSet<>();
        allReceivers.addAll(this.mentionsHandle);
        allReceivers.add(this.snippetAuthorHandle);
        allReceivers.remove(this.commentAuthorHandle);
        return allReceivers;
    }

    private MessageBody constructMessageBodyForMentions(){
        String title = "You got mentioned in the comment on the Snippet: **"+snippetTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has mentioned you in the comment"
                + " made on the [Snippet](" + viewLink + ") in the repository ["+ this.projectName +"]("+ this.projectLink+")\\n\\n **Comment** : " + description + ".";
        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForSnippetAuthor(){
        String title = "You got a new comment in your Snippet: **"+snippetTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has commented in the Snippet,  + [" +snippetTitle+"]("
                + viewLink + ")" +" in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n **Comment** : " + description + ".";
        return new MessageBody(title, messageToSend);
    }


    private MessageBody constructMessageBodyForMentionsInHtml(){
        String title = "You got mentioned in a comment";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has mentioned you in the comment"
                + " made on the " + HtmlContentCreator.getAnchor(viewLink, "Snippet") + " in the repository " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName)
                + HtmlContentCreator.newLine(2)  +HtmlContentCreator.bold("Comment")+ " : " + description + ".";
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.SNIPPET_COMMENT);
        return messageBody;
    }

    private MessageBody constructMessageBodyForSnippetAuthorInHtml(){
        String title = "You got a new comment in your Snippet: " + HtmlContentCreator.bold(snippetTitle);
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has commented in the Snippet,  " + HtmlContentCreator.getAnchor(viewLink, snippetTitle) + " in the repository " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName)
                + HtmlContentCreator.newLine(2) +HtmlContentCreator.bold("Comment")+ " : " + description + ".";
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.SNIPPET_COMMENT);
        return messageBody;

    }

}
