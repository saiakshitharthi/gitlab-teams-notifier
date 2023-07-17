package com.sprinklr.InternProjectBot.Cards;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.microsoft.bot.schema.*;
import com.sprinklr.InternProjectBot.FileUtils.FileOperations;
import com.sprinklr.InternProjectBot.HTML.HtmlContentCreator;
import com.sprinklr.InternProjectBot.MessageUtils.MessageBody;
import com.sprinklr.InternProjectBot.StringUtilities.StaticMessages;
import org.apache.commons.io.IOUtils;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.CompletionException;


public class Cards {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static Attachment getCodeContentCard(String commentBetweenLines) {
        HeroCard heroCard = new HeroCard();
        heroCard.setSubtitle(StaticMessages.codeContentSubTitle);
        heroCard.setText(HtmlContentCreator.stringToCodeConverter(commentBetweenLines));
        return heroCard.toAttachment();
    }

    public static Attachment nothingToSelectCard() {
        HeroCard heroCard = new HeroCard();
        heroCard.setText(StaticMessages.nothingToSelectContent);
        return heroCard.toAttachment();
    }

    public static Attachment getHelpHeroCard() {
        HeroCard heroCard = new HeroCard();
        heroCard.setTitle(HtmlContentCreator.makeTitle("Command List"));
        heroCard.setText(StaticMessages.helpContent);
        return heroCard.toAttachment();
    }

    public static Attachment getNormalTextCard(String text) {
        HeroCard heroCard = new HeroCard();
        heroCard.setText(text);
        return heroCard.toAttachment();
    }

    public static Attachment createAdaptiveCardForReplyBox(MessageBody messageBody) {
        Attachment adaptiveCardAttachment = new Attachment();

        try (InputStream inputStream = adaptiveCardAttachment.getClass().getClassLoader().getResourceAsStream("Cards/SkeletonCard.json")) {
            assert inputStream != null;
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            adaptiveCardAttachment.setContentType("application/vnd.microsoft.card.adaptive");
            ObjectNode objectNode = objectMapper.readValue(result, ObjectNode.class);
            ArrayNode bodyNode = (ArrayNode) objectNode.get("body");
            bodyNode.add(getInputText("reply"));
            ArrayNode node = (ArrayNode) objectNode.get("actions");
            node.add(getActionButton("Reply", "reply", messageBody.getDiscussionThreadURL(), messageBody.getDiscussionThreadID()));
            if (messageBody.isContainsResolveThread()) {
                node.add(getActionButton("Resolve Thread", "resolveThread", messageBody.getDiscussionThreadURL(), messageBody.getDiscussionThreadID()));
            }
            adaptiveCardAttachment.setContent(objectNode);
            System.out.println(objectNode);
            return adaptiveCardAttachment;
        } catch (Throwable t) {
            throw new CompletionException(t);
        }
    }

    public static Attachment createHeroCardForNotificationContent(MessageBody messageBody) {
        HeroCard heroCard = new HeroCard();
        heroCard.setTitle(HtmlContentCreator.makeTitle(messageBody.getTitle()));
        heroCard.setText(HtmlContentCreator.makeText(messageBody.getText()));
        return heroCard.toAttachment();
    }

    public static Attachment createAdaptiveCardForUnsubscribeList(List<String> unsubscribeOptions) {

        Attachment adaptiveCardAttachment = new Attachment();
        try (InputStream inputStream = adaptiveCardAttachment.getClass().getClassLoader().getResourceAsStream("Cards/UnsubscribeCard.json")) {
            if (unsubscribeOptions.size() == 0) {
                return nothingToSelectCard();
            }
            assert inputStream != null;
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            adaptiveCardAttachment.setContentType("application/vnd.microsoft.card.adaptive");
            ObjectNode objectNode = objectMapper.readValue(result, ObjectNode.class);
            ArrayNode choices = (ArrayNode) objectNode.get("body").get(1).get("choices");

            for (String option : unsubscribeOptions) {
                choices.add(getChoice(StaticMessages.map.get(option).title, option));
            }
            ArrayNode arrayNode = (ArrayNode) objectNode.get("actions");
            arrayNode.add(getActionButton("Unsubscribe", "unsubscribe"));
            adaptiveCardAttachment.setContent(objectNode);
            return adaptiveCardAttachment;
        } catch (Throwable t) {
            System.out.println(t.getMessage());
            return nothingToSelectCard();
        }
    }

