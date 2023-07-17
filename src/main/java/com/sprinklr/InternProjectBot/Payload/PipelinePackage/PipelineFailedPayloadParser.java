package com.sprinklr.InternProjectBot.Payload.PipelinePackage;

import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.HTML.HtmlContentCreator;
import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.MessageUtils.MessageBody;
import com.sprinklr.InternProjectBot.StringUtilities.MessageTypeID;

import java.util.ArrayList;
import java.util.List;

public class PipelineFailedPayloadParser extends PipelinePayloadParser {

    public PipelineFailedPayloadParser(GitLabAPI gitLabAPI) {
        super(gitLabAPI);
    }


    @Override
    public void parsePayload(Payload payload) {
        super.parsePayload(payload);
    }

    @Override
    public List<Message> getMessagesToBeSent() {

        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }
        messages.add(new Message(gitlabAPI.getEmailFromUsername(this.pipelineAuthorHandle), this.pipelineAuthorHandle, constructMessageBody()));
        return messages;
    }

    @Override
    public List<Message> getMessagesToBeSentInHtml() {
        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }
        MessageBody messageBody = constructMessageBodyInHtml();
        messageBody.setMessageTypeID(MessageTypeID.PIPELINE_FAILED);
        messages.add(new Message(gitlabAPI.getEmailFromUsername(this.pipelineAuthorHandle), this.pipelineAuthorHandle, messageBody));
        return messages;
    }

    private MessageBody constructMessageBody() {

        String title = "Pipeline :" + this.pipelineId
                + " has **failed**";
        String messageToSend = "Hello! , Pipeline: [" + this.pipelineId + "]("
                + this.pipelineLink + "), which ran on the branch:" + this.branchName
                + " has failed in the project : [" + this.projectName + "]("
                + this.projectLink + ").";

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyInHtml() {
        String title = "Pipeline " +"#" + this.pipelineId
                + " has " + HtmlContentCreator.makeRed("failed");
        String messageToSend = "Hello! , Pipeline: " + HtmlContentCreator.getAnchor(this.pipelineLink,"#"+this.pipelineId)
                + ", which ran on the branch : " + HtmlContentCreator.bold(this.branchName)
                + " has " + HtmlContentCreator.makeRed("failed") + " in the project : "
                + HtmlContentCreator.getAnchor(this.projectLink, this.projectName) + ".";

        return new MessageBody(title, messageToSend);

    }
}
