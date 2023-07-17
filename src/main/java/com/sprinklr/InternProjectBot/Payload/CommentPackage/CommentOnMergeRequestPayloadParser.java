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

public class CommentOnMergeRequestPayloadParser extends CommentPayloadParser {

    private String mergeRequestAuthorHandle;
    private String mergeRequestTitle;
    private List<String> assigneeHandles;

    private Boolean isNewDiscussionThread;


    public CommentOnMergeRequestPayloadParser(GitLabAPI gitlabAPI, DataAccessLayer dataAccessLayer) {
        super();
        this.gitlabAPI = gitlabAPI;
        this.dataAccessLayer = dataAccessLayer;

    }



    @Override
    public void parsePayload(Payload payload) {
        if (isValidPayload(payload)) {
            super.parsePayload(payload);
            if(payload.getUser() == null || payload.getUser().getUsername() == null || payload.getMerge_request() == null || payload.getProject() == null ){
                this.isValidPayload = false;
                return;
            }
            this.mergeRequestAuthorHandle = payload.getUser().getUsername();
            this.commentAuthorURL = gitlabAPI.getURLFromUsername(payload.getUser().getUsername());
            this.mergeRequestTitle = payload.getMerge_request().getTitle();
            this.isNewDiscussionThread = this.membersInDiscussionHandle.isEmpty();
            this.assigneeHandles = gitlabAPI.getHandleFromIDs(payload.getMerge_request().getAssignee_ids());
            this.discussionThreadURL = "https://gitlab.com/api/v4/projects/"+payload.getProject().getId()+ "/merge_requests/"
                    + payload.getMerge_request().getIid() + "/discussions/" + this.discussionId ;
            dataAccessLayer.pushHandleIntoDiscussion(this.commentAuthorHandle, this.discussionId);
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
        MessageBody messageBodyForMergeRequestAuthorAndAssignees = constructMessageBodyForMergeRequestAuthorAndAssignees();
        for (String s : this.mentionsHandle) {
            if (allReceivers.remove(s)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForMentions));
            }
        }
        for (String s : this.membersInDiscussionHandle) {
            if (allReceivers.remove(s)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForDiscussionThread));
            }
        }
        if (isNewDiscussionThread) {
            for (String s : this.assigneeHandles) {
                if (allReceivers.remove(s)) {
                    messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForMergeRequestAuthorAndAssignees));
                }
            }
            if (allReceivers.remove(mergeRequestAuthorHandle)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(mergeRequestAuthorHandle),mergeRequestAuthorHandle, messageBodyForMergeRequestAuthorAndAssignees));
            }
        }
        return messages;
    }
    @Override
    public List<Message> getMessagesToBeSentInHtml() {

        Set<String> allReceivers = getAllReceivers();
        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }
        MessageBody messageBodyForMentions = constructMessageBodyForMentionsInHtml();
        MessageBody messageBodyForDiscussionThread = constructMessageBodyForDiscussionThreadInHtml();
        MessageBody messageBodyForMergeRequestAuthorAndAssignees = constructMessageBodyForMergeRequestAuthorAndAssigneesInHtml();
        for (String s : this.mentionsHandle) {
            if (allReceivers.remove(s)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForMentions));
            }
        }
        for (String s : this.membersInDiscussionHandle) {
            if (allReceivers.remove(s)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForDiscussionThread));
            }
        }
        if (isNewDiscussionThread) {
            for (String s : this.assigneeHandles) {
                if (allReceivers.remove(s)) {
                    messages.add(new Message(gitlabAPI.getEmailFromUsername(s),s, messageBodyForMergeRequestAuthorAndAssignees));
                }
            }
            if (allReceivers.remove(mergeRequestAuthorHandle)) {
                messages.add(new Message(gitlabAPI.getEmailFromUsername(mergeRequestAuthorHandle), mergeRequestAuthorHandle, messageBodyForMergeRequestAuthorAndAssignees));
            }
        }
        return messages;
    }



    private Set<String> getAllReceivers() {
        Set<String> allReceivers = new HashSet<>();
        allReceivers.addAll(this.membersInDiscussionHandle);
        allReceivers.addAll(this.assigneeHandles);
        allReceivers.addAll(this.mentionsHandle);
        allReceivers.add(this.mergeRequestAuthorHandle);
        allReceivers.remove(this.commentAuthorHandle);
        return allReceivers;
    }

    private MessageBody constructMessageBodyForDiscussionThread() {

        String title = "You got a new comment in a discussion in Merge Request: **" + mergeRequestTitle + "**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL + ") "
                + "has commented in the discussion where you commented, in the Merge Request,  + [" + mergeRequestTitle + "]("
                + viewLink + ")" + " in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n" +
                " **Comment** : " + description + ".";
        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForMentions() {
        String title = "You got mentioned in the comment on the Merge Request: **" + mergeRequestTitle + "**";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL + ") "
                + "has mentioned you in the [comment]("
                + viewLink + ")," + " in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n **Comment** : " + description + ".";

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForMergeRequestAuthorAndAssignees() {
        String title = "You got a new comment in your Merge Request";
        String messageToSend = "[" + commentAuthorName + "](" + commentAuthorURL + ") "
                + "has commented in the Merge Request,  + [" + mergeRequestTitle + "]("
                + viewLink + ")" + " in the repository [" + this.projectName + "](" + this.projectLink + ")\\n\\n **Comment** : " + description + ".";

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyForDiscussionThreadInHtml() {
        String title = "You got a new comment in a discussion";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has commented in the discussion where you commented, in the Merge Request, " + HtmlContentCreator.getAnchor(viewLink, mergeRequestTitle)
                + " in the repository " + HtmlContentCreator.getAnchor(projectLink, projectName) + HtmlContentCreator.newLine(2)
                + " " + HtmlContentCreator.bold("Comment") + " : " + description + ".";
        String lastComment = this.lastCommentInDiscussion;
        if(lastComment!=null){
            messageToSend = messageToSend + HtmlContentCreator.newLine(2)+HtmlContentCreator.bold("Last Comment")+" : "+lastComment;
        }
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_MERGE_REQUEST);
        messageBody.setDiscussionThread(true);
        messageBody.setDiscussionThreadID(discussionId);
        messageBody.setContainsResolveThread(true);
        return messageBody;

    }

    private MessageBody constructMessageBodyForMentionsInHtml() {
        String title = "You got mentioned in a comment";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has mentioned you in the " + HtmlContentCreator.getAnchor(viewLink, "comment") + ", in the Merge Request : "+ HtmlContentCreator.getAnchor(payload.getMerge_request().getUrl(),payload.getMerge_request().getTitle())  + ", in the repository " + HtmlContentCreator.getAnchor(projectLink, projectName) + HtmlContentCreator.newLine(2)
                + " " + HtmlContentCreator.bold("Comment") + " : " + description + ".";
        String lastComment = this.lastCommentInDiscussion;
        if(lastComment!=null){
            messageToSend = messageToSend + HtmlContentCreator.newLine(2)+HtmlContentCreator.bold("Last Comment")+" : "+lastComment;
        }
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_MERGE_REQUEST);
        messageBody.setDiscussionThread(true);
        messageBody.setDiscussionThreadID(discussionId);
        messageBody.setContainsResolveThread(true);
        return messageBody;
    }

    private MessageBody constructMessageBodyForMergeRequestAuthorAndAssigneesInHtml() {
        String title = "You got a new comment in a Merge Request";
        String messageToSend = HtmlContentCreator.getAnchor(commentAuthorURL, commentAuthorName)
                + " has commented in the Merge Request, " + HtmlContentCreator.getAnchor(viewLink, mergeRequestTitle)
                + " in the repository " + HtmlContentCreator.getAnchor(projectLink, projectName) + HtmlContentCreator.newLine(2)
                + " " + HtmlContentCreator.bold("Comment") + " : " + description + ".";
        String lastComment = this.lastCommentInDiscussion;
        if(lastComment!=null){
            messageToSend = messageToSend + HtmlContentCreator.newLine(2)+HtmlContentCreator.bold("Last Comment")+" : "+lastComment;
        }
        MessageBody messageBody = new MessageBody(title, messageToSend);
        messageBody.setDiscussionThreadURL(this.discussionThreadURL);
        messageBody.setMessageTypeID(MessageTypeID.COMMENT_ON_MERGE_REQUEST);
        messageBody.setDiscussionThread(true);
        messageBody.setDiscussionThreadID(discussionId);
        messageBody.setContainsResolveThread(true);
        return messageBody;
    }

}