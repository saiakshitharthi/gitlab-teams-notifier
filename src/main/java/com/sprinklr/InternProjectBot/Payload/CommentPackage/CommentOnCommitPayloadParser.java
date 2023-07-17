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

public class CommentOnCommitPayloadParser extends CommentPayloadParser {
    private String commitAuthorHandle;
    private String commitTitle;
    public CommentOnCommitPayloadParser(GitLabAPI gitlabAPI, DataAccessLayer dataAccessLayer){
        this.gitlabAPI = gitlabAPI;
        this.dataAccessLayer = dataAccessLayer;
    }

    @Override
    public void parsePayload(Payload payload) {
        if(this.isValidPayload(payload)){
            if(payload.getCommit() == null){
                this.isValidPayload = false;
                return;
            }
            super.parsePayload(payload);
            this.commitAuthorHandle = gitlabAPI.getUsernameFromEmail(payload.getCommit().getAuthor().getEmail());
            this.commitTitle = payload.getCommit().getTitle();
            this.discussionThreadURL = "https://gitlab.com/api/v4/projects/"+payload.getProject().getId()+ "/repository/commits/"
                    + payload.getCommit().getId()+ "/discussions/"+this.discussionId;
            dataAccessLayer.pushHandleIntoDiscussion(this.commentAuthorHandle, this.discussionId);
            dataAccessLayer.pushLastCommentIntoDiscussion(HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName) +" : "+ this.description, this.discussionId);
        }


    }


    @Override
    public List<Message> getMessagesToBeSent() {
        Set<String> allReceivers = getAllReceivers();
        List<Message> messages = new ArrayList<>();
        if(this.isValidPayload == false){
            return messages;
        }
        MessageBody messageBodyForMentions = constructMessageBodyForMentions();
        MessageBody messageBodyForDiscussionThread = constructMessageBodyForDiscussionThread();
        MessageBody messageBodyForCommmitAuthor = constructMessageBodyForCommitAuthor();
        for (String s: this.mentionsHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForMentions));
            }
        }
        for(String s: this.membersInDiscussionHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message( gitlabAPI.getEmailFromUsername(s),s, messageBodyForDiscussionThread));
            }
        }
        if(allReceivers.remove(this.commitAuthorHandle)){
            messages.add(new Message(gitlabAPI.getEmailFromUsername(this.commitAuthorHandle),this.commitAuthorHandle, messageBodyForCommmitAuthor));
        }
        return messages;
    }
    @Override
    public List<Message> getMessagesToBeSentInHtml() {
        Set<String> allReceivers = getAllReceivers();
        List<Message> messages = new ArrayList<>();
        if(this.isValidPayload == false){
            return messages;
        }
        MessageBody messageBodyForMentions = constructMessageBodyForMentionsInHtml();
        MessageBody messageBodyForDiscussionThread = constructMessageBodyForDiscussionThreadInHtml();
        MessageBody messageBodyForCommmitAuthor = constructMessageBodyForCommitAuthorInHtml();
        for (String s: this.mentionsHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s), s, messageBodyForMentions));
            }
        }
        for(String s: this.membersInDiscussionHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForDiscussionThread));
            }
        }
        if(allReceivers.remove(this.commitAuthorHandle)){
            messages.add(new Message(gitlabAPI.getEmailFromUsername(this.commitAuthorHandle),this.commitAuthorHandle, messageBodyForCommmitAuthor));
        }
        return messages;
    }



    private Set<String> getAllReceivers(){
        Set<String> allReceivers = new HashSet<>();
        allReceivers.addAll(this.membersInDiscussionHandle);
        allReceivers.add(this.commitAuthorHandle);
        allReceivers.addAll(this.mentionsHandle);
        allReceivers.remove(this.commentAuthorHandle);
        return allReceivers;
    }

    private MessageBody constructMessageBodyForDiscussionThread(){

        String title = "You got a new comment on a discussion on the commit: **"+commitTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has commented on the commit  + [" +commitTitle  +"]("
                + viewLink + ")" +" in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n" +
                " **Comment** : " + description + ".";
        String lastComment = this.lastCommentInDiscussion;
        if(lastComment!=null){
            messageToSend = messageToSend + HtmlContentCreator.newLine(2)+HtmlContentCreator.bold("Last Comment")+" : "+lastComment;
        }
        return new MessageBody(title, messageToSend);
    }
    private MessageBody constructMessageBodyForMentions(){
        String title = "You got mentioned in the comment on the commit: **"+commitTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has mentioned you in the [comment]("
                + viewLink + ")," + " in the repository ["+ this.projectName +"]("+ this.projectLink+")\\n\\n "
                + "**Comment** : " + description + ".";

        return new MessageBody(title, messageToSend);
    }
    private MessageBody constructMessageBodyForCommitAuthor(){
        String title = "You got a comment on the commit: **"+commitTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has commented on the commit  + [" +commitTitle  +"]("
                + viewLink + ")" +" in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n "
                + "**Comment** : " + description + ".";

        return new MessageBody(title, messageToSend);
    }


    private MessageBody constructMessageBodyForDiscussionThreadInHtml(){
        String title = "You got a new comment on a discussion on a commit ";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName) + " "
                + "has commented on the commit  " + HtmlContentCreator.getAnchor(viewLink, commitTitle)
                + " in the repository " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName) + HtmlContentCreator.newLine(2)
                + HtmlContentCreator.bold("Comment")+" : " + description + ".";
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_COMMIT);
        messageBody.setDiscussionThreadID(discussionId);
        messageBody.setDiscussionThread(true);
        messageBody.setDiscussionThreadID(discussionId);
        return messageBody;
    }
    private MessageBody constructMessageBodyForMentionsInHtml(){
        String title = "You got mentioned in a comment";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName) + " "
                + "has mentioned you in the " + HtmlContentCreator.getAnchor(viewLink, "comment") + " ,"
                + " in the commit " + HtmlContentCreator.getAnchor(payload.getCommit().getUrl(), commitTitle)
                + " in the repository "
                + HtmlContentCreator.getAnchor(this.projectLink, this.projectName) + HtmlContentCreator.newLine(2)
                + HtmlContentCreator.bold("Comment")+" : " + description + ".";

        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_COMMIT);
        return messageBody;
    }
    private MessageBody constructMessageBodyForCommitAuthorInHtml(){
        String title = "You got a comment in your commit";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName) + " "
                + "has commented on your commit : " + HtmlContentCreator.getAnchor(viewLink, commitTitle)
                + " in the repository " + HtmlContentCreator.getAnchor(this.projectLink, this.projectName) + HtmlContentCreator.newLine(2)
                + HtmlContentCreator.bold("Comment")+" : " + description + ".";
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_COMMIT);
        return messageBody;
    }


}
