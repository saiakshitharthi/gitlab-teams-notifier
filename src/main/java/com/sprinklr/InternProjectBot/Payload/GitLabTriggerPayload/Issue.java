package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

import java.util.ArrayList;

public class Issue {
    /*id": 92,
            "title": "test",
            "assignee_ids": [],
            "assignee_id": null,
            "author_id": 1,
            "project_id": 5,
            "created_at": "2015-04-12 14:53:17 UTC",
            "updated_at": "2015-04-26 08:28:42 UTC",
            "position": 0,
            "branch_name": null,
            "description": "test",
            "milestone_id": null,
            "state": "closed",
            "iid": 17,
            "labels": [
    {
        "id": 25,
            "title": "Afterpod",
            "color": "#3e8068",
            "project_id": null,
            "created_at": "2019-06-05T14:32:20.211Z",
            "updated_at": "2019-06-05T14:32:20.211Z",
            "template": false,
            "description": null,
            "type": "GroupLabel",
            "group_id": 4
    },
    {
        "id": 86,
            "title": "Element",
            "color": "#231afe",
            "project_id": 4,
            "created_at": "2019-06-05T14:32:20.637Z",
            "updated_at": "2019-06-05T14:32:20.637Z",
            "template": false,
            "description": null,
            "type": "ProjectLabel",
            "group_id": null
    }
    ]
}*/
    public int author_id;
    public Object closed_at;
    public boolean confidential;
    public String created_at;
    public String description;
    public Object discussion_locked;
    public Object due_date;
    public int id;
    public int iid;
    public Object last_edited_at;
    public Object last_edited_by_id;
    public Object milestone_id;
    public Object moved_to_id;
    public Object duplicated_to_id;
    public int project_id;
    public int relative_position;
    public int state_id;
    public int time_estimate;
    public String title;
    public String updated_at;
    public Object updated_by_id;
    public Object weight;
    public Object health_status;
    public String url;
    public int total_time_spent;
    public int time_change;
    public Object human_total_time_spent;
    public Object human_time_change;
    public Object human_time_estimate;
    public ArrayList<Integer> assignee_ids;
    public int assignee_id;
    public ArrayList<Object> labels;
    public String state;
    public String severity;


    public int getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(int author_id) {
        this.author_id = author_id;
    }

    public Object getClosed_at() {
        return closed_at;
    }

    public void setClosed_at(Object closed_at) {
        this.closed_at = closed_at;
    }

    public boolean isConfidential() {
        return confidential;
    }

    public void setConfidential(boolean confidential) {
        this.confidential = confidential;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Object getDiscussion_locked() {
        return discussion_locked;
    }

    public void setDiscussion_locked(Object discussion_locked) {
        this.discussion_locked = discussion_locked;
    }

    public Object getDue_date() {
        return due_date;
    }

    public void setDue_date(Object due_date) {
        this.due_date = due_date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Object getMilestone_id() {
        return milestone_id;
    }

    public void setMilestone_id(Object milestone_id) {
        this.milestone_id = milestone_id;
    }

    public Object getMoved_to_id() {
        return moved_to_id;
    }

    public void setMoved_to_id(Object moved_to_id) {
        this.moved_to_id = moved_to_id;
    }

    public Object getDuplicated_to_id() {
        return duplicated_to_id;
    }

    public void setDuplicated_to_id(Object duplicated_to_id) {
        this.duplicated_to_id = duplicated_to_id;
    }

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public int getRelative_position() {
        return relative_position;
    }

    public void setRelative_position(int relative_position) {
        this.relative_position = relative_position;
    }

    public int getState_id() {
        return state_id;
    }

    public void setState_id(int state_id) {
        this.state_id = state_id;
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

    public Object getWeight() {
        return weight;
    }

    public void setWeight(Object weight) {
        this.weight = weight;
    }

    public Object getHealth_status() {
        return health_status;
    }

    public void setHealth_status(Object health_status) {
        this.health_status = health_status;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getAssignee_id() {
        return assignee_id;
    }

    public void setAssignee_id(int assignee_id) {
        this.assignee_id = assignee_id;
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

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }
}
