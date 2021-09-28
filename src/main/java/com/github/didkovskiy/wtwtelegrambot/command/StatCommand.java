package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.command.annotation.AdminCommand;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.StatisticsService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getChatId;

/**
 * Statistics {@link Command}.
 */
@AdminCommand
public class StatCommand implements Command {

    private final TelegramUserService telegramUserService;
    private final SendBotMessageService sendBotMessageService;
    private final StatisticsService statisticsService;

    public StatCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, StatisticsService statisticsService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.statisticsService = statisticsService;
    }

    @Override
    public void execute(Update update) {
        String chatId = getChatId(update);
        int activeUserCount = telegramUserService.retrieveAllActiveUsers().size();
        List<WatchLater> mostPopularWatchLaterRecords = statisticsService.getMostPopularWatchLaterRecords();
        StringBuilder sb = new StringBuilder("<b>I have prepared statistics:</b> \uD83D\uDCC8 \n");
        sb.append(String.format("The number of active users: %s \n\n", activeUserCount));
        sb.append("<b>Top five most saved movies:</b> \uD83D\uDD1D \n\n");
        String top;
        for (int i = 0; i < mostPopularWatchLaterRecords.size(); i++) {
            if (i == 0) top = "\uD83E\uDD47";
            else if (i == 1) top = "\uD83E\uDD48";
            else if (i == 2) top = "\uD83E\uDD49";
            else top = "";
            sb.append(String.format("<u><b><i>%s %s</i></b></u> <b>%s</b> \n"
                            + "<b>The number of users who have saved the movie:</b> %d \n\n",
                    top,
                    mostPopularWatchLaterRecords.get(i).getTitle(),
                    mostPopularWatchLaterRecords.get(i).getDescription(),
                    mostPopularWatchLaterRecords.get(i).getUsersCount()));
        }
        sendBotMessageService.sendMessage(chatId, sb.toString());
    }

}