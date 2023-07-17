// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.sprinklr.InternProjectBot.Conversation;

import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
import com.microsoft.bot.schema.ConversationReference;
import com.sprinklr.InternProjectBot.Database.Models.ConversationDetails;
import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.State;
import java.util.List;


public class ConversationReferences {

    DataAccessLayer dataAccessLayer;
    GitLabAPI gitLabAPI;

    public ConversationReferences(DataAccessLayer dataAccessLayer,GitLabAPI gitLabAPI) {
        super();
        this.dataAccessLayer = dataAccessLayer;
        this.gitLabAPI = gitLabAPI;
    }

    public void setConversationReferenceGivenConversationId(String conversationId, ConversationReference conversationReference){
        ConversationDetails conversationDetails = dataAccessLayer.getConversationDetailsGivenConversationId(conversationId);
        conversationDetails.conversationReference = conversationReference;
        conversationDetails.save();
    }

    public void pushUnsubscriptionListGivenConversationId(String conversationId, List<String> unsubscriptions){

        dataAccessLayer.pushUnsubscriptionListGivenConversationId(conversationId, unsubscriptions);
    }

    public void pushSubscriptionListGivenConversationId(String conversationId, List<String> subscriptions){

        dataAccessLayer.pushSubscriptionListGivenConversationId(conversationId, subscriptions);

    }

    public ConversationDetails getConversationDetailsGivenConversationId(String conversationId) {
        return dataAccessLayer.getConversationDetailsGivenConversationId(conversationId);

    }
    public State getStateGivenConversationId(String conversationId ){
        ConversationDetails conversationDetails = getConversationDetailsGivenConversationId(conversationId);
        if(conversationDetails == null){
            return null;
        }
        return getConversationDetailsGivenConversationId(conversationId).state;
    }

    public String getGitLabTokenGivenConversationId(String conversationId){
        State state = getStateGivenConversationId(conversationId);
        if(state == null){
            return null;
        }
        return state.getGitLabAuthenticationToken();
    }

    public String getGitLabTokenGivenGitLabHandle(String handle){
        ConversationDetails conversationDetails = getConversationDetailsGivenGitLabHandle(handle);
        if(conversationDetails == null || conversationDetails.state == null){
            return null;
        }
        return conversationDetails.state.getGitLabAuthenticationToken();
    }

    public String getGitLabHandleGivenConversationId(String conversationId){
        ConversationDetails conversationDetails = getConversationDetailsGivenConversationId(conversationId);
        if(conversationDetails == null){
            return null;
        }
        return conversationDetails.gitLabHandle;
    }

    public ConversationDetails getConversationDetailsGivenGitLabHandle(String handle){
        return dataAccessLayer.getConversationDetailsGivenGitLabHandle(handle);
    }

    public List<String> getUnsubscriptionsGivenGitLabHandle(String handle){
        return dataAccessLayer.getUnsubscriptionListGivenGitLabHandle(handle);
    }


    public List<String> getUnsubscriptionsGivenId(String conversationId){
        return dataAccessLayer.getUnsubscriptionListGivenConversationId(conversationId);
    }

    public List<String> getSubscriptionsGivenGitLabHandle(String handle){
        return dataAccessLayer.getSubscriptionListGivenGitLabHandle(handle);
    }


    public List<String> getSubscriptionsGivenId(String conversationId){
        return dataAccessLayer.getSubscriptionListGivenConversationId(conversationId);
    }

    public boolean handleLogin(String gitLabHandle, String gitLabAccessToken, ConversationReference conversationReference){
        String obtainedGitLabHandle = gitLabAPI.getGitLabHandleGivenGitLabAccessToken(gitLabAccessToken);
        String conversationId = conversationReference.getConversation().getId();
        if(obtainedGitLabHandle == null){
            return false;
        }
        if(obtainedGitLabHandle.equals(gitLabHandle)){
            ConversationDetails conversationDetails = new ConversationDetails(dataAccessLayer);
            conversationDetails.conversationId = conversationId;
            conversationDetails.state = new State();
            conversationDetails.gitLabHandle = gitLabHandle;
            conversationDetails.conversationReference = conversationReference;
            conversationDetails.state.setGitLabAuthenticationToken(gitLabAccessToken);
            conversationDetails.state.setIsLoggedin(true);
            conversationDetails.save();
            return true;
        }
        return false;

    }
    public boolean isLoggedInGivenConversationId
            (String conversationId){
        ConversationDetails conversationDetails = getConversationDetailsGivenConversationId(conversationId);
        if(conversationDetails == null || conversationDetails.state == null){
            return false;
        }

        return conversationDetails.state.getIsLoggedin();
    }

    public boolean handleLogout(String conversationId){
        if(isLoggedInGivenConversationId(conversationId)){
            dataAccessLayer.removeConversationDetailsGivenConversationId(conversationId);
            return true;
        }
        else {
            return false;
        }
    }
}
