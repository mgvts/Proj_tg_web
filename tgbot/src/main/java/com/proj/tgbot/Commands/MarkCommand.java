package com.proj.tgbot.Commands;

import com.proj.tgbot.Commands.RegistrationCommand.RegisterCommandType;
import com.proj.tgbot.service.MarkService;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Arrays;

public class MarkCommand {
    RegistrationCommand registrationCommand = new RegistrationCommand();
    MarkService markService = new MarkService();

    public MarkCommandType checkAddMarkMessage(String text, Long chatId) {
        if (!text.startsWith("/addMark")) {
            return MarkCommandType.NOT_MARK_COMMAND;
        }
        if (registrationCommand.getUserState(chatId) != RegisterCommandType.AUTHORIZED) {
            return MarkCommandType.NOT_AUTHORIZED;
        }

        String[] splitText = text.split(" ");
        if (splitText.length == 1) {
            return MarkCommandType.MISS_VALUE;
        }
        String value = splitText[1];
        if (NumberUtils.isDigits(value)) {
            int parsedValue = Integer.parseInt(value);
            if (0 <= parsedValue && parsedValue <= 10) {
                if (splitText.length > 2) {
                    if (!markService.addMark(parsedValue, chatId,
                            Arrays.toString(Arrays.copyOfRange(splitText, 2, splitText.length)))) {
                        return MarkCommandType.WRONG_VALUE;
                    }
                } else {
                    if (!markService.addMark(parsedValue, chatId)) {
                        return MarkCommandType.WRONG_VALUE;
                    }
                }
                return MarkCommandType.COMPLETE;
            }
        }
        return MarkCommandType.WRONG_VALUE;
    }

    public enum MarkCommandType {
        NOT_MARK_COMMAND,
        NOT_AUTHORIZED,
        MISS_VALUE,
        WRONG_VALUE,
        COMPLETE,
    }
}
