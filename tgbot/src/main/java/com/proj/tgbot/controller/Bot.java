package com.proj.tgbot.controller;


import com.proj.tgbot.Commands.MarkCommand;
import com.proj.tgbot.Commands.RegistrationCommand;
import com.proj.tgbot.Commands.StartCommand;

import com.proj.tgbot.config.BotConfig;
import com.proj.tgbot.domain.UserCredentials;
import com.proj.tgbot.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.MessageEntity;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@Component
@Slf4j
public final class Bot extends TelegramLongPollingBot {

    private static final String HELP_TEXT_MESSAGE = """
            это сообщение выводится по комманде /help
            список доступных комманд: 
            /reg - регистрация пользователя в системе +
            /findUserTgId [tgId] - вывод информации о пользователе +
            /findUser [login or id] - вывод информации о пользователе +
            /enter - позволит войти, если пользователь зарегистрирован +
            /start - начало работы с ботом +
            /help - вывод этого сообщения +
            /info - вывод био пользователя +
            /status - вывод статуса авторизации +
            /addMark [value{0-10}] [... comment if needed] - эта команда позволяет добавлять свои оценки состояния,
            команда сработает только если пользователь авторизирован
            (при повторном нажании на /reg регистрация начинается заново --)
            """;

    final BotConfig config;
    RegistrationCommand registrationCommand = new RegistrationCommand();
    MarkCommand markCommand = new MarkCommand();

    StartCommand startCommand = new StartCommand("start", "lets start");
    UserService userService = new UserService();

    public Bot(BotConfig config) {
        this.config = config;
        List<BotCommand> listOfCommands = new ArrayList<>();
        listOfCommands.add(new BotCommand("/reg", "start registration"));
        listOfCommands.add(new BotCommand("/help", "view help message"));
        listOfCommands.add(new BotCommand("/start", "start working with bot"));
        listOfCommands.add(new BotCommand("/status", "view my authorization status"));


        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            e.printStackTrace();
            log.error("Error settings Bot command list: " + e.getMessage());
        }
    }


    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            Long chatId = update.getMessage().getChatId();


            switch (messageText) {
                case "/start" -> {
                    sendMessage(chatId, "Hello there this is " + getBotUsername()
                            + " helps for marks about ur mental health pliz /reg to start bot, and lower is help message");
                    sendMessage(chatId, HELP_TEXT_MESSAGE);
                }
                case "/reg" -> {
                    registrationCommand.start(chatId);
                    sendMessage(chatId, "waiting ur Login");
                }
                case "/status" -> {
                    sendMessage(chatId, "ur status is " + registrationCommand.getUserState(chatId));
                }
                case "/help" -> sendMessage(chatId, HELP_TEXT_MESSAGE);
                case "/enter" -> {
                    if (checkEnter(chatId)) {
                        sendMessage(chatId, "i successfully found u");
                    } else {
                        sendMessage(chatId, "i cant find u, try /reg or write @mgvts");
                    }
                }
                case "/info" -> {
                    UserCredentials u = infoAboutUser(chatId);
                    if (u == null) {
                        sendMessage(chatId, "i cant found u, sorry, try /enter or /reg");
                    } else {
                        sendMessage(chatId, "i found u");
                        sendMessage(chatId, u.toString());
                    }
                }
                default -> {
                    switch (markCommand.checkAddMarkMessage(messageText, chatId)) {
                        case WRONG_VALUE -> {
                            sendMessage(chatId,
                                    "if u want to add mark pliz write _/addMark V_ , where V is number in range {0-10}, ur emotional condition now");
                            return;
                        }
                        case MISS_VALUE -> {
                            sendMessage(chatId, "i found u, but u dont see any arguments pliz try again _/addMark V_ ");
                        }
                        case NOT_AUTHORIZED -> {
                            sendMessage(chatId,
                                    "if u want to add mark authorize to bot\n/reg -- for registration\n/help -- for help text");
                            return;
                        }
                        case NOT_MARK_COMMAND -> {
                        }
                        case COMPLETE -> {
                            sendMessage(chatId, "u successfully sent mark");
                            return;
                        }
                        default -> {
                            log.error("undefined behavior at checkAddMarkMessage");
                        }
                    }
                    if (messageText.startsWith("/findUserTgId ")) {
                        try {
                            String text = messageText.substring("/findUserTgId ".length() + 1);
                            UserCredentials u = userService.
                                    findUserByTgId(Long.parseLong(text));
                            if (u == null) {
                                sendMessage(chatId, "go again i didnt found anyone be login:" + text);
                                return;
                            }
                            sendMessage(chatId, "here some info about user with tgId:" + text);
                            sendMessage(chatId, u.toString());
                        } catch (NumberFormatException e) {
                            sendMessage(chatId, "не стоит играть с огнём, будьте аккуратны, используйте /help");
                        }
                        return;
                    }
                    if (messageText.startsWith("/findUser ")) {
                        String text = messageText.substring("/findUser ".length());

                        UserCredentials u = userService.findUserByLoginOrId(text);
                        if (u == null) {
                            sendMessage(chatId, "go again i didnt found anyone be login:" + text);
                            return;
                        }
                        if (NumberUtils.isDigits(text)) {
                            sendMessage(chatId, "here some info about user with id:" + text);
                        } else {
                            sendMessage(chatId, "here some info about user with login:" + text);
                        }
                        sendMessage(chatId, u.toString());
                        return;
                    }
                    if (checkRegistrationState(update.getMessage())) {
                        return;
                    }

                    sendMessage(chatId, "Sorry, try /help");
                }
            }
        }
    }

    private UserCredentials infoAboutUser(Long chatId) {
        return userService.findUserByTgId(chatId);
    }

    private boolean checkEnter(Long chatId) {
        UserCredentials u = userService.findUserByTgId(chatId);
        if (u == null) {
            return false;
        } else {
            registrationCommand.authorization(chatId);
            return true;
        }
    }

