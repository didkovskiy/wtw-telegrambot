package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbYouTubeClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandName.TRAILER;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getChatId;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.SPACE;
import static org.apache.commons.lang3.StringUtils.isAlphanumericSpace;

/**
 * Trailer {@link Command}.
 */
public class TrailerCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final IMDbSearchMovieClient imDbSearchMovieClient;
    private final IMDbYouTubeClient imDbYouTubeClient;

    public TrailerCommand(SendBotMessageService sendBotMessageService, IMDbSearchMovieClient imDbSearchMovieClient, IMDbYouTubeClient imDbYouTubeClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.imDbSearchMovieClient = imDbSearchMovieClient;
        this.imDbYouTubeClient = imDbYouTubeClient;
    }

    @Override
    public void execute(Update update) {
        String chatId = getChatId(update);
        if (getMessage(update).equalsIgnoreCase(TRAILER.getCommandName())) {
            sendBotMessageService.sendMessage(chatId, "Type 'movie title' after /trailer command.");
            return;
        }
        String movieTitle = Arrays.stream(getMessage(update).split(SPACE)).skip(1).collect(Collectors.joining(SPACE));
        if (isAlphanumericSpace(movieTitle)) {
            SearchResult searchResult = imDbSearchMovieClient.getFirstSearchResult(movieTitle);
            if (isNull(searchResult)) {
                sendTrailerNotFoundMessage(chatId, movieTitle);
                return;
            }
            YouTubeTrailerData youTubeTrailerData = imDbYouTubeClient.getYouTubeTrailer(searchResult.getId());
            if (isNull(youTubeTrailerData) || youTubeTrailerData.getVideoUrl().isEmpty()) {
                sendTrailerNotFoundMessage(chatId, movieTitle);
            } else sendTrailerInfo(chatId, youTubeTrailerData);
        } else sendTrailerNotFoundMessage(chatId, movieTitle);
    }

    private void sendTrailerNotFoundMessage(String chatId, String movieTitle) {
        String movieNotFoundMessage = String.format("I didn't find a trailer for a movie with a title <b>%s</b>", movieTitle);
        sendBotMessageService.sendMessage(chatId, movieNotFoundMessage);
    }

    private void sendTrailerInfo(String chatId, YouTubeTrailerData youTubeTrailerData) {
        String trailerInfo = String.format("Hey, I found this: \n\n"
                        + "<b>%s</b>\n"
                        + "%s\n"
                , youTubeTrailerData.getFullTitle(), youTubeTrailerData.getVideoUrl());
        sendBotMessageService.sendMessage(chatId, trailerInfo);
    }
}
