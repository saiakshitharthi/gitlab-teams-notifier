package com.sprinklr.InternProjectBot.Payload.JobPackage;

import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.HTML.HtmlContentCreator;
import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.MessageUtils.MessageBody;
import com.sprinklr.InternProjectBot.StringUtilities.MessageTypeID;

import java.util.ArrayList;
import java.util.List;

public class JobSucceededPayloadParser extends JobPayloadParser {

    public JobSucceededPayloadParser(GitLabAPI gitLabAPI) {
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
        messages.add(new Message(gitlabAPI.getURLFromUsername(this.jobAuthorHandle), this.jobAuthorHandle, constructMessageBody()));
        return messages;
    }

    @Override
    public List<Message> getMessagesToBeSentInHtml() {

        List<Message> messages = new ArrayList<>();
        if(!this.isValidPayload){
            return messages;
        }
        MessageBody messageBody = constructMessageBodyInHtml();
        messageBody.setMessageTypeID(MessageTypeID.JOB_SUCCESS);
        messages.add(new Message(gitlabAPI.getEmailFromUsername(this.jobAuthorHandle),this.jobAuthorHandle, messageBody));
        return messages;
    }

    private MessageBody constructMessageBody() {

        String title = "Job " + this.jobName
                + " has **Succeeded**";
        String messageToSend = "Hello! , " + this.jobName
                + ", which ran on the branch:" + this.branchName
                + " has succeeded in the project : [" + this.projectName + "]("
                + this.projectLink + ").";

        return new MessageBody(title, messageToSend);
    }

    private MessageBody constructMessageBodyInHtml() {

        String title = "Job " + this.jobName
                + " has " + HtmlContentCreator.makeGreen("Succeeded");
        String messageToSend = "Hello! , " + HtmlContentCreator.getAnchor(this.jobLink,this.jobName)
                + " , which ran on the branch : " + HtmlContentCreator.bold(this.branchName)
                + " has  " + HtmlContentCreator.makeGreen("Succeeded") + " in the project : "
                + HtmlContentCreator.getAnchor(this.projectLink, this.projectName);

        return new MessageBody(title, messageToSend);
    }

}
