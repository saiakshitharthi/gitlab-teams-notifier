package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

public class ChangePosition {
    private Object base_sha;
    private Object start_sha;
    private Object head_sha;
    private Object old_path;

    public Object getBase_sha() {
        return base_sha;
    }

    public void setBase_sha(Object base_sha) {
        this.base_sha = base_sha;
    }

    public Object getStart_sha() {
        return start_sha;
    }

    public void setStart_sha(Object start_sha) {
        this.start_sha = start_sha;
    }

    public Object getHead_sha() {
        return head_sha;
    }

    public void setHead_sha(Object head_sha) {
        this.head_sha = head_sha;
    }

    public Object getOld_path() {
        return old_path;
    }

    public void setOld_path(Object old_path) {
        this.old_path = old_path;
    }

    public Object getNew_path() {
        return new_path;
    }

    public void setNew_path(Object new_path) {
        this.new_path = new_path;
    }

    public String getPosition_type() {
        return position_type;
    }

    public void setPosition_type(String position_type) {
        this.position_type = position_type;
    }

    public Object getOld_line() {
        return old_line;
    }

    public void setOld_line(Object old_line) {
        this.old_line = old_line;
    }

    public Object getNew_line() {
        return new_line;
    }

    public void setNew_line(Object new_line) {
        this.new_line = new_line;
    }

    public Object getLine_range() {
        return line_range;
    }

    public void setLine_range(Object line_range) {
        this.line_range = line_range;
    }

    private Object new_path;
    private String position_type;
    private Object old_line;
    private Object new_line;
    private Object line_range;
}
