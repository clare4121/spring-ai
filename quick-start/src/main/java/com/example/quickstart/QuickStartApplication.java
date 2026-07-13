package com.example.quickstart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * Spring AI Quick Start Application
 * 集成DeepSeek和千问模型的示例项目
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class QuickStartApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuickStartApplication.class, args);
    }
}
