package com.sprinklr.InternProjectBot.Database;


import com.sprinklr.InternProjectBot.Database.Models.ConversationDetails;

import java.util.List;

public interface DataAccessLayer {

    void pushEmailIntoDiscussion(String email, String discussionID);
    void pushHandleIntoDiscussion(String handle, String discussionID);
    void unsubscribeUserId(String id);
    void unsubscribeUserEmail(String email);
    void subscribeUserId(String id);
    void pushLastCommentIntoDiscussion(String lastComment, String discussionID);
    void saveConversationDetails(ConversationDetails conversationDetails);
    String getUserEmailFromUserIdDatabase(String id);
    String getLastCommentInDiscussion(String discussionID);
    List<String> pushUnsubscriptionListGivenGitLabHandle(String gitLabHandle , List<String> unsubscriptionList);
    List<String> pushUnsubscriptionListGivenConversationId(String conversationId, List<String> unsubscriptionList);
    List<String> pushSubscriptionListGivenGitLabHandle(String gitLabHandle , List<String> subscriptionList);
    List<String> pushSubscriptionListGivenConversationId(String conversationId, List<String> subscriptionList);
    List<String> getAllEmailsInDiscussion(String discussionID);
    List<String> getAllHandlesInDiscussion(String discussionID);
    List<String> getUnsubscriptionListGivenConversationId(String conversationId);
    List<String> getUnsubscriptionListGivenGitLabHandle(String gitLabHandle);
    List<String> getSubscriptionListGivenConversationId(String conversationId);
    List<String> getSubscriptionListGivenGitLabHandle(String gitLabHandle);
    ConversationDetails getConversationDetailsGivenConversationId(String conversationId);
    ConversationDetails getConversationDetailsGivenGitLabHandle(String gitLabHandle);
    void removeConversationDetailsGivenConversationId(String conversationId);
    void removeConversationDetailsGivenGitLabHandle(String gitLabHandle);
}
