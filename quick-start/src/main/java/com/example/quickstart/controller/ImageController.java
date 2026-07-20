package com.example.quickstart.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 图片生成控制器
 * 使用千问(DashScope)模型的图片生成功能
 */
@RestController
@RequestMapping("/api/image")
public class ImageController {
    @Value("${spring.ai.dashscope.api-key:}")
    private String apiKey;
    private final DashScopeImageModel dashScopeImageModel;

    public ImageController(DashScopeImageModel dashScopeImageModel) {
        this.dashScopeImageModel = dashScopeImageModel;
    }

    /**
     * 根据文字生成图片
     * @param request 图片生成请求体
     */
    @PostMapping("/generate")
    public Map<String, Object> generateImage(@RequestBody ImageGenerateRequest request) {
        String prompt = request.getInput().getMessages().get(0).getContent().get(0).getText();

        DashScopeImageOptions options = DashScopeImageOptions.builder()
                .withModel(request.getModel())
                .withSize(request.getParameters().getSize())
                .withN(request.getParameters().getN())
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
        ImageResponse response = dashScopeImageModel.call(imagePrompt);

        return Map.of(
                "model", request.getModel(),
                "size", request.getParameters().getSize(),
                "n", request.getParameters().getN(),
                "response", response
        );
    }

    /**
     * 图片生成请求体
     */
    public static class ImageGenerateRequest {
        private String model;
        private Input input;
        private Parameters parameters;

        public String getModel() { return model; }
        public void setModel(String model) { this.model = model; }
        public Input getInput() { return input; }
        public void setInput(Input input) { this.input = input; }
        public Parameters getParameters() { return parameters; }
        public void setParameters(Parameters parameters) { this.parameters = parameters; }

        public static class Input {
            private List<Message> messages;
            public List<Message> getMessages() { return messages; }
            public void setMessages(List<Message> messages) { this.messages = messages; }
        }

        public static class Message {
            private String role;
            private List<ContentItem> content;
            public String getRole() { return role; }
            public void setRole(String role) { this.role = role; }
            public List<ContentItem> getContent() { return content; }
            public void setContent(List<ContentItem> content) { this.content = content; }
        }

        public static class ContentItem {
            private String text;
            public String getText() { return text; }
            public void setText(String text) { this.text = text; }
        }

        public static class Parameters {
            private String size;
            private Integer n;
            private Boolean enable_sequential;
            public String getSize() { return size; }
            public void setSize(String size) { this.size = size; }
            public Integer getN() { return n; }
            public void setN(Integer n) { this.n = n; }
            public Boolean getEnable_sequential() { return enable_sequential; }
            public void setEnable_sequential(Boolean enable_sequential) { this.enable_sequential = enable_sequential; }
        }
    }
}
