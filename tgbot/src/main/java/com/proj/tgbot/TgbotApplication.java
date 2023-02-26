package com.proj.tgbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TgbotApplication {
    public static void main(String[] args) {
        try {
            SpringApplication.run(TgbotApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
