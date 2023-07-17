package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

public class Position {
    private String base_sha;
    private String start_sha;
    private String head_sha;
    private String old_path;
    private String new_path;
    private String position_type;
    private int old_line;

    public String getBase_sha() {
        return base_sha;
    }

    public void setBase_sha(String base_sha) {
        this.base_sha = base_sha;
    }

    public String getStart_sha() {
        return start_sha;
    }

    public void setStart_sha(String start_sha) {
        this.start_sha = start_sha;
    }

    public String getHead_sha() {
        return head_sha;
    }

    public void setHead_sha(String head_sha) {
        this.head_sha = head_sha;
    }

    public String getOld_path() {
        return old_path;
    }

    public void setOld_path(String old_path) {
        this.old_path = old_path;
    }

    public String getNew_path() {
        return new_path;
    }

    public void setNew_path(String new_path) {
        this.new_path = new_path;
    }

    public String getPosition_type() {
        return position_type;
    }

    public void setPosition_type(String position_type) {
        this.position_type = position_type;
    }

    public int getOld_line() {
        return old_line;
    }

    public void setOld_line(int old_line) {
        this.old_line = old_line;
    }

    public Object getNew_line() {
        return new_line;
    }

    public void setNew_line(Object new_line) {
        this.new_line = new_line;
    }

    public LineRange getLine_range() {
        return line_range;
    }

    public void setLine_range(LineRange line_range) {
        this.line_range = line_range;
    }

    private Object new_line;
    private LineRange line_range;
}
