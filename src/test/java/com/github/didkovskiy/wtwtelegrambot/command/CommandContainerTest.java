package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for CommandContainer")
class CommandContainerTest {

    private CommandContainer commandContainer;

    @BeforeEach
    public void init() {
        SendBotMessageService sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        commandContainer = new CommandContainer(sendBotMessageService, telegramUserService);
    }

    @Test
    public void shouldGetAllTheExistingCommands() {
        //given
        String helpCommandName  = "/help";
        String startCommandName = "/start";
        String stopCommandName  = "/stop";
        String noCommandName    = "/nocommand";

        //when
        Command helpCommand  = commandContainer.retrieveCommand(helpCommandName);
        Command startCommand = commandContainer.retrieveCommand(startCommandName);
        Command stopCommand  = commandContainer.retrieveCommand(stopCommandName);
        Command noCommand    = commandContainer.retrieveCommand(noCommandName);

        //then
        assertEquals(HelpCommand.class,  helpCommand.getClass());
        assertEquals(StartCommand.class, startCommand.getClass());
        assertEquals(StopCommand.class,  stopCommand.getClass());
        assertEquals(NoCommand.class,    noCommand.getClass());
    }

    @Test
    public void shouldReturnUnknownCommand() {
        //given
        String unknownCommand = "/htrghtredsf";

        //when
        Command command = commandContainer.retrieveCommand(unknownCommand);

        //then
        assertEquals(UnknownCommand.class, command.getClass());
    }

    @Test
    public void shouldNotContainUnknownCommand() {
        //when-then
        Arrays.stream(CommandName.values())
                .forEach(commandName -> {
                    Command command = commandContainer.retrieveCommand(commandName.getCommandName());
                    assertNotEquals(UnknownCommand.class, command.getClass());
                });
    }
}