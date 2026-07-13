package com.example.quickstart.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.deepseek.DeepSeekChatOptions;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * AI模型对话控制器
 * 支持DeepSeek和千问模型的对话调用
 */
@RestController
@RequestMapping("/api/ai")
public class AiChatController {

    private final ChatClient deepSeekChatClient;
    private final ChatClient qwenChatClient;

    public AiChatController(ChatClient deepSeekChatClient, ChatClient qwenChatClient) {
        this.deepSeekChatClient = deepSeekChatClient;
        this.qwenChatClient = qwenChatClient;
    }

    /**
     * DeepSeek对话
     * @param message 用户消息
     * @param temperature 温度参数 (0.0-2.0)，控制随机性，默认0.7
     * @param maxTokens 最大生成token数，默认2048
     */
    @PostMapping("/deepseek/chat")
    public Map<String, String> deepSeekChat(
            @RequestParam String message,
            @RequestParam(defaultValue = "0.7") Double temperature,
            @RequestParam(defaultValue = "2048") Integer maxTokens) {

        DeepSeekChatOptions options = DeepSeekChatOptions.builder()
                .temperature(temperature)
                .maxTokens(maxTokens)
                .build();

        String content = deepSeekChatClient.prompt()
                .user(message)
                .options(options)
                .call()
                .content();

        return Map.of(
                "model", "deepseek-chat",
                "temperature", temperature.toString(),
                "maxTokens", maxTokens.toString(),
                "content", content != null ? content : ""
        );
    }

    /**
     * 千问(Qwen)对话
     * @param message 用户消息
     * @param temperature 温度参数 (0.0-2.0)，控制随机性，默认0.7
     * @param maxTokens 最大生成token数，默认2048
     */
    @PostMapping("/qwen/chat")
    public Map<String, String> qwenChat(
            @RequestParam String message,
            @RequestParam(defaultValue = "0.7") Double temperature,
            @RequestParam(defaultValue = "2048") Integer maxTokens) {

        DashScopeChatOptions options = DashScopeChatOptions.builder()
                .withTemperature(temperature)
                .withMaxToken(maxTokens)
                .build();

        String content = qwenChatClient.prompt()
                .user(message)
                .options(options)
                .call()
                .content();

        return Map.of(
                "model", "qwen-plus",
                "temperature", temperature.toString(),
                "maxTokens", maxTokens.toString(),
                "content", content != null ? content : ""
        );
    }
}
