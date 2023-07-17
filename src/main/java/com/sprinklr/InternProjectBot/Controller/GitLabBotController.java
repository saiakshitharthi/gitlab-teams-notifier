package com.sprinklr.InternProjectBot.Controller;

import com.codepoetics.protonpack.collectors.CompletableFutures;
import com.microsoft.bot.builder.ActivityHandler;
import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.builder.TurnContext;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ChannelAccount;
import com.microsoft.bot.schema.ConversationReference;
import com.sprinklr.InternProjectBot.Cards.Cards;
import com.sprinklr.InternProjectBot.Conversation.ConversationReferences;
import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.StringUtilities.StaticMessages;
import com.sprinklr.InternProjectBot.StringUtilities.StringOperations;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class GitLabBotController extends ActivityHandler {

    /*
        * Port on which the bot is running.
     */
    @Value("${server.port:3978}")
    private int port;

    private ConversationReferences conversationReferences;
    private GitLabAPI gitLabAPI;
    private DataAccessLayer dataAccessLayer;

    public GitLabBotController(ConversationReferences withReferences, GitLabAPI withGitLabAPI, DataAccessLayer dataAccessLayer) {
        conversationReferences = withReferences;
        this.gitLabAPI = withGitLabAPI;
        this.dataAccessLayer = dataAccessLayer;
    }
    /*
        * This method is called whenever there is an activity from the user, like a message or a button click.
     */
    @Override
    protected CompletableFuture<Void> onMessageActivity(TurnContext turnContext) {



        String conversationId = turnContext.getActivity().getConversation().getId();
        ConversationReference conversationReference = turnContext.getActivity().getConversationReference();
        if (turnContext.getActivity().getValue() != null) {
            LinkedHashMap<String, String> hashMap = (LinkedHashMap<String, String>) turnContext.getActivity().getValue();

            boolean isReplyButton = "reply".equals(hashMap.get("button"));
            boolean isResolveThreadButton = "resolveThread".equals(hashMap.get("button"));
            boolean isUnsubscribeButton = "unsubscribe".equals(hashMap.get("button"));
            boolean isSubscribeButton = "subscribe".equals(hashMap.get("button"));
            boolean isLoginButton = "login".equals(hashMap.get("button"));

            if (isReplyButton) {
                // Handle reply
                return handleReplyButton(turnContext, conversationId, hashMap);
            } else if (isResolveThreadButton) {
                // Handle resolving thread
                return handleResolveThreadButton(turnContext, conversationId);
            } else if (isUnsubscribeButton) {
                // Handle unsubscribe
                return handleUnsubscribeButton(turnContext, conversationId, hashMap);
            } else if (isSubscribeButton) {
                // Handle subscribe
                return handleSubscribeButton(turnContext, conversationId, hashMap);
            } else if (isLoginButton) {
                // Handle login
                return handleLoginButton(turnContext, hashMap, conversationReference);
            } else {
                return turnContext.sendActivity(MessageFactory.text(StaticMessages.invalidInput)).thenApply(sendResult -> null);
            }
        }

        String inputText = turnContext.getActivity().getText();
        List<String> firstAndLastWords = StringOperations.getTheFirstAndLastWords(inputText);
        String firstWord = firstAndLastWords.get(0);
        String lastWord = firstAndLastWords.get(1);


        switch (firstWord) {
            case "help":
                // Handle help command
                return handleHelpCommand(turnContext, lastWord);
            case "get_token":
                // Handle get_token command
                return handleGetTokenCommand(turnContext, conversationId, lastWord);
            case "get_handle":
                // Handle get_handle command
                return handleGetHandleCommand(turnContext, conversationId, lastWord);
            case "unsubscribe":
                // Handle unsubscribe command
                return handleUnsubscribeCommand(turnContext, conversationId);
            case "subscribe":
                // Handle subscribe command
                return handleSubscribeCommand(turnContext, conversationId);
            case "login":
                // Handle login command
                return handleLoginCommand(turnContext, conversationId, conversationReference);
            case "logout":
                // Handle logout command
                return handleLogoutCommand(turnContext, conversationId);
            default:
                return turnContext.sendActivity(MessageFactory.text(StaticMessages.invalidInput)).thenApply(sendResult -> null);
        }


    }

    private boolean isButtonInput(TurnContext turnContext){
        if(turnContext == null || turnContext.getActivity() == null || turnContext.getActivity().getValue() == null || ((LinkedHashMap<String, String>) turnContext.getActivity().getValue()).get("button") == null){

            return false;
        }
        return true;
    }

    private CompletableFuture<Void> handleReplyButton(TurnContext turnContext, String conversationId, LinkedHashMap<String, String> hashMap) {
        String discussionThreadURL = hashMap.get("discussionThreadURL");
        String reply = hashMap.get("reply");
        String discussionThreadID = hashMap.get("discussionThreadID");
        if(!conversationReferences.isLoggedInGivenConversationId(conversationId)){
            return turnContext.sendActivity(MessageFactory.text("Your access token got expired or you are not logged in!")).thenApply(sendResult -> null);
        }
        try {
            if (reply == null) {
                return turnContext.sendActivity(MessageFactory.text("Message can't be sent, something went wrong!")).thenApply(sendResult -> null);
            }
            gitLabAPI.replyToAMessage(conversationReferences.getStateGivenConversationId(conversationId).getGitLabAuthenticationToken(), discussionThreadURL, reply);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return turnContext.sendActivity(MessageFactory.text("Message can't be sent, something went wrong!")).thenApply(sendResult -> null);
        }
        return turnContext.sendActivity(MessageFactory.attachment(Cards.getNormalTextCard("Successfully sent the reply on the comment:<br><b>Comment :</b> " + dataAccessLayer.getLastCommentInDiscussion(discussionThreadID) + "<br><b>Reply sent :  </b>" + reply + "."))).thenApply(sendResult -> null);
    }

    private CompletableFuture<Void> handleResolveThreadButton(TurnContext turnContext, String conversationId) {
        try {
            gitLabAPI.resolveThread(conversationReferences.getStateGivenConversationId(conversationId).getGitLabAuthenticationToken(), conversationReferences.getStateGivenConversationId(conversationId).getDiscussionThreadURL());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return turnContext.sendActivity(MessageFactory.text("Discussion thread can't be resolved, something went wrong")).thenApply(sendResult -> null);
        }
        return turnContext.sendActivity(MessageFactory.text("Successfully resolved the thread.")).thenApply(sendResult -> null);
    }

    private CompletableFuture<Void> handleUnsubscribeButton(TurnContext turnContext, String conversationId, LinkedHashMap<String, String> hashMap) {
        List<String> unsubscribeOptions = StringOperations.splitStringIntoList(hashMap.get("unsubscribeOptions"));
        conversationReferences.pushUnsubscriptionListGivenConversationId(conversationId, unsubscribeOptions);
        return turnContext.sendActivity(MessageFactory.text(StaticMessages.unsubscribed)).thenApply(sendResult -> null);
    }

    private CompletableFuture<Void> handleSubscribeButton(TurnContext turnContext, String conversationId, LinkedHashMap<String, String> hashMap) {
        List<String> subscribeOptions = StringOperations.splitStringIntoList(hashMap.get("subscribeOptions"));
        conversationReferences.pushSubscriptionListGivenConversationId(conversationId, subscribeOptions);
        return turnContext.sendActivity(MessageFactory.text(StaticMessages.subscribed)).thenApply(sendResult -> null);
    }

    private CompletableFuture<Void> handleLoginButton(TurnContext turnContext, LinkedHashMap<String, String> hashMap, ConversationReference conversationReference) {
        String gitLabHandle = hashMap.get("gitLabHandle");
        String gitLabAuthenticationToken = hashMap.get("gitLabAuthenticationToken");

        boolean isLoggedin = conversationReferences.handleLogin(gitLabHandle, gitLabAuthenticationToken, conversationReference);
        if (isLoggedin) {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.loggedIn + gitLabHandle)).thenApply(sendResult -> null);
        } else {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.loginFailed)).thenApply(sendResult -> null);
        }
    }

    private CompletableFuture<Void> handleHelpCommand(TurnContext turnContext, String lastWord) {
        if (!lastWord.isEmpty()) {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.wrongFormat)).thenApply(sendResult -> null);
        } else {
            Activity reply = MessageFactory.attachment(Cards.getHelpHeroCard());
            return turnContext.sendActivity(reply).thenApply(sendResult -> null);
        }
    }

    private CompletableFuture<Void> handleGetTokenCommand(TurnContext turnContext, String conversationId, String lastWord) {
        if (!lastWord.isEmpty()) {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.wrongFormat)).thenApply(sendResult -> null);
        } else {
            String token = conversationReferences.getStateGivenConversationId(conversationId).getGitLabAuthenticationToken();
            if (token == null) {
                return turnContext.sendActivity(MessageFactory.text("No token set")).thenApply(sendResult -> null);
            } else {
                return turnContext.sendActivity(MessageFactory.text(token)).thenApply(sendResult -> null);
            }
        }
    }

    private CompletableFuture<Void> handleGetHandleCommand(TurnContext turnContext, String conversationId, String lastWord) {
        if (!lastWord.isEmpty()) {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.wrongFormat)).thenApply(sendResult -> null);
        } else {
            String handle = conversationReferences.getGitLabHandleGivenConversationId(conversationId);
            if (handle == null) {
                return turnContext.sendActivity(MessageFactory.text("No handle set")).thenApply(sendResult -> null);
            } else {
                return turnContext.sendActivity(MessageFactory.text(handle)).thenApply(sendResult -> null);
            }
        }
    }

    private CompletableFuture<Void> handleUnsubscribeCommand(TurnContext turnContext, String conversationId) {
        if (conversationReferences.isLoggedInGivenConversationId(conversationId)) {
            List<String> subscriptions = conversationReferences.getSubscriptionsGivenId(conversationId);
            Activity reply = MessageFactory.attachment(Cards.createAdaptiveCardForUnsubscribeList(subscriptions));
            return turnContext.sendActivity(reply).thenApply(resourceResponse -> null);
        } else {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.notLoggedIn)).thenApply(sendResult -> null);
        }
    }

    private CompletableFuture<Void> handleSubscribeCommand(TurnContext turnContext, String conversationId) {
        if (conversationReferences.isLoggedInGivenConversationId(conversationId)) {
            List<String> unsubscriptions = conversationReferences.getUnsubscriptionsGivenId(conversationId);
            Activity reply = MessageFactory.attachment(Cards.createAdaptiveCardForSubscribeList(unsubscriptions));
            return turnContext.sendActivity(reply).thenApply(resourceResponse -> null);
        } else {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.notLoggedIn)).thenApply(sendResult -> null);
        }
    }

    private CompletableFuture<Void> handleLoginCommand(TurnContext turnContext, String conversationId, ConversationReference conversationReference) {
        Activity reply = MessageFactory.attachment(Cards.getGitLabLoginCard());
        boolean alreadyLoggedIn = conversationReferences.isLoggedInGivenConversationId(conversationId);
        if (alreadyLoggedIn) {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.alreadyLoggedIn + conversationReferences.getGitLabHandleGivenConversationId(conversationId))).thenApply(sendResult -> null);
        }
        return turnContext.sendActivity(reply).thenApply(resourceResponse -> null);
    }

    private CompletableFuture<Void> handleLogoutCommand(TurnContext turnContext, String conversationId) {
        if (conversationReferences.handleLogout(conversationId)) {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.successfulLogout)).thenApply(sendResult -> null);
        } else {
            return turnContext.sendActivity(MessageFactory.text(StaticMessages.notLoggedIn)).thenApply(sendResult -> null);
        }
    }


    @Override
    protected CompletableFuture<Void> onMembersAdded(List<ChannelAccount> membersAdded, TurnContext turnContext) {
        return membersAdded.stream()
                .filter(member -> !StringUtils.equals(member.getId(), turnContext.getActivity().getRecipient().getId()))
                .map(channel -> turnContext.sendActivity(MessageFactory.text(String.format(StaticMessages.welcomeMessage, port))))
                .collect(CompletableFutures.toFutureList())
                .thenApply(resourceResponses -> null);
    }

    @Override
    protected CompletableFuture<Void> onConversationUpdateActivity(TurnContext turnContext) {
        addConversationReference(turnContext.getActivity());
        return super.onConversationUpdateActivity(turnContext);
    }

    private void addConversationReference(Activity activity) {
        ConversationReference conversationReference = activity.getConversationReference();
        conversationReferences.setConversationReferenceGivenConversationId(conversationReference.getConversation().getId(), conversationReference);
    }



}
