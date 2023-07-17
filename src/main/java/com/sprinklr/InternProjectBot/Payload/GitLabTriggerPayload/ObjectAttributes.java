package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

import java.util.ArrayList;

public class ObjectAttributes {
    private Object attachment;
    private int author_id;
    private ChangePosition change_position;
    private Object commit_id;
    private String created_at;
    private String discussion_id;
    private int id;
    private String line_code;
    private String note;
    private int noteable_id;
    private String noteable_type;
    private OriginalPosition original_position;
    private Position position;
    private int project_id;
    private Object resolved_at;
    private Object resolved_by_id;
    private Object resolved_by_push;
    private Object st_diff;
    private boolean system;
    private String type;
    private String updated_at;
    private Object updated_by_id;
    private String description;
    private String url;

    private int assignee_id;
    private Object head_pipeline_id;
    private int iid;
    private Object last_edited_at;
    private Object last_edited_by_id;
    private Object merge_commit_sha;
    private Object merge_error;
    private MergeParams merge_params;
    private String merge_status;
    private Object merge_user_id;
    private boolean merge_when_pipeline_succeeds;
    private Object milestone_id;
    private String source_branch;
    private int source_project_id;
    private int state_id;
    private String target_branch;
    private int target_project_id;
    private int time_estimate;
    private String title;
    private Source source;
    private Target target;
    private LastCommit last_commit;
    private boolean work_in_progress;
    private int total_time_spent;
    private int time_change;
    private Object human_total_time_spent;
    private String name;
    private String ref;


    public boolean tag;

    public boolean isTag() {
        return tag;
    }

    public void setTag(boolean tag) {
        this.tag = tag;
    }

    public String getSha() {
        return sha;
    }

    public void setSha(String sha) {
        this.sha = sha;
    }

    public String getBefore_sha() {
        return before_sha;
    }

