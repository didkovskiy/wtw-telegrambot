package com.github.didkovskiy.wtwtelegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Update;

/**
 * Utility class for Commands.
 */
public class CommandUtils {

    /**
     * Get chatId from {@link Update} object.
     *
     * @param update provided {@link Update}
     * @return chatID from the provided {@link Update} object.
     */
    public static String getChatId(Update update) {
        return update.getMessage().getChatId().toString();
    }

    /**
     * Get text of the message from {@link Update} object.
     *
     * @param update provided {@link Update}
     * @return the text of the message from the provided {@link Update} object.
     */
    public static String getMessage(Update update) {
        return update.getMessage().getText();
    }
}