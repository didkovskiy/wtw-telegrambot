package com.github.didkovskiy.wtwtelegrambot.bot;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMovieClient;
import com.github.didkovskiy.wtwtelegrambot.command.CommandContainer;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import com.github.didkovskiy.wtwtelegrambot.service.impl.SendBotMessageServiceImpl;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandName.NO;
import static org.apache.commons.lang3.StringUtils.SPACE;

/**
 * Telegram bot that helps you to choose movies.
 */
@Component
public class WhatToWatchTelegramBot extends TelegramLongPollingBot {

    public static String COMMAND_PREFIX = "/";

    @Value("${bot.username}")
    private String username;

    @Value("${bot.token}")
    private String token;

    private final CommandContainer commandContainer;

    public WhatToWatchTelegramBot(TelegramUserService telegramUserService,
                                  WatchLaterService watchLaterService,
                                  IMDbMovieClient imDbMovieClient) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
                telegramUserService,
                watchLaterService,
                imDbMovieClient);
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(SPACE)[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName()).execute(update);
            }
        }
    }
}
