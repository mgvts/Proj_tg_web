package com.proj.tgbot.Commands;


import lombok.Setter;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;


import java.util.HashMap;
import java.util.Map;

public class RegistrationCommand {
    public Map<Long, String> UserLogin = new HashMap<>();
    public Map<Long, String> UserPassword = new HashMap<>();
    public static Map<Long, RegisterCommandType> UserState = new HashMap<>();


    public String getUserLogin(Long id) {
        return UserLogin.get(id);
    }

    public String getUserPassword(Long id) {
        return UserPassword.get(id);
    }

    public RegisterCommandType getUserState(Long id) {
        return UserState.get(id);
    }

    public boolean containsUser(Long id) {
        return UserState.containsKey(id);
    }

    public void start(Long chatId) {
        UserState.put(chatId, RegisterCommandType.WAITING_LOGIN);
    }

    public void initialize(Long chatId) {
        UserState.put(chatId, RegisterCommandType.NOT_REGISTERED);
    }

    public void authorization(Long chatId) {
        UserState.put(chatId, RegisterCommandType.AUTHORIZED);
    }

    private boolean validatePassword(String userPassword) {
        return userPassword != null && 2 < userPassword.length() && userPassword.length() < 100;
    }

    private boolean validateLogin(String userLogin) {
        return userLogin != null && userLogin.matches("^[a-z]{2,24}$");
    }

    public boolean updateUserLogin(Message message) {
        Long id = message.getChatId();
        if (validateLogin(message.getText())) {
            UserLogin.put(message.getChatId(), message.getText());
            UserState.put(id, RegisterCommandType.WAITING_PASSWORD);
            return true;
        }
        return false;
    }

    public boolean updateUserPassword(Message message) {
        Long id = message.getChatId();

        if (validatePassword(message.getText())) {
            UserPassword.put(message.getChatId(), message.getText());
            UserState.put(id, RegisterCommandType.AUTHORIZED);
            return true;
        }
        return false;

    }

    public enum RegisterCommandType {
        NOT_REGISTERED,
        AUTHORIZED,
        WAITING_LOGIN,
        WAITING_PASSWORD,
    }
}
