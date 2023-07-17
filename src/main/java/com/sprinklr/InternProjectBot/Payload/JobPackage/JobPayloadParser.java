package com.sprinklr.InternProjectBot.Payload.JobPackage;

import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.Payload.PayloadParser;

public abstract class JobPayloadParser implements PayloadParser {
    protected String jobAuthorName;
    protected String jobAuthorURL;
    protected String jobAuthorHandle;
    protected String projectName;
    protected String projectLink;
    protected String jobName;
    protected String branchName;
    protected String jobLink;
    protected GitLabAPI gitlabAPI;
    protected Payload payload;
    protected boolean isValidPayload;


    public Boolean isValidPayload(Payload payload) {
        if(payload == null || payload.getUser() == null || payload.getRepository() == null) {
            this.isValidPayload = false;
            return false;
        }
        else{
            this.isValidPayload = true;
            return true;
        }
    }
    public JobPayloadParser(GitLabAPI gitlabAPI) {
        this.gitlabAPI = gitlabAPI;
    }
    @Override
    public void parsePayload(Payload payload) {
        if(!isValidPayload(payload)){
            this.isValidPayload = false;
            return;
        }
        this.isValidPayload = true;
        this.jobAuthorName = payload.getUser().getName();
        this.jobAuthorURL = gitlabAPI.getURLFromUsername(payload.getUser().getUsername());
        this.jobAuthorHandle = payload.getUser().getUsername();
        this.projectName = payload.getRepository().getName();
        this.projectLink = payload.getRepository().getHomepage();
        this.jobName = payload.getBuild_name();
        this.branchName = payload.getRef();
        this.payload = payload;
        this.jobLink = this.projectLink + "/-/jobs/" + this.payload.getBuild_id();
        System.out.println(this.jobLink);
    }
}
