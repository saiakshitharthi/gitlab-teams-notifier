package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

import java.util.ArrayList;

public class Payload {

    private String object_kind;
    private String event_type;
    private User user;
    private int project_id;
    private Project project;
    private ObjectAttributes object_attributes;
    private Repository repository;
    private MergeRequest merge_request;
    private Commit commit;
    private ArrayList<User> assignees;
    private Issue issue;
    private Snippet snippet;
    public String ref;
    public boolean tag;
    public String before_sha;
    public String sha;
    private String name;
    public int retries_count;
    public long build_id;
    public String build_name;
    public String build_stage;
    public String build_status;
    public String build_created_at;
    public Object build_started_at;
    public Object build_finished_at;
    public Object build_duration;
    public double build_queued_duration;
    public boolean build_allow_failure;
    public String build_failure_reason;
    public int pipeline_id;
    public Object runner;
    public String project_name;
    public Object environment;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public String getBefore_sha() {
        return before_sha;
    }

    public void setBefore_sha(String before_sha) {
        this.before_sha = before_sha;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public int getRetries_count() {
        return retries_count;
    }

    public void setRetries_count(int retries_count) {
        this.retries_count = retries_count;
    }

    public long getBuild_id() {
        return build_id;
    }

    public void setBuild_id(long build_id) {
        this.build_id = build_id;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getBuild_stage() {
        return build_stage;
    }

    public void setBuild_stage(String build_stage) {
        this.build_stage = build_stage;
    }

    public String getBuild_status() {
        return build_status;
    }

    public void setBuild_status(String build_status) {
        this.build_status = build_status;
    }

    public String getBuild_created_at() {
        return build_created_at;
    }

    public void setBuild_created_at(String build_created_at) {
        this.build_created_at = build_created_at;
    }

    public Object getBuild_started_at() {
        return build_started_at;
    }

    public void setBuild_started_at(Object build_started_at) {
        this.build_started_at = build_started_at;
    }

    public Object getBuild_finished_at() {
        return build_finished_at;
    }

    public void setBuild_finished_at(Object build_finished_at) {
        this.build_finished_at = build_finished_at;
    }

    public Object getBuild_duration() {
        return build_duration;
    }

    public void setBuild_duration(Object build_duration) {
        this.build_duration = build_duration;
    }

    public double getBuild_queued_duration() {
        return build_queued_duration;
    }

    public void setBuild_queued_duration(double build_queued_duration) {
        this.build_queued_duration = build_queued_duration;
    }

    public boolean isBuild_allow_failure() {
        return build_allow_failure;
    }

    public void setBuild_allow_failure(boolean build_allow_failure) {
        this.build_allow_failure = build_allow_failure;
    }

    public String getBuild_failure_reason() {
        return build_failure_reason;
    }

    public void setBuild_failure_reason(String build_failure_reason) {
        this.build_failure_reason = build_failure_reason;
    }

    public int getPipeline_id() {
        return pipeline_id;
    }

    public void setPipeline_id(int pipeline_id) {
        this.pipeline_id = pipeline_id;
    }

    public Object getRunner() {
        return runner;
    }

    public void setRunner(Object runner) {
        this.runner = runner;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public Object getEnvironment() {
        return environment;
    }

    public void setEnvironment(Object environment) {
        this.environment = environment;
    }

    public Snippet getSnippet() {
        return snippet;
    }

    public void setSnippet(Snippet snippet) {
        this.snippet = snippet;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public ArrayList<User> getAssignees() {
        return assignees;
    }

    public void setAssignees(ArrayList<User> assignees) {
        this.assignees = assignees;
    }

    public ArrayList<User> getReviewers() {
        return reviewers;
    }

    public void setReviewers(ArrayList<User> reviewers) {
        this.reviewers = reviewers;
    }

    private ArrayList<User> reviewers;

    public String getObject_kind() {
        return object_kind;
    }

    public void setObject_kind(String object_kind) {
        this.object_kind = object_kind;
    }

    public String getEvent_type() {
        return event_type;
    }

    public void setEvent_type(String event_type) {
        this.event_type = event_type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public ObjectAttributes getObject_attributes() {
        return object_attributes;
    }

    public void setObject_attributes(ObjectAttributes object_attributes) {
        this.object_attributes = object_attributes;
    }

    public Repository getRepository() {
        return repository;
    }

    public void setRepository(Repository repository) {
        this.repository = repository;
    }

    public MergeRequest getMerge_request() {
        return merge_request;
    }

    public void setMerge_request(MergeRequest merge_request) {
        this.merge_request = merge_request;
    }

    public Commit getCommit() {
        return commit;
    }

    public void setCommit(Commit commit) {
        this.commit = commit;
    }
}

