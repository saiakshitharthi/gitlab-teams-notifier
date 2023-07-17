package com.sprinklr.InternProjectBot.Controller;



import com.sprinklr.InternProjectBot.Factories.PayloadFactory;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.MessageSender.MessageSenderInterface;
import com.sprinklr.InternProjectBot.Payload.PayloadParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/gitlab")
public class GitlabController {


    @Autowired
    private PayloadFactory payloadFactory;

    @Autowired
    @Qualifier("botMessageSender")
    private MessageSenderInterface messageSender;


    @PostMapping("/")
    void receivePostRequest(@RequestBody Payload payload) {

        PayloadParser payloadParser = payloadFactory.getPayloadParser(payload);
        if(payloadParser == null){
            System.out.println("Payload not supported");
        }else{
            payloadParser.parsePayload(payload);
            List<Message> messages = payloadParser.getMessagesToBeSentInHtml();
            messageSender.sendMessages(messages,payload);
        }


    }
    @GetMapping ("/test")
    void test(){

    }
}