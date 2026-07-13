package com.example.quickstart.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageModel;
import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.ai.image.ImageResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 图片生成控制器
 * 使用千问(DashScope)模型的图片生成功能
 */
@RestController
@RequestMapping("/api/image")
public class ImageController {

    private final DashScopeImageModel imageModel;

    public ImageController(DashScopeImageModel imageModel) {
        this.imageModel = imageModel;
    }

    /**
     * 根据文字生成图片
     * @param prompt 描述图片内容的文字
     * @param model 图片模型，默认wan2.7-image-pro
     * @param size 图片尺寸，如1024x1024
     * @param n 生成图片数量，默认1
     * @param style 风格，可选auto/realistic
     */
    @PostMapping("/generate")
    public Map<String, Object> generateImage(
            @RequestParam String prompt,
            @RequestParam(defaultValue = "wan2.7-image-pro") String model,
            @RequestParam(defaultValue = "1024x1024") String size,
            @RequestParam(defaultValue = "1") Integer n,
            @RequestParam(defaultValue = "auto") String style) {

        DashScopeImageOptions options = DashScopeImageOptions.builder()
                .withModel(model)
                .withSize(size)
                .withN(n)
                .withStyle(style)
                .build();

        ImagePrompt imagePrompt = new ImagePrompt(prompt, options);
        ImageResponse response = imageModel.call(imagePrompt);

        return Map.of(
                "model", model,
                "size", size,
                "n", n,
                "style", style,
                "response", response
        );
    }
}
