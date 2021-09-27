package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandName.WATCH_LATER;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getChatId;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isAlphanumericSpace;

/**
 * Watch Later {@link Command}.
 */
public class WatchLaterCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final WatchLaterService watchLaterService;
    private final IMDbMovieClient imDbMovieClient;
    private final TelegramUserService telegramUserService;

    public WatchLaterCommand(SendBotMessageService sendBotMessageService, WatchLaterService watchLaterService, IMDbMovieClient imDbMovieClient, TelegramUserService telegramUserService) {
        this.sendBotMessageService = sendBotMessageService;
        this.watchLaterService = watchLaterService;
        this.imDbMovieClient = imDbMovieClient;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public void execute(Update update) {
        if (getMessage(update).equalsIgnoreCase(WATCH_LATER.getCommandName())) {
            sendWatchLaterList(getChatId(update));
            return;
        }
        String watchLaterMovieTitle = Arrays.stream(getMessage(update).split(SPACE)).skip(1).collect(Collectors.joining(SPACE));
        String chatId = getChatId(update);
        if (isAlphanumericSpace(watchLaterMovieTitle)) {
            SearchResult searchResult = imDbMovieClient.getFirstSearchResult(watchLaterMovieTitle);
            if (isNull(searchResult)) {
                sendMovieNotFoundMessage(chatId, watchLaterMovieTitle);
            } else {
                WatchLater watchLaterSaved = watchLaterService.save(chatId, searchResult);
                sendBotMessageService.sendMessage(chatId, String.format("The movie <b>%s</b> was added to the WatchLater list.", watchLaterSaved.getTitle()));
            }
        } else sendMovieNotFoundMessage(chatId, watchLaterMovieTitle);
    }

    private void sendWatchLaterList(String chatId) {
        //todo add exception handling
        List<WatchLater> watchLaterList = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new)
                .getWatchLaterList();
        if (watchLaterList.isEmpty()) {
            sendBotMessageService.sendMessage(chatId, "Your WatchLater list is empty.");
        } else {
            StringBuilder sb = new StringBuilder("Your WatchLater list: \n\n");
            for (WatchLater watchLater : watchLaterList) {
                sb.append(String.format(
                        "<b>Title:</b> %s\n" + "<b>Description:</b> %s\n\n", watchLater.getTitle(), watchLater.getDescription()));
            }
            sendBotMessageService.sendMessage(chatId, sb.toString());
        }
    }

    private void sendMovieNotFoundMessage(String chatId, String watchLaterMovieTitle) {
        String movieNotFoundMessage = String.format("I didn't find a movie with a title <b>%s</b>", watchLaterMovieTitle);
        sendBotMessageService.sendMessage(chatId, movieNotFoundMessage);
    }
}
