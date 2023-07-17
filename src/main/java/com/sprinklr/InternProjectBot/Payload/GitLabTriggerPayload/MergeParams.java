package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

public class MergeParams {
    private String force_remove_source_branch;

    public String getForce_remove_source_branch() {
        return force_remove_source_branch;
    }

    public void setForce_remove_source_branch(String force_remove_source_branch) {
        this.force_remove_source_branch = force_remove_source_branch;
    }
}
