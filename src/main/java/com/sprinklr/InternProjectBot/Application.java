// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.sprinklr.InternProjectBot;

import com.microsoft.bot.builder.Bot;
import com.microsoft.bot.integration.AdapterWithErrorHandler;
import com.microsoft.bot.integration.BotFrameworkHttpAdapter;
import com.microsoft.bot.integration.Configuration;
import com.microsoft.bot.integration.spring.BotController;
import com.microsoft.bot.integration.spring.BotDependencyConfiguration;
import com.sprinklr.InternProjectBot.Controller.GitLabBotController;
import com.sprinklr.InternProjectBot.Conversation.ConversationReferences;
import com.sprinklr.InternProjectBot.Database.DataAccessLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;


@SpringBootApplication
@Import({BotController.class})
public class Application extends BotDependencyConfiguration {

    @Autowired
    GitLabAPI gitLabAPI;

    @Autowired
    @Qualifier("dataAccessLayerImplMongoDBAtlas")
    DataAccessLayer dataAccessLayer;

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);

    }

    @Bean
    public Bot getBot(ConversationReferences conversationReferences) {
        return new GitLabBotController(conversationReferences,gitLabAPI,dataAccessLayer);
    }

    @Bean
    public ConversationReferences getConversationReferences() {
        return new ConversationReferences(dataAccessLayer,gitLabAPI);
    }

    @Override
    public BotFrameworkHttpAdapter getBotFrameworkHttpAdaptor(Configuration configuration) {
        return new AdapterWithErrorHandler(configuration);
    }
}
