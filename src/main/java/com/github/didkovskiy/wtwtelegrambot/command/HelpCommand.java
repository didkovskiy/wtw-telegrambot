package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandName.*;

/**
 * Help {@link Command}.
 */
public class HelpCommand implements Command {

    private final SendBotMessageService sendBotMessageService;

    public static final String HELP_MESSAGE = String.format(
            "\uD83D\uDD25 <b>Available commands</b> \uD83D\uDD25\n\n"
                    + "<b>Start\\stop working with bot:</b>\n"
                    + "%s - start working with me ✅\n"
                    + "%s - stop working with me \uD83D\uDED1\n\n"

                    + "<b>WatchLater list access:</b>\n"
                    + "%s + 'movie title' - save movie to the WatchLater list\n"
                    + "%s - see WatchLater list ⌚️\n\n"

                    + "%s - get statistic about active users\n"
                    + "%s - get help about working with me \uD83D\uDC4B\n",
            START.getCommandName(),
            STOP.getCommandName(),

            WATCH_LATER.getCommandName(),
            WATCH_LATER.getCommandName(),

            STAT.getCommandName(),
            HELP.getCommandName());

    public HelpCommand(SendBotMessageService sendBotMessageService) {
        this.sendBotMessageService = sendBotMessageService;
    }

    @Override
    public void execute(Update update) {
        sendBotMessageService.sendMessage(update.getMessage().getChatId().toString(), HELP_MESSAGE);
    }
}
