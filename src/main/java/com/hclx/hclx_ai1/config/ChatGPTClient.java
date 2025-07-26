package com.hclx.hclx_ai1.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ChatGPTClient {
@Bean
    public ChatClient initGPT4(ChatClient.Builder builder)
    {
    return  builder.build();
    }
}
