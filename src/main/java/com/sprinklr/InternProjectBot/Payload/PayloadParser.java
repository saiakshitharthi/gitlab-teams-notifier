package com.sprinklr.InternProjectBot.Payload;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.MessageUtils.Message;

import java.util.List;

public interface PayloadParser {
   public void parsePayload(Payload payload);
   public Boolean isValidPayload(Payload payload);
   public List<Message> getMessagesToBeSent();
   public List<Message> getMessagesToBeSentInHtml();
}
