package com.proj.tgbot.Commands;

import org.telegram.telegrambots.extensions.bots.commandbot.commands.IBotCommand;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.bots.AbsSender;

public class StartCommand extends BotCommand implements IBotCommand {

    String identifier;
    String description;

    public StartCommand(String identifier, String description) {
        super(identifier, description);
        this.identifier = identifier;
        this.description = description;
    }


    @Override
    public String getCommandIdentifier() {
        return this.identifier;
    }

    @Override
    public void processMessage(AbsSender absSender, Message message, String[] strings) {
    }
}
