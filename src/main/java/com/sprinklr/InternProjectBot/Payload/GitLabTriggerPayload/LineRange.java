package com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload;

public class LineRange {
    private Start start;
    private End end;

    public Start getStart() {
        return start;
    }

    public void setStart(Start start) {
        this.start = start;
    }

    public End getEnd() {
        return end;
    }

    public void setEnd(End end) {
        this.end = end;
    }
}
