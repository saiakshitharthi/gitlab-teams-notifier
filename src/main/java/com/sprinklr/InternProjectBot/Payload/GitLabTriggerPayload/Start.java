package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

public class Start {
    private String line_code;
    private String type;
    private int old_line;
    private Object new_line;

    public String getLine_code() {
        return line_code;
    }

    public void setLine_code(String line_code) {
        this.line_code = line_code;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
}
