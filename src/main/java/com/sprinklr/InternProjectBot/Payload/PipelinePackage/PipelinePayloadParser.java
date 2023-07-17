package com.sprinklr.InternProjectBot.Payload.PipelinePackage;

import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.Payload.PayloadParser;

public abstract class PipelinePayloadParser implements PayloadParser {

    protected String pipelineAuthorName;
    protected String pipelineAuthorURL;
    protected String pipelineAuthorHandle;
    protected String projectName;
    protected String projectLink;
    protected String pipelineId;
    protected String pipelineLink;
    protected String branchName;
    protected GitLabAPI gitlabAPI;
    protected boolean isValidPayload;

    public Boolean isValidPayload(Payload payload) {
        if(payload == null || payload.getObject_attributes() == null || payload.getUser() == null || payload.getProject() == null) {
            this.isValidPayload = false;
            return false;
        }
        else{
            this.isValidPayload = true;
            return true;
        }
    }

    public PipelinePayloadParser(GitLabAPI gitlabAPI) {
        this.gitlabAPI = gitlabAPI;
    }
    @Override
    public void parsePayload(Payload payload) {
        if(!isValidPayload(payload)){
            this.isValidPayload = false;
            return;
        }
        this.isValidPayload = true;
        this.pipelineAuthorName= payload.getUser().getName();
        this.pipelineAuthorURL = gitlabAPI.getURLFromUsername(payload.getUser().getUsername());
        this.pipelineAuthorHandle = payload.getUser().getUsername();
        this.projectName = payload.getProject().getName();
        this.projectLink = payload.getProject().getGit_http_url();
        this.pipelineId = Integer.toString(payload.getObject_attributes().getId());
        this.pipelineLink = payload.getObject_attributes().getUrl();
        this.branchName = payload.getObject_attributes().getRef();
    }
}