    public static Attachment createAdaptiveCardForSubscribeList(List<String> subscriptionOptions) {

        if (subscriptionOptions.size() == 0) {
            return nothingToSelectCard();
        }
        Attachment adaptiveCardAttachment = new Attachment();

        String fileContent = FileOperations.readFileAsString("Cards/SubscribeCard.json");
        adaptiveCardAttachment.setContentType(StaticMessages.adaptiveCardContentType);

        ObjectNode objectNode;
        try {
            objectNode = objectMapper.readValue(fileContent, ObjectNode.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error in ObjectMapper while reading SubscribeCard.json");
            return nothingToSelectCard();
        }

        ArrayNode choices = (ArrayNode) objectNode.get("body").get(1).get("choices");

        for (String option : subscriptionOptions) {
            choices.add(getChoice(StaticMessages.map.get(option).title, option));
        }

        ArrayNode arrayNode = (ArrayNode) objectNode.get("actions");
        arrayNode.add(getActionButton("Subscribe", "subscribe"));
        adaptiveCardAttachment.setContent(objectNode);

        return adaptiveCardAttachment;

    }

    public static Attachment getGitLabLoginCard() {
        Attachment adaptiveCardAttachment = new Attachment();
        String fileContent = FileOperations.readFileAsString("Cards/SampleCard.json");
        ObjectNode objectNode;
        try {
            objectNode = objectMapper.readValue(fileContent, ObjectNode.class);
        }catch (Exception e){
            System.out.println(e.getMessage());
            System.out.println("Error in ObjectMapper while reading SubscribeCard.json");
            return nothingToSelectCard();
        }
        adaptiveCardAttachment.setContent(objectNode);
        return adaptiveCardAttachment;

    }

    public static ObjectNode getChoice(String title, String value) {
        Attachment adaptiveCardAttachment = new Attachment();

        try (InputStream inputStream = adaptiveCardAttachment.getClass().getClassLoader().getResourceAsStream("Cards/ChoiceCard.json")) {
            assert inputStream != null;
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            ObjectNode objectNode = objectMapper.readValue(result, ObjectNode.class);
            objectNode.put("title", title);
            objectNode.put("value", value);
            return objectNode;
        } catch (Throwable t) {
            throw new CompletionException(t);
        }
    }

    public static ObjectNode getInputText(String id) {
        Attachment adaptiveCardAttachment = new Attachment();

        try (InputStream inputStream = adaptiveCardAttachment.getClass().getClassLoader().getResourceAsStream("Cards/InputTextCard.json")) {
            assert inputStream != null;
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            ObjectNode objectNode = objectMapper.readValue(result, ObjectNode.class);
            objectNode.put("id", id);
            return objectNode;
        } catch (Throwable t) {
            throw new CompletionException(t);
        }
    }

    public static ObjectNode getActionButton(String title, String name){
        Attachment adaptiveCardAttachment = new Attachment();

        try (InputStream inputStream = adaptiveCardAttachment.getClass().getClassLoader().getResourceAsStream("Cards/ActionButtonCard.json")) {
            assert inputStream != null;
            String result = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
            ObjectNode objectNode = objectMapper.readValue(result, ObjectNode.class);
            ObjectNode buttonData = (ObjectNode) objectNode.get("data");
            buttonData.put("button", name);
            objectNode.put("title", title);
            return objectNode;
        } catch (Throwable t) {
            throw new CompletionException(t);
        }
    }

    public static ObjectNode getActionButton(String title, String name, String discussionThreadURL, String discussionThreadID) {
        String fileContent = FileOperations.readFileAsString("Cards/ActionButtonCard.json");
        ObjectNode objectNode;
        try {
            objectNode = objectMapper.readValue(fileContent, ObjectNode.class);
            objectNode.put("title", title);
            ObjectNode buttonData = (ObjectNode) objectNode.get("data");
            buttonData.put("button", name);
            buttonData.put("discussionThreadURL", discussionThreadURL);
            buttonData.put("discussionThreadID", discussionThreadID);
            return objectNode;
        } catch (Throwable t) {
            throw new CompletionException(t);
        }

    }
}
