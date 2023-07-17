package com.sprinklr.InternProjectBot.Payload.CommentPackage;

import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.Payload.PayloadParser;
import com.sprinklr.InternProjectBot.StringUtilities.StringOperations;

import java.util.ArrayList;
import java.util.List;

abstract public class CommentPayloadParser implements PayloadParser {

    protected Boolean isValidPayload;
    protected String commentAuthorName;
    protected String commentAuthorURL;
    protected String commentAuthorHandle;
    protected String description;
    protected String viewLink;
    protected String projectLink;
    protected String projectName;
    protected String discussionId;
    protected String lastCommentInDiscussion;
    protected String discussionThreadURL;
    protected List<String> membersInDiscussionHandle;
    protected List<String> mentionsHandle;
    protected DataAccessLayer dataAccessLayer;
    protected GitLabAPI gitlabAPI;
    protected Payload payload;


    final void constructMentions() {
        List<String> mentionsUsernames = StringOperations.findStringsStartWithAt(this.description);
        this.mentionsHandle = new ArrayList<>();
        for (String s : mentionsUsernames) {
            this.mentionsHandle.add(s);
        }
    }

    @Override
    public void parsePayload(Payload payload) {
        System.out.println("This is payload " + payload);
        this.isValidPayload = isValidPayload(payload);
        try {
            this.payload = payload;
            this.commentAuthorName = payload.getUser().getName();
            this.commentAuthorURL = gitlabAPI.getURLFromUsername(payload.getUser().getUsername());
            this.commentAuthorHandle = payload.getUser().getUsername();
            this.description = payload.getObject_attributes().getDescription();
            this.viewLink = payload.getObject_attributes().getUrl();
            this.projectLink = payload.getProject().getHttp_url();
            this.projectName = payload.getProject().getName();
            this.membersInDiscussionHandle = dataAccessLayer
                    .getAllHandlesInDiscussion(payload.getObject_attributes().getDiscussion_id());
            this.discussionId = payload.getObject_attributes().getDiscussion_id();
            this.lastCommentInDiscussion = getLastCommentInDiscussion();
            constructMentions();
            dataAccessLayer.pushLastCommentIntoDiscussion(this.description, this.discussionId);
        }catch (Exception exception){
            System.out.println("Exception in parsing payload");
            exception.printStackTrace();
        }
    }

    @Override
    public Boolean isValidPayload(Payload payload) {
        if (payload == null) {
            return false;
        }
        if (payload.getUser() == null) {
            return false;
        }
        if (payload.getObject_attributes() == null) {
            return false;
        }
        if (payload.getProject() == null || payload.getProject().getHttp_url() == null) {
            return false;
        }
        if (payload.getObject_attributes().getDescription() == null) {
            return false;
        }
        if (payload.getObject_attributes().getUrl() == null) {
            return false;
        }
        return true;


    }

    private String getLastCommentInDiscussion() {
        if (this.discussionId != null) {
            return dataAccessLayer.getLastCommentInDiscussion(this.discussionId);
        }
        return null;

    }

}
