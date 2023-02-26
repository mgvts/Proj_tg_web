package com.proj.tgbot.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Data
@PropertySource("classpath:application.properties")
public class BotConfig {

    //mb private
    @Value("${bot.name}")
    String BOT_NAME;

    @Value("${bot.token}")
    String BOT_TOKEN;
}
