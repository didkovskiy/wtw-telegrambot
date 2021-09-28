package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMovieClient;
import com.github.didkovskiy.wtwtelegrambot.command.annotation.AdminCommand;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.StatisticsService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import com.google.common.collect.ImmutableMap;

import java.util.List;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandName.*;
import static java.util.Objects.nonNull;

/**
 * Container of the {@link Command}s, which are using for handling telegram commands.
 */
public class CommandContainer {

    private final ImmutableMap<String, Command> commandMap;
    private final Command unknownCommand;
    private final List<String> admins;

    public CommandContainer(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService,
                            WatchLaterService watchLaterService, IMDbMovieClient imDbMovieClient,
                            StatisticsService statisticsService, List<String> admins) {
        this.admins = admins;

        commandMap = ImmutableMap.<String, Command>builder()
                .put(START.getCommandName(), new StartCommand(sendBotMessageService, telegramUserService))
                .put(STOP.getCommandName(), new StopCommand(sendBotMessageService, telegramUserService))
                .put(HELP.getCommandName(), new HelpCommand(sendBotMessageService))
                .put(NO.getCommandName(), new NoCommand(sendBotMessageService))
                .put(STAT.getCommandName(), new StatCommand(sendBotMessageService, telegramUserService, statisticsService))
                .put(WATCH_LATER.getCommandName(), new WatchLaterCommand(sendBotMessageService, watchLaterService, imDbMovieClient, telegramUserService))
                .put(REMOVE_WATCH_LATER.getCommandName(), new RemoveWatchLaterCommand(sendBotMessageService, telegramUserService, watchLaterService))
                .put(RANDOM.getCommandName(), new RandomCommand(sendBotMessageService, imDbMovieClient))
                .put(TRAILER.getCommandName(), new TrailerCommand(sendBotMessageService, imDbMovieClient))
                .put(CLEAR_WATCH_LATER.getCommandName(), new ClearWatchLaterCommand(sendBotMessageService, telegramUserService, watchLaterService))
                .build();

        unknownCommand = new UnknownCommand(sendBotMessageService);
    }

    public Command retrieveCommand(String commandIdentifier, String username) {
        Command orDefault = commandMap.getOrDefault(commandIdentifier, unknownCommand);
        if (isAdminCommand(orDefault)) {
            if (admins.contains(username)) {
                return orDefault;
            } else {
                return unknownCommand;
            }
        }
        return orDefault;
    }

    private boolean isAdminCommand(Command command) {
        return nonNull(command.getClass().getAnnotation(AdminCommand.class));
    }

}