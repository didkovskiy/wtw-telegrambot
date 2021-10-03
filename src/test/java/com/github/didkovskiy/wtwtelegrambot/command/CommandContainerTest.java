package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMostPopularDataClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbYouTubeClient;
import com.github.didkovskiy.wtwtelegrambot.command.annotation.AdminCommand;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.StatisticsService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;
    private final String username = "username";

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        WatchLaterService watchLaterService = Mockito.mock(WatchLaterService.class);
        IMDbSearchMovieClient imDbSearchMovieClient = Mockito.mock(IMDbSearchMovieClient.class);
        IMDbYouTubeClient imDbYouTubeClient = Mockito.mock(IMDbYouTubeClient.class);
        IMDbMostPopularDataClient imDbMostPopularDataClient = Mockito.mock(IMDbMostPopularDataClient.class);
        StatisticsService statisticsService = Mockito.mock(StatisticsService.class);
        List<String> admins = List.of("admin");

        commandContainer = new CommandContainer(sendBotMessageService,
                telegramUserService, watchLaterService, imDbSearchMovieClient,
                imDbYouTubeClient, imDbMostPopularDataClient, statisticsService, admins);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //given

        String helpCommandName = "/help";
        String startCommandName = "/start";
        String stopCommandName = "/stop";
        String noCommandName = "/nocommand";

        //when
        Command helpCommand = commandContainer.retrieveCommand(helpCommandName, username);
        Command startCommand = commandContainer.retrieveCommand(startCommandName, username);
        Command stopCommand = commandContainer.retrieveCommand(stopCommandName, username);
        Command noCommand = commandContainer.retrieveCommand(noCommandName, username);

        //then
        assertEquals(HelpCommand.class, helpCommand.getClass());
        assertEquals(StartCommand.class, startCommand.getClass());
        assertEquals(StopCommand.class, stopCommand.getClass());
        assertEquals(NoCommand.class, noCommand.getClass());
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/htrghtredsf";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand, username);

        //then
        assertEquals(UnknownCommand.class, command.getClass());
    }

    @Test
    public void shouldReturnAdminCommand(){
        //when
        Command adminCommand = commandContainer.retrieveCommand("/stat", "admin");

        //then
        assertTrue(adminCommand.getClass().isAnnotationPresent(AdminCommand.class));
    }

    @Test
    public void shouldReturnUnknownCommandForGeneralUsers(){
        //when
        Command adminCommand = commandContainer.retrieveCommand("/stat", "Vasya");

        //then
        assertEquals(UnknownCommand.class, adminCommand.getClass());
    }
}