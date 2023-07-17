package com.sprinklr.InternProjectBot.MessageSender;

import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;

import java.util.List;

public interface MessageSenderInterface {

    void sendMessage(Message message, Payload payload);
    void sendMessages(List<Message> messages, Payload payloadx);
}
