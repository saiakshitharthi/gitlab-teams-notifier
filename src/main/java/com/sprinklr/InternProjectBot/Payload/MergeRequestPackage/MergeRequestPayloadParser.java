package com.sprinklr.InternProjectBot.Payload.MergeRequestPackage;

import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.Payload.PayloadParser;

import java.util.List;

public abstract class MergeRequestPayloadParser implements PayloadParser {

    protected Boolean isValidPayload;
    protected String authorName;
    protected String authorURL;
    protected String authorHandle;
    protected String mergeRequestAuthorHandle;
    protected String viewLink;
    protected String projectLink;
    protected String projectName;
    protected String sourceBranch;
    protected String targetBranch;
    protected String mergeRequestTitle;
    protected GitLabAPI gitlabAPI;
    protected Payload payload;
    protected List<String> assigneeHandles;
    protected List<String> reviewerHandles;

    public MergeRequestPayloadParser(GitLabAPI gitlabAPI) {
        this.gitlabAPI =  gitlabAPI;
    }
    public Boolean isValidPayload(Payload payload){
        if(payload == null || payload.getUser() == null || payload.getObject_attributes() == null || payload.getProject() == null){
            this.isValidPayload=  false;
            return false;
        }
        this.isValidPayload = true;
        return true;
    }

    @Override
    public void parsePayload(Payload payload) {
        if(!isValidPayload(payload)){
            this.isValidPayload = false;
            return;
        }

        this.payload = payload;
        this.authorName = payload.getUser().getName();
        this.authorURL = gitlabAPI.getURLFromUsername(payload.getUser().getUsername());
        this.authorHandle = payload.getUser().getUsername();
        this.mergeRequestAuthorHandle = gitlabAPI.getHandleFromID(payload.getObject_attributes().getAuthor_id());
        this.viewLink = payload.getObject_attributes().getUrl();
        this.projectLink = payload.getProject().getHttp_url();
        this.projectName = payload.getProject().getName();
        this.sourceBranch = payload.getObject_attributes().getSource_branch();
        this.targetBranch = payload.getObject_attributes().getTarget_branch();
        this.mergeRequestTitle = payload.getObject_attributes().getTitle();
        this.assigneeHandles = gitlabAPI.getHandleFromIDs(payload.getObject_attributes().getAssignee_ids());
        this.reviewerHandles = gitlabAPI.getHandleFromIDs(payload.getObject_attributes().getReviewer_ids());
    }
}
