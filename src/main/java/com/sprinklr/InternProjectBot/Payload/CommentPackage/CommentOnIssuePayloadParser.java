package com.sprinklr.InternProjectBot.Payload.CommentPackage;

import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
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

public class CommentOnIssuePayloadParser extends CommentPayloadParser {



    private String issueAuthorHandle;
    private String issueTitle;
    private Boolean isNewDiscussionThread;
    private List<String> assigneeHandles;

    public CommentOnIssuePayloadParser(GitLabAPI gitlabAPI, DataAccessLayer dataAccessLayer){
        this.gitlabAPI = gitlabAPI;
        this.dataAccessLayer = dataAccessLayer;
    }

    @Override
    public void parsePayload(Payload payload) {
        if(isValidPayload(payload)){
            super.parsePayload(payload);
            if(payload.getIssue() == null){
                this.isValidPayload = false;
                return;
            }
            this.issueAuthorHandle = gitlabAPI.getHandleFromID(payload.getIssue().getAuthor_id());
            this.issueTitle = payload.getIssue().getTitle();
            this.isNewDiscussionThread = (this.membersInDiscussionHandle.size() == 0);
            this.assigneeHandles = gitlabAPI.getHandleFromIDs(payload.getIssue().getAssignee_ids());
            this.discussionThreadURL = "https://gitlab.com/api/v4/projects/"+payload.getProject().getId()+ "/issues/"
                    + payload.getIssue().getIid()+ "/discussions/"+this.discussionId;
            dataAccessLayer.pushHandleIntoDiscussion(this.commentAuthorHandle,this.discussionId );
            dataAccessLayer.pushLastCommentIntoDiscussion(HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName) +" : "+ this.description, this.discussionId);
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
        MessageBody messageBodyForDiscussionThread = constructMessageBodyForDiscussionThread();
        MessageBody messageBodyForIssueAuthorAndAssignees = constructMessageBodyForIssueAuthorAndAssignees();
        for (String s: this.mentionsHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForMentions));
            }
        }

        if(isNewDiscussionThread){
            for(String s: this.assigneeHandles){
                if(allReceivers.remove(s)){
                    messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForIssueAuthorAndAssignees));
                }
            }
            if(allReceivers.remove(issueAuthorHandle)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(issueAuthorHandle),issueAuthorHandle, messageBodyForIssueAuthorAndAssignees));
            }
        }
        else{
            for(String s: this.membersInDiscussionHandle){
                if(allReceivers.remove(s)){
                    messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForDiscussionThread));
                }
            }
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
        MessageBody messageBodyForMentionsInHtml = constructMessageBodyForMentionsInHtml();
        MessageBody messageBodyForDiscussionThreadInHtml = constructMessageBodyForDiscussionThreadInHtml();
        MessageBody messageBodyForIssueAuthorAndAssigneesInHtml = constructMessageBodyForIssueAuthorAndAssigneesInHtml();
        for (String s: this.mentionsHandle){
            if(allReceivers.remove(s)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForMentionsInHtml));
            }
        }

        if(isNewDiscussionThread){
            for(String s: this.assigneeHandles){
                if(allReceivers.remove(s)){
                    messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForIssueAuthorAndAssigneesInHtml));
                }
            }
            if(allReceivers.remove(issueAuthorHandle)){
                messages.add(new Message(gitlabAPI.getEmailFromUsername(issueAuthorHandle), issueAuthorHandle, messageBodyForIssueAuthorAndAssigneesInHtml));
            }
        }
        else{
            for(String s: this.membersInDiscussionHandle){
                if(allReceivers.remove(s)){
                    messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForDiscussionThreadInHtml));
                }
            }
        }
        return messages;
    }

    private Set<String> getAllReceivers(){
        Set<String> allReceivers = new HashSet<>();

            allReceivers.addAll(this.membersInDiscussionHandle);
            allReceivers.addAll(this.mentionsHandle);
            allReceivers.add(this.issueAuthorHandle);
            allReceivers.remove(this.commentAuthorHandle);


        return allReceivers;
    }
    private MessageBody constructMessageBodyForDiscussionThread(){

        String title = "You got a new comment in a discussion in Issue: **"+issueTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has commented in the discussion where you commented, in the Issue,  + [" +issueTitle  +"]("
                + viewLink + ")" +" in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n" +
                " **Comment** : " + description + ".";
        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForMentions(){
        String title = "You got mentioned in the comment on the Issue: **"+issueTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has mentioned you in the comment, in the [" +issueTitle+ "]"
                + viewLink + ")," + " in the repository ["+ this.projectName +"]("+ this.projectLink+")\\n\\n **Comment** : " + description + ".";

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForIssueAuthorAndAssignees(){
        String title = "You got a new comment in your Issue **"+issueTitle+"**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL +") "
                + "has commented in the Issue,  + [" +issueTitle+"]("
                + viewLink + ")" +" in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n **Comment** : " + description + ".";
        return new MessageBody(title, messageToSend);
    }


    private MessageBody constructMessageBodyForDiscussionThreadInHtml(){

        String title = "You got a new comment in a discussion in an Issue";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has commented in the discussion where you commented, in the Issue, "+HtmlContentCreator.getAnchor(viewLink,issueTitle)
                +" in the repository "+HtmlContentCreator.getAnchor(projectLink,projectName)+ HtmlContentCreator.newLine(2)
                + " "+HtmlContentCreator.bold("Comment")+" : " + description + ".";
        String lastComment = this.lastCommentInDiscussion;
        if(lastComment!=null){
            messageToSend = messageToSend + HtmlContentCreator.newLine(2)+HtmlContentCreator.bold("Last Comment")+" : "+lastComment;
        }
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_ISSUE);
        messageBody.setDiscussionThread(true);
        messageBody.setDiscussionThreadID(discussionId);
        return messageBody;
    }

    private MessageBody constructMessageBodyForMentionsInHtml(){
        String title = "You got mentioned in the comment on an Issue: "+ HtmlContentCreator.bold(issueTitle);
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has mentioned you in the comment, in the issue : "+HtmlContentCreator.getAnchor(viewLink,issueTitle)
                +", in the repository "+HtmlContentCreator.getAnchor(projectLink,projectName)+HtmlContentCreator.newLine(2)
                + HtmlContentCreator.bold("Comment")+" : " + description + ".";
        String lastComment = this.lastCommentInDiscussion;
        if(lastComment!=null){
            messageToSend = messageToSend + HtmlContentCreator.newLine(2)+HtmlContentCreator.bold("Last Comment")+" : "+lastComment;
        }
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_ISSUE);
        messageBody.setDiscussionThread(true);
        messageBody.setDiscussionThreadID(discussionId);
        return messageBody;
    }

    private MessageBody constructMessageBodyForIssueAuthorAndAssigneesInHtml(){

        String title = "You got a new comment in your Issue ";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has commented in the Issue, "+HtmlContentCreator.getAnchor(viewLink,issueTitle)
                +" in the repository "+HtmlContentCreator.getAnchor(projectLink,projectName)+HtmlContentCreator.newLine(2)
                + HtmlContentCreator.bold("Comment")+" : " + description + ".";
        String lastComment = this.lastCommentInDiscussion;
        if(lastComment!=null){
            messageToSend = messageToSend + HtmlContentCreator.newLine(2)+HtmlContentCreator.bold("Last Comment")+" : "+lastComment;
        }
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_ISSUE);
        messageBody.setDiscussionThread(true);
        messageBody.setDiscussionThreadID(discussionId);
        return messageBody;
    }


}
