package com.sprinklr.InternProjectBot.Database;

/*
    * This class is the implementation of the DataAccessLayer in MongoDB.
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.*;
import com.sprinklr.InternProjectBot.Database.Models.*;
import com.sprinklr.InternProjectBot.State;
import com.microsoft.bot.schema.ConversationReference;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Repository
public class DataAccessLayerImplMongoDBAtlas implements DataAccessLayer {

    @Value("${connectionString}")
    private String connectionString;
    @Value("${subscriptionCount}")
    private int subscriptionCount;

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> DiscussionThreadCollection;
    private MongoCollection<Document> ConversationDetailsCollection;
    private MongoCollection<Document> UsersCollection;
    private MongoCollection<Document> UnsubscribedUsersCollection;
    private MongoCollection<Document> UnsubscriptionsOfUsersCollection;
    private ObjectMapper objectMapper;

    /*
        * This method is called after the object is created (All the dependencies are injected and variables are initialized).
        * It initializes the mongoClient and the database.
     */
    @PostConstruct
    public void init() {
        objectMapper = new ObjectMapper();
        try {
            mongoClient = MongoClients.create(connectionString);
            database = mongoClient.getDatabase("sampleDatabase");
            DiscussionThreadCollection = database.getCollection("DiscussionThread");
            ConversationDetailsCollection = database.getCollection("ConversationDetails");
            UsersCollection = database.getCollection("Users");
            UnsubscribedUsersCollection = database.getCollection("UnsubscribedUsers");
            UnsubscriptionsOfUsersCollection = database.getCollection("Unsubscriptions");
        } catch (Exception e) {
            System.out.println("Error in creating the mongoClient In init() method of DataAccessLayerImplMongoDBAtlas class");
        }
    }
    /*
        * Discussion Thread
     */
    @Override
    public void pushEmailIntoDiscussion(String email, String discussionID) {
        if(email==null || discussionID==null){
            return;
        }
        Document existingDoc = DiscussionThreadCollection.find(Filters.eq("discussion_id", discussionID)).first();
        Document newDoc = null;
        if (existingDoc == null) {
            List<String> userEmails = new ArrayList<>();
            userEmails.add(email);
            newDoc = new Document("emails", userEmails).append("discussion_id", discussionID);
        } else {
            Document temp = new Document("emails", email);
            DiscussionThreadCollection.updateOne(Filters.eq("discussion_id", discussionID), new Document("$addToSet", temp));
        }
        if (newDoc != null) {
            DiscussionThreadCollection.insertOne(newDoc);
        }
    }

    @Override
    public void pushHandleIntoDiscussion(String handle, String discussionID) {
        if(handle == null || discussionID == null){
            return;
        }
        Document existingDoc = DiscussionThreadCollection.find(Filters.eq("discussion_id", discussionID)).first();
        Document newDoc = null;
        if (existingDoc == null) {
            List<String> userHandles = new ArrayList<>();
            userHandles.add(handle);
            newDoc = new Document("handles", userHandles).append("discussion_id", discussionID);
        } else {
            Document temp = new Document("handles", handle);
            DiscussionThreadCollection.updateOne(Filters.eq("discussion_id", discussionID), new Document("$addToSet", temp));
        }
        if (newDoc != null) {
            DiscussionThreadCollection.insertOne(newDoc);
        }
    }

    @Override
    public void pushLastCommentIntoDiscussion(String lastComment, String discussionID) {
        if (discussionID != null && lastComment != null) {
            Document existingDoc = DiscussionThreadCollection.find(Filters.eq("discussion_id", discussionID)).first();
            if (existingDoc != null) {
                DiscussionThreadCollection.updateOne(Filters.eq("discussion_id", discussionID), new Document("$set", new Document("last_comment", lastComment)));
            }
            else{
                DiscussionThreadCollection.insertOne(new Document("discussion_id", discussionID).append("last_comment", lastComment));
            }
        }

    }

    @Override
    public String getLastCommentInDiscussion(String discussionID) {
        if(discussionID == null){
            return null;
        }
        Document existingDoc = DiscussionThreadCollection.find(Filters.eq("discussion_id", discussionID)).first();
        if (existingDoc == null) {
            return null;
        } else {
            return (String) existingDoc.get("last_comment");
        }

    }

    @Override
    public List<String> getAllEmailsInDiscussion(String discussionID) {
        if(discussionID == null){
            return new ArrayList<>();
        }
        Document existingDoc = DiscussionThreadCollection.find(Filters.eq("discussion_id", discussionID)).first();
        if (existingDoc == null) {
            return new ArrayList<>();
        } else {
            return (List<String>) existingDoc.get("emails");
        }

    }

    @Override
    public List<String> getAllHandlesInDiscussion(String discussionID) {
        if(discussionID == null){
            return new ArrayList<>();
        }
        Document existingDoc = DiscussionThreadCollection.find(Filters.eq("discussion_id", discussionID)).first();
        if (existingDoc == null) {
            return new ArrayList<>();
        } else {
            return (List<String>) existingDoc.get("handles");
        }
    }

    @Override
    public List<String> getUnsubscriptionListGivenConversationId(String conversationId) {
        if(conversationId == null){
            return new ArrayList<>();
        }

        Document existingDoc = UnsubscriptionsOfUsersCollection.find(Filters.eq("conversation_id", conversationId)).first();
        if(existingDoc == null){
            return new ArrayList<>();
        }
        else {
            return (List<String>) existingDoc.get("unsubscription_list");
        }
    }

    @Override
    public List<String> getUnsubscriptionListGivenGitLabHandle(String gitLabHandle) {
        if(gitLabHandle == null){
            return new ArrayList<>();
        }

        Document existingDoc = UnsubscriptionsOfUsersCollection.find(Filters.eq("gitlab_handle", gitLabHandle)).first();
        if(existingDoc == null){
            return new ArrayList<>();
        }
        else {
            return (List<String>) existingDoc.get("unsubscription_list");
        }
    }

    @Override
    public List<String> getSubscriptionListGivenConversationId(String conversationId) {
        List<String > unsubscriptionList = getUnsubscriptionListGivenConversationId(conversationId);
        List<String> numbers = new ArrayList<>();

        for (int i = 1; i <= this.subscriptionCount; i++) {
            numbers.add(String.valueOf(i));
        }
        numbers.removeAll(unsubscriptionList);
        return numbers;
    }

    @Override
    public List<String> getSubscriptionListGivenGitLabHandle(String gitLabHandle) {
        List<String > unsubscriptionList = getUnsubscriptionListGivenGitLabHandle(gitLabHandle);
        List<String> numbers = new ArrayList<>();

        for (int i = 1; i <= this.subscriptionCount; i++) {
            numbers.add(String.valueOf(i));
        }
        numbers.removeAll(unsubscriptionList);
        return numbers;
    }


    @Override
    public void saveConversationDetails(ConversationDetails conversationDetails) {
        if (conversationDetails == null
                || conversationDetails.conversationId == null
                || conversationDetails.conversationReference == null
                || conversationDetails.state == null
                || conversationDetails.gitLabHandle == null) {
            System.out.println("Error in saving conversation details, as you have passed null values in the conversation details");
            return;
        }
        System.out.println("This is the reply url from saveConversationDetails function in DataAccessLayerImplMongoDBAtlas " + conversationDetails.state.getDiscussionThreadURL());
        Document existingDoc = ConversationDetailsCollection.find(Filters.eq("conversation_id", conversationDetails.conversationId)).first();
        Document newDoc;
        String conversationReferenceString = null;
        String stateString = null;
        try {

            conversationReferenceString = objectMapper.writeValueAsString(conversationDetails.conversationReference);
            stateString = objectMapper.writeValueAsString(conversationDetails.state);

        } catch (Exception exception) {
            System.out.println(exception.getMessage());
            System.out.println("Error while converting conversation reference and state to string");
        }
        newDoc = new Document("conversation_id", conversationDetails.conversationId)
                .append("conversation_reference", conversationReferenceString)
                .append("state", stateString)
                .append("gitlab_handle", conversationDetails.gitLabHandle);

        if (existingDoc == null) {
            ConversationDetailsCollection.insertOne(newDoc);
        } else {
            ConversationDetailsCollection.findOneAndReplace(Filters.eq("conversation_id", conversationDetails.conversationId), newDoc);
        }
    }

    @Override
    public List<String> pushUnsubscriptionListGivenConversationId(String conversationId, List<String> unsubscriptionList) {
        System.out.println("Unsubscribing!!");
        System.out.println(unsubscriptionList);
        System.out.println(UnsubscriptionsOfUsersCollection);
        Document existingDoc = UnsubscriptionsOfUsersCollection.find(Filters.eq("conversation_id", conversationId)).first();
        List<String> existingList;
        if(existingDoc == null){
            UnsubscriptionsOfUsersCollection.insertOne(new Document("conversation_id",conversationId).append("gitlab_handle",getConversationDetailsGivenConversationId(conversationId).gitLabHandle).append("unsubscription_list",unsubscriptionList));
            return unsubscriptionList;
        }else {
            existingList = (List<String>)existingDoc.get("unsubscription_list");
        }
        Set<String> unionSet = new HashSet<>(existingList);

        unionSet.addAll(unsubscriptionList);
        List<String> unionList = new ArrayList<>(unionSet);
        UnsubscriptionsOfUsersCollection.findOneAndUpdate(Filters.eq("conversation_id",conversationId), new Document("$set", new Document("unsubscription_list",unionList)));
        return unionList;
    }

    @Override
    public List<String> pushSubscriptionListGivenGitLabHandle(String gitLabHandle, List<String> subscriptionList) {
        System.out.println("Subscribing!!");
        System.out.println(subscriptionList);
        System.out.println(UnsubscriptionsOfUsersCollection);
        Document existingDoc = UnsubscriptionsOfUsersCollection.find(Filters.eq("gitlab_handle", gitLabHandle)).first();
        List<String> existingList;
        if(existingDoc == null){
            UnsubscriptionsOfUsersCollection.insertOne(new Document("conversation_id",getConversationDetailsGivenGitLabHandle(gitLabHandle).conversationId).append("gitlab_handle",gitLabHandle).append("unsubscription_list",subscriptionList));
            return subscriptionList;
        }else {
            existingList = (List<String>)existingDoc.get("unsubscription_list");
        }
        Set<String> unionSet = new HashSet<>(existingList);
        subscriptionList.forEach(unionSet::remove);
        List<String> unionList = new ArrayList<>(unionSet);
        UnsubscriptionsOfUsersCollection.findOneAndUpdate(Filters.eq("gitlab_handle",gitLabHandle), new Document("$set", new Document("unsubscription_list",unionList)));
        return unionList;
    }

    @Override
    public List<String> pushSubscriptionListGivenConversationId(String conversationId, List<String> subscriptionList) {
        System.out.println("Subscribing!!");
        System.out.println(subscriptionList);
        System.out.println(UnsubscriptionsOfUsersCollection);
        Document existingDoc = UnsubscriptionsOfUsersCollection.find(Filters.eq("conversation_id", conversationId)).first();
        List<String> existingList;
        if(existingDoc == null){
            return subscriptionList;
        }else {
            existingList = (List<String>)existingDoc.get("unsubscription_list");
        }
        Set<String> unionSet = new HashSet<>(existingList);
        subscriptionList.forEach(unionSet::remove);
        List<String> unionList = new ArrayList<>(unionSet);
        UnsubscriptionsOfUsersCollection.findOneAndUpdate(Filters.eq("conversation_id",conversationId), new Document("$set", new Document("unsubscription_list",unionList)));
        return unionList;
    }

    @Override
    public List<String> pushUnsubscriptionListGivenGitLabHandle(String gitLabHandle, List<String> unsubscriptionList) {
        Document existingDoc = UnsubscriptionsOfUsersCollection.find(Filters.eq("gitlab_handle", gitLabHandle)).first();
        List<String> existingList;
        if(existingDoc == null){
            UnsubscriptionsOfUsersCollection.insertOne(new Document("conversation_id",getConversationDetailsGivenGitLabHandle(gitLabHandle)).append("gitlab_handle",gitLabHandle).append("unsubscription_list",unsubscriptionList));
            return unsubscriptionList;
        }else {
            existingList = (List<String>)existingDoc.get("unsubscription_list");
        }
        Set<String> unionSet = new HashSet<>(existingList);

        unionSet.addAll(unsubscriptionList);
        List<String> unionList = new ArrayList<>(unionSet);
        UnsubscriptionsOfUsersCollection.findOneAndUpdate(Filters.eq("gitlab_handle",gitLabHandle), new Document("$set", new Document("unsubscription_list",unionList)));
        return unionList;
    }

    @Override
    public ConversationDetails getConversationDetailsGivenConversationId(String conversationId) {
        Document existingDoc = ConversationDetailsCollection.find(Filters.eq("conversation_id", conversationId)).first();
        ConversationDetails conversationDetails = new ConversationDetails(this);
        if (existingDoc == null) {
            return null;
        } else {

            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            System.out.println((String) existingDoc.get("conversation_reference"));
            Gson gson = builder.create();
            conversationDetails.conversationReference = gson.fromJson((String) existingDoc.get("conversation_reference"), ConversationReference.class);
            System.out.println(conversationDetails.conversationReference);
            conversationDetails.state = gson.fromJson((String) existingDoc.get("state"), State.class);
            System.out.println(conversationDetails.state);
            conversationDetails.conversationId = (String) existingDoc.get("conversation_id");
            conversationDetails.gitLabHandle = (String) existingDoc.get("gitlab_handle");


            return conversationDetails;
        }


    }

    @Override
    public ConversationDetails getConversationDetailsGivenGitLabHandle(String gitLabHandle) {
        if (gitLabHandle == null) {
            return null;
        }
        Document existingDoc = ConversationDetailsCollection.find(Filters.eq("gitlab_handle", gitLabHandle)).first();
        ConversationDetails conversationDetails = new ConversationDetails(this);
        if (existingDoc == null) {
            return null;
        } else {
            try {
                conversationDetails.conversationId = (String) existingDoc.get("conversation_id");
                conversationDetails.conversationReference = objectMapper.readValue((String) existingDoc.get("conversation_reference"), ConversationReference.class);
                conversationDetails.state = objectMapper.readValue((String) existingDoc.get("state"), State.class);
                conversationDetails.gitLabHandle = (String) existingDoc.get("gitlab_handle");
            } catch (Exception exception) {
                System.out.println(exception.getMessage());
                System.out.println("Exception in getConversationDetailsGivenGitLabHandle in DataAccessLayerImplMongoDBAtlas class while converting from json to object");
            }
            return conversationDetails;
        }

    }

    @Override
    public void removeConversationDetailsGivenConversationId(String conversationId) {
        if (conversationId == null) {
            return;
        }
        ConversationDetailsCollection.deleteOne(Filters.eq("conversation_id", conversationId));
    }

    @Override
    public void removeConversationDetailsGivenGitLabHandle(String gitLabHandle) {
        if (gitLabHandle == null) {
            return;
        }
        ConversationDetailsCollection.deleteOne(Filters.eq("gitlab_handle", gitLabHandle));

    }

    @Override
    public String getUserEmailFromUserIdDatabase(String id) {
        if(id == null){
            return null;
        }
        System.out.println("id: " + id);

        Document existingDoc = UsersCollection.find(Filters.eq("_id", new ObjectId(id))).first();
        if (existingDoc == null) {
            System.out.println("existingDoc is null");
            return null;
        } else {
            return existingDoc.get("email").toString();
        }

    }

    @Override
    public void unsubscribeUserId(String userId) {
        String userEmail = getUserEmailFromUserIdDatabase(userId);
        unsubscribeUserEmail(userEmail);
    }

    @Override
    public void unsubscribeUserEmail(String userEmail) {
        UnsubscribedUsersCollection.insertOne(new Document("email", userEmail));

    }

    @Override
    public void subscribeUserId(String userId) {

        UnsubscribedUsersCollection.deleteOne(Filters.eq("email", getUserEmailFromUserIdDatabase(userId)));

    }
}
