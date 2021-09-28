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
            "\uD83D\uDD25 <b>Available commands</b> \uD83D\uDD25 \n\n"
                    + "<b>Start\\stop working with bot:</b> \n"
                    + "%s - start working with me ▶️\n"
                    + "%s - stop working with me ⏹ \n\n"

                    + "<b>WatchLater list access:</b>\n"
                    + "%s + 'movie title' - save movie to the WatchLater list \uD83D\uDCCC \n"
                    + "%s - see WatchLater list \uD83D\uDD59 \n"
                    + "%s - remove movie from WatchLater list ✖️\n"
                    + "%s - clear the WatchLater list \uD83E\uDDF9 \n\n"

                    + "<b>Search a random movie:</b>\n"
                    + "%s - get a completely random movie from most popular \uD83D\uDC8E \n"
                    + "%s + '[1-3] keywords' - get a random movie by searching keywords \uD83D\uDD0D \n\n"

                    + "<b>Watch the movie trailer:</b>\n"
                    + "%s + 'movie title' - find the trailer for this movie \uD83C\uDF9E \n\n"

                    + "<b>Other:</b>\n"
                    + "%s - get statistic about active users \uD83D\uDDD2\n"
                    + "%s - get help about working with me \uD83D\uDC4B\n",

            START.getCommandName(),
            STOP.getCommandName(),

            WATCH_LATER.getCommandName(),
            WATCH_LATER.getCommandName(),
            REMOVE_WATCH_LATER.getCommandName(),
            CLEAR_WATCH_LATER.getCommandName(),

            RANDOM.getCommandName(),
            RANDOM.getCommandName(),

            TRAILER.getCommandName(),

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
