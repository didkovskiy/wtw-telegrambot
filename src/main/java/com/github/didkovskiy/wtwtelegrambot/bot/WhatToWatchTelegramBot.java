package com.github.didkovskiy.wtwtelegrambot.bot;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMostPopularDataClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbYouTubeClient;
import com.github.didkovskiy.wtwtelegrambot.command.CommandContainer;
import com.github.didkovskiy.wtwtelegrambot.service.StatisticsService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import com.github.didkovskiy.wtwtelegrambot.service.impl.SendBotMessageServiceImpl;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

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
                                  IMDbSearchMovieClient imDbSearchMovieClient,
                                  IMDbYouTubeClient imDbYouTubeClient,
                                  IMDbMostPopularDataClient imDbMostPopularDataClient,
                                  StatisticsService statisticsService,
                                  @Value("#{'${bot.admins}'.split(',')}") List<String> admins) {
        this.commandContainer = new CommandContainer(new SendBotMessageServiceImpl(this),
                telegramUserService,
                watchLaterService,
                imDbSearchMovieClient,
                imDbYouTubeClient,
                imDbMostPopularDataClient,
                statisticsService,
                admins);
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
        String username = update.getMessage().getFrom().getUserName();
        if (update.hasMessage() && update.getMessage().hasText()) {
            String message = update.getMessage().getText().trim();
            if (message.startsWith(COMMAND_PREFIX)) {
                String commandIdentifier = message.split(SPACE)[0].toLowerCase();
                commandContainer.retrieveCommand(commandIdentifier, username).execute(update);
            } else {
                commandContainer.retrieveCommand(NO.getCommandName(), username).execute(update);
            }
        }
    }
}
