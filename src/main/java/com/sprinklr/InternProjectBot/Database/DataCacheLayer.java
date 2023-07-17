package com.sprinklr.InternProjectBot.Database;

import com.sprinklr.InternProjectBot.Database.Models.ConversationDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataCacheLayer implements DataAccessLayer{

    @Autowired
    @Qualifier("dataAccessLayerImplMongoDBAtlas")
    private DataAccessLayer dataAccessLayer;


    @Override
    public void pushEmailIntoDiscussion(String email, String discussionID) {
        dataAccessLayer.pushEmailIntoDiscussion(email,discussionID);
    }

    @Override
    public void pushHandleIntoDiscussion(String handle, String discussionID) {
        dataAccessLayer.pushHandleIntoDiscussion(handle,discussionID);
    }

    @Override
    public void unsubscribeUserId(String id) {

    }

    @Override
    public void unsubscribeUserEmail(String email) {

    }

    @Override
    public void subscribeUserId(String id) {

    }

    @Override
    public void pushLastCommentIntoDiscussion(String lastComment, String discussionID) {

    }

    @Override
    public void saveConversationDetails(ConversationDetails conversationDetails) {

    }



    @Override
    public String getUserEmailFromUserIdDatabase(String id) {
        return null;
    }

    @Override
    public String getLastCommentInDiscussion(String discussionID) {
        return null;
    }

    @Override
    public List<String> pushUnsubscriptionListGivenGitLabHandle(String gitLabHandle, List<String> unsubscriptionList) {
        return null;
    }

    @Override
    public List<String> pushUnsubscriptionListGivenConversationId(String conversationId, List<String> unsubscriptionList) {
        return null;
    }

    @Override
    public List<String> pushSubscriptionListGivenGitLabHandle(String gitLabHandle, List<String> subscriptionList) {
        return null;
    }

    @Override
    public List<String> pushSubscriptionListGivenConversationId(String conversationId, List<String> subscriptionList) {
        return null;
    }

    @Override
    public List<String> getAllEmailsInDiscussion(String discussionID) {
        return null;
    }

    @Override
    public List<String> getAllHandlesInDiscussion(String discussionID) {
        return null;
    }

    @Override
    public List<String> getUnsubscriptionListGivenConversationId(String conversationId) {
        return null;
    }

    @Override
    public List<String> getUnsubscriptionListGivenGitLabHandle(String gitLabHandle) {
        return null;
    }

    @Override
    public List<String> getSubscriptionListGivenConversationId(String conversationId) {
        return null;
    }

    @Override
    public List<String> getSubscriptionListGivenGitLabHandle(String gitLabHandle) {
        return null;
    }

    @Override
    public ConversationDetails getConversationDetailsGivenConversationId(String conversationId) {
        return null;
    }

    @Override
    public ConversationDetails getConversationDetailsGivenGitLabHandle(String gitLabHandle) {
        return null;
    }

    @Override
    public void removeConversationDetailsGivenConversationId(String conversationId) {

    }

    @Override
    public void removeConversationDetailsGivenGitLabHandle(String gitLabHandle) {

    }
}