//    private void startCommand(Long chatId) {
//        if (userService.findByTgId(chatId)){
//            registrationCommand.authorization(chatId);
//            sendMessage(chatId, "oh u are actually here, i remember u");
//        } else{
//            sendMessage(chatId, "oh i dont remember u? try /reg");
//        }
//    }

    private boolean checkRegistrationState(Message message) {
        Long chatId = message.getChatId();
        if (!registrationCommand.containsUser(chatId)) {
            registrationCommand.initialize(chatId);
            sendMessage(chatId, "pliz try /reg to registration");
            return true;
        }
        switch (registrationCommand.getUserState(chatId)) {
            case WAITING_LOGIN -> {
                if (registrationCommand.updateUserLogin(message)) {
                    sendMessage(chatId, "waiting ur password");
                    return true;
                }
                sendMessage(chatId, "ur login must be lowercase latin letters {2,24} repeat pls /reg");
            }
            case WAITING_PASSWORD -> {
                if (registrationCommand.updateUserPassword(message)) {
                    try {
                        userService.registerUser(new UserCredentials(
                                registrationCommand.getUserLogin(chatId),
                                registrationCommand.getUserPassword(chatId),
                                message.getChatId() + ""));
                    } catch (IOException e) {
                        sendMessage(chatId, "sorry i can't register, try later or write on @mgvts to solve this");
                        log.error("cant register user:" + e.getMessage());
                    }
                    sendMessage(chatId, "u are successfully registered");
                    sendMessage(chatId, "ur login is: " + registrationCommand.getUserLogin(chatId) +
                            "\n\n and password: " + registrationCommand.getUserPassword(chatId));
                    return true;
                }
            }
            case NOT_REGISTERED -> {
                sendMessage(chatId, "u are not registered yet? pliz go to /reg to solve this");
                return false;
            }
            case AUTHORIZED -> {
                sendMessage(chatId, "u are registered but i can't parse any words");
                return false;
            }
            default -> {
                throw new RuntimeException("no cases a parsed in checkRegistrationState");
            }
        }
        return false;
    }

    private void sendMessage(Long chatId, String textToSend) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
//        message.setParseMode("MarkdownV2");

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBOT_NAME();
    }

    @Override
    public String getBotToken() {
        return config.getBOT_TOKEN();
    }
}