    public void setBefore_sha(String before_sha) {
        this.before_sha = before_sha;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDetailed_status() {
        return detailed_status;
    }

    public void setDetailed_status(String detailed_status) {
        this.detailed_status = detailed_status;
    }

    public ArrayList<String> getStages() {
        return stages;
    }

    public void setStages(ArrayList<String> stages) {
        this.stages = stages;
    }

    public String getFinished_at() {
        return finished_at;
    }

    public void setFinished_at(String finished_at) {
        this.finished_at = finished_at;
    }

    public Object getDuration() {
        return duration;
    }

    public void setDuration(Object duration) {
        this.duration = duration;
    }

    public Object getQueued_duration() {
        return queued_duration;
    }

    public void setQueued_duration(Object queued_duration) {
        this.queued_duration = queued_duration;
    }

    public ArrayList<Object> getVariables() {
        return variables;
    }

    public void setVariables(ArrayList<Object> variables) {
        this.variables = variables;
    }

    public String sha;
    public String before_sha;

    public String status;
    public String detailed_status;
    public ArrayList<String> stages;

    public String finished_at;
    public Object duration;
    public Object queued_duration;
    public ArrayList<Object> variables;

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(int assignee_id) {
        this.assignee_id = assignee_id;
    }

    public Object getHead_pipeline_id() {
        return head_pipeline_id;
    }

    public void setHead_pipeline_id(Object head_pipeline_id) {
        this.head_pipeline_id = head_pipeline_id;
    }

    public int getIid() {
        return iid;
    }

    public void setIid(int iid) {
        this.iid = iid;
    }

    public Object getLast_edited_at() {
        return last_edited_at;
    }

    public void setLast_edited_at(Object last_edited_at) {
        this.last_edited_at = last_edited_at;
    }

    public Object getLast_edited_by_id() {
        return last_edited_by_id;
    }

    public void setLast_edited_by_id(Object last_edited_by_id) {
        this.last_edited_by_id = last_edited_by_id;
    }

    public Object getMerge_commit_sha() {
        return merge_commit_sha;
    }

    public void setMerge_commit_sha(Object merge_commit_sha) {
        this.merge_commit_sha = merge_commit_sha;
    }

    public Object getMerge_error() {
        return merge_error;
    }

    public void setMerge_error(Object merge_error) {
        this.merge_error = merge_error;
    }

    public MergeParams getMerge_params() {
        return merge_params;
    }

    public void setMerge_params(MergeParams merge_params) {
        this.merge_params = merge_params;
    }

    public String getMerge_status() {
        return merge_status;
    }

    public void setMerge_status(String merge_status) {
        this.merge_status = merge_status;
    }

    public Object getMerge_user_id() {
        return merge_user_id;
    }

    public void setMerge_user_id(Object merge_user_id) {
        this.merge_user_id = merge_user_id;
    }

    public boolean isMerge_when_pipeline_succeeds() {
        return merge_when_pipeline_succeeds;
    }

    public void setMerge_when_pipeline_succeeds(boolean merge_when_pipeline_succeeds) {
        this.merge_when_pipeline_succeeds = merge_when_pipeline_succeeds;
    }

    public Object getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(Object milestone_id) {
        this.milestone_id = milestone_id;
    }

    public String getSource_branch() {
        return source_branch;
    }

    public void setSource_branch(String source_branch) {
        this.source_branch = source_branch;
    }

    public int getSource_project_id() {
        return source_project_id;
    }

    public void setSource_project_id(int source_project_id) {
        this.source_project_id = source_project_id;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
    }

    public String getTarget_branch() {
        return target_branch;
    }

    public void setTarget_branch(String target_branch) {
        this.target_branch = target_branch;
    }

    public int getTarget_project_id() {
        return target_project_id;
    }

    public void setTarget_project_id(int target_project_id) {
        this.target_project_id = target_project_id;
    }

    public int getTime_estimate() {
        return time_estimate;
    }

    public void setTime_estimate(int time_estimate) {
        this.time_estimate = time_estimate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Source getSource() {
        return source;
    }

    public void setSource(Source source) {
        this.source = source;
    }

    public Target getTarget() {
        return target;
    }

    public void setTarget(Target target) {
        this.target = target;
    }

    public LastCommit getLast_commit() {
        return last_commit;
    }

    public void setLast_commit(LastCommit last_commit) {
        this.last_commit = last_commit;
    }

    public boolean isWork_in_progress() {
        return work_in_progress;
    }

    public void setWork_in_progress(boolean work_in_progress) {
        this.work_in_progress = work_in_progress;
    }

    public int getTotal_time_spent() {
        return total_time_spent;
    }

    public void setTotal_time_spent(int total_time_spent) {
        this.total_time_spent = total_time_spent;
    }

    public int getTime_change() {
        return time_change;
    }

    public void setTime_change(int time_change) {
        this.time_change = time_change;
    }

    public Object getHuman_total_time_spent() {
        return human_total_time_spent;
    }

    public void setHuman_total_time_spent(Object human_total_time_spent) {
        this.human_total_time_spent = human_total_time_spent;
    }

    public Object getHuman_time_change() {
        return human_time_change;
    }

    public void setHuman_time_change(Object human_time_change) {
        this.human_time_change = human_time_change;
    }

    public Object getHuman_time_estimate() {
        return human_time_estimate;
    }

    public void setHuman_time_estimate(Object human_time_estimate) {
        this.human_time_estimate = human_time_estimate;
    }

    public ArrayList<Integer> getAssignee_ids() {
        return assignee_ids;
    }

    public void setAssignee_ids(ArrayList<Integer> assignee_ids) {
        this.assignee_ids = assignee_ids;
    }

    public ArrayList<Integer> getReviewer_ids() {
        return reviewer_ids;
    }

    public void setReviewer_ids(ArrayList<Integer> reviewer_ids) {
        this.reviewer_ids = reviewer_ids;
    }

    public ArrayList<Object> getLabels() {
        return labels;
    }

    public void setLabels(ArrayList<Object> labels) {
        this.labels = labels;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isBlocking_discussions_resolved() {
        return blocking_discussions_resolved;
    }

    public void setBlocking_discussions_resolved(boolean blocking_discussions_resolved) {
        this.blocking_discussions_resolved = blocking_discussions_resolved;
    }

    public boolean isFirst_contribution() {
        return first_contribution;
    }

    public void setFirst_contribution(boolean first_contribution) {
        this.first_contribution = first_contribution;
    }

    public String getDetailed_merge_status() {
        return detailed_merge_status;
    }

    public void setDetailed_merge_status(String detailed_merge_status) {
        this.detailed_merge_status = detailed_merge_status;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    private Object human_time_change;
    private Object human_time_estimate;
    private ArrayList<Integer> assignee_ids;
    private ArrayList<Integer> reviewer_ids;
    private ArrayList<Object> labels;
    private String state;
    private boolean blocking_discussions_resolved;
    private boolean first_contribution;
    private String detailed_merge_status;
    private String action;
    private Object getAttachment() {
        return attachment;
    }

    public void setAttachment(Object attachment) {
        this.attachment = attachment;
    }

    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public ChangePosition getChange_position() {
        return change_position;
    }

    public void setChange_position(ChangePosition change_position) {
        this.change_position = change_position;
    }

    public Object getCommit_id() {
        return commit_id;
    }

    public void setCommit_id(Object commit_id) {
        this.commit_id = commit_id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDiscussion_id() {
        return discussion_id;
    }

    public void setDiscussion_id(String discussion_id) {
        this.discussion_id = discussion_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLine_code() {
        return line_code;
    }

    public void setLine_code(String line_code) {
        this.line_code = line_code;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getNoteable_id() {
        return noteable_id;
    }

    public void setNoteable_id(int noteable_id) {
        this.noteable_id = noteable_id;
    }

    public String getNoteable_type() {
        return noteable_type;
    }

    public void setNoteable_type(String noteable_type) {
        this.noteable_type = noteable_type;
    }

    public OriginalPosition getOriginal_position() {
        return original_position;
    }

    public void setOriginal_position(OriginalPosition original_position) {
        this.original_position = original_position;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public Object getResolved_at() {
        return resolved_at;
    }

    public void setResolved_at(Object resolved_at) {
        this.resolved_at = resolved_at;
    }

    public Object getResolved_by_id() {
        return resolved_by_id;
    }

    public void setResolved_by_id(Object resolved_by_id) {
        this.resolved_by_id = resolved_by_id;
    }

    public Object getResolved_by_push() {
        return resolved_by_push;
    }

    public void setResolved_by_push(Object resolved_by_push) {
        this.resolved_by_push = resolved_by_push;
    }

    public Object getSt_diff() {
        return st_diff;
    }

    public void setSt_diff(Object st_diff) {
        this.st_diff = st_diff;
    }

    public boolean isSystem() {
        return system;
    }

    public void setSystem(boolean system) {
        this.system = system;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public Object getUpdated_by_id() {
        return updated_by_id;
    }

    public void setUpdated_by_id(Object updated_by_id) {
        this.updated_by_id = updated_by_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
