package com.example.quickstart.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeImageApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 阿里巴巴千问(Qwen)模型配置
 * 千问API文档: https://help.aliyun.com/zh/dashscope/
 */
@Configuration
public class QwenConfig {

    @Value("${spring.ai.dashscope.api-key:}")
    private String apiKey;

    @Value("${spring.ai.dashscope.base-url:https://dashscope.aliyuncs.com}")
    private String baseUrl;

    @Value("${spring.ai.dashscope.image-base-url:https://dashscope.aliyuncs.com/api/v1/services/aigc/multimodal-generation/generation}")
    private String imageBaseUrl;

    @Bean
    public DashScopeApi dashScopeApi() {
        return DashScopeApi.builder()
                .apiKey(apiKey)
                .baseUrl(baseUrl)
                .build();
    }

    @Bean
    public DashScopeChatModel dashScopeChatModel(DashScopeApi dashScopeApi) {
        return DashScopeChatModel.builder()
                .dashScopeApi(dashScopeApi)

                .build();
    }

    @Bean
    public ChatClient qwenChatClient(DashScopeChatModel dashScopeChatModel) {
        return ChatClient.builder(dashScopeChatModel).build();
    }

    @Bean
    public DashScopeImageApi dashScopeImageApi() {
        return new DashScopeImageApi(apiKey, imageBaseUrl);
    }

    @Bean
    public DashScopeImageModel dashScopeImageModel(DashScopeImageApi dashScopeImageApi) {
        return new DashScopeImageModel(dashScopeImageApi);
    }
}
