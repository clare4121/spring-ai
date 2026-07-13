package com.example.quickstart.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatModel;
import org.springframework.ai.deepseek.api.DeepSeekApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * DeepSeek模型配置
 * DeepSeek API文档: https://platform.deepseek.com/
 */
@Configuration
public class DeepSeekConfig {

    @Value("${spring.ai.deepseek.api-key:}")
    private String apiKey;

    @Value("${spring.ai.deepseek.base-url:https://api.deepseek.com}")
    private String baseUrl;

    @Bean
    public DeepSeekApi deepSeekApi() {
        return DeepSeekApi.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public DeepSeekChatModel deepSeekChatModel(DeepSeekApi deepSeekApi) {
        return DeepSeekChatModel.builder()
                .deepSeekApi(deepSeekApi)
                .build();
    }

    @Bean
    public ChatClient deepSeekChatClient(DeepSeekChatModel deepSeekChatModel) {
        return ChatClient.builder(deepSeekChatModel).build();
    }
}
