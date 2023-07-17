package com.sprinklr.InternProjectBot.Database.Models;

import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
import com.microsoft.bot.schema.ConversationReference;
import com.sprinklr.InternProjectBot.State;

public class ConversationDetails {
    private final DataAccessLayer dataAccessLayer;


    public String conversationId;
    public String gitLabHandle;
    public State state;
    public ConversationReference conversationReference;
    public ConversationDetails(DataAccessLayer dataAccessLayer){
        this.dataAccessLayer = dataAccessLayer;
        this.state = new State();

    }
    public void save(){
        System.out.println("saving conversation details");
        System.out.println(dataAccessLayer);
        dataAccessLayer.saveConversationDetails(this);
    }

}
