package com.sprinklr.InternProjectBot.MessageSender.BotMessage;

import com.microsoft.bot.builder.MessageFactory;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.schema.Attachment;
import com.sprinklr.InternProjectBot.Cards.Cards;
import com.sprinklr.InternProjectBot.Database.Models.ConversationDetails;
import com.sprinklr.InternProjectBot.Conversation.ConversationReferences;
import com.sprinklr.InternProjectBot.GitLabAPI;
import com.sprinklr.InternProjectBot.MessageUtils.Message;
import com.sprinklr.InternProjectBot.MessageSender.MessageSenderInterface;
import com.microsoft.bot.schema.Activity;
import com.microsoft.bot.schema.ConversationReference;
import com.sprinklr.InternProjectBot.MessageUtils.MessageBody;
import com.sprinklr.InternProjectBot.Payload.GitLabTriggerPayload.Payload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BotMessageSender implements MessageSenderInterface {

    private final BotFrameworkHttpAdapter adapter;
    private final ConversationReferences conversationReferences;
    private final String appId;

    @Autowired
    private GitLabAPI gitLabAPI;


    @Autowired
    public BotMessageSender(
            BotFrameworkHttpAdapter withAdapter,
            Configuration withConfiguration,
            ConversationReferences withReferences
    ) {
        adapter = withAdapter;
        conversationReferences = withReferences;
        appId = withConfiguration.getProperty("MicrosoftAppId");
    }

    @Override
    public void sendMessage(Message message, Payload payload) {
        if(message == null || payload == null){
            throw new IllegalArgumentException("Message or Payload is null in BotMessageSender class in sendMessage method");
        }

        /*
            * Get the conversation reference of the user.
        */
        ConversationDetails conversationDetails = conversationReferences.getConversationDetailsGivenGitLabHandle(message.getReceiverGitLabHandle());
        /*
            * Check if the user does not have an account.
        */
        if(conversationDetails == null){
            System.out.println("Can't send the message to "
                    + message.getReceiverGitLabHandle()
                    + " because He doesn't have an account!!");
            return;
        }
        /*
            * Check if the user does not have a conversation Reference.
        */
        ConversationReference reference = conversationDetails.conversationReference;

        if(reference == null){
            System.out.println("Can't send the message to "
                    + message.getReceiverGitLabHandle()
                    + " because He doesn't have a conversation Reference!!");
            return;
        }
        /*
            * Check if the user has unsubscribed from this type of message.
        */

        List<String> unsubscriptions =  conversationReferences.getUnsubscriptionsGivenGitLabHandle(message.getReceiverGitLabHandle());
        boolean isUnsubscribed = unsubscriptions.contains(message.getMessageBody().getMessageTypeID());

        if(isUnsubscribed){
            System.out.println("Message is not sent to " + message.getReceiverGitLabHandle() + " because he has unsubscribed from this type of message!!");
            return;
        }

        System.out.println("Bot Message Sender is called!! to send the message to: "
                + message.getReceiverGitLabHandle());

        /*
            * If it contains comment, then send the adaptive card for notification content.
            * If it contains comment between lines, then send the code content of the comment.
            * If it contains reply (If it contains resolve thread, it will manage in the card itself
            * then save the reply URL, which is the url of the discussion thread.
         */

        List<Attachment> attachments = new ArrayList<>();
        /*
            * Handling the Main Notification Content.
        */

        attachments.add(Cards.createHeroCardForNotificationContent(message.getMessageBody()));

        /*
            * Handling if it contains comment between lines.
        */

        String commentBetweenLines = gitLabAPI.getCommentBetweenLines(payload,
                conversationReferences.getGitLabTokenGivenGitLabHandle(message.getReceiverGitLabHandle()));
        if(commentBetweenLines != null){
            attachments.add(Cards.getCodeContentCard(commentBetweenLines));
        }

        /*
            * Handling if it contains reply box.
        */

        if(doesItContainReply(message.getMessageBody())){
            attachments.add(Cards.createAdaptiveCardForReplyBox(message.getMessageBody()));
            System.out.println("Inside Bot Message Sender and saving the Reply URL.");
            conversationDetails.state.setDiscussionThreadURL(message.getMessageBody().getDiscussionThreadURL());
            System.out.println(message.getMessageBody().getDiscussionThreadURL());
            conversationDetails.save();
        }

        Activity reply = MessageFactory
                .attachment(attachments);

        adapter.continueConversation(
                appId, reference, turnContext -> turnContext.sendActivity(reply).thenApply(resourceResponse -> null)
        );
    }


    private boolean doesItContainReply(MessageBody messageBody){
        if(messageBody.isDiscussionThread()){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public void sendMessages(List<Message> messages, Payload payload) {
        for (Message message : messages) {
            this.sendMessage(message, payload);
        }
    }
}
