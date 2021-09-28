package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMostPopularDataClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.MostPopularDataDetail;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Arrays;
import java.util.stream.Collectors;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandName.RANDOM;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getChatId;
import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getMessage;
import static java.util.Objects.isNull;
import static org.apache.commons.lang3.StringUtils.*;

/**
 * Random {@link Command}.
 */
public class RandomCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final IMDbSearchMovieClient imDbSearchMovieClient;
    private final IMDbMostPopularDataClient imDbMostPopularDataClient;

    public RandomCommand(SendBotMessageService sendBotMessageService, IMDbSearchMovieClient imDbSearchMovieClient, IMDbMostPopularDataClient imDbMostPopularDataClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.imDbSearchMovieClient = imDbSearchMovieClient;
        this.imDbMostPopularDataClient = imDbMostPopularDataClient;
    }

    @Override
    public void execute(Update update) {
        String chatId = getChatId(update);
        if (getMessage(update).equalsIgnoreCase(RANDOM.getCommandName())) {
            MostPopularDataDetail mostPopularDataDetail = imDbMostPopularDataClient.getRandomMovieDetailsFromMostPopularMovies();
            sendRandomMostPopularMovieInfo(chatId, mostPopularDataDetail);
            return;
        }
        String movieKeywords = Arrays.stream(getMessage(update).split(SPACE)).skip(1).limit(3).collect(Collectors.joining(SPACE));
        if (isAlphanumericSpace(movieKeywords)) {
            SearchResult searchResult = imDbSearchMovieClient.getRandomSearchResult(movieKeywords);
            if (isNull(searchResult)) {
                sendMovieNotFoundMessage(chatId, movieKeywords);
            } else sendRandomMovieByKeywordInfo(chatId, searchResult);
        } else sendMovieNotFoundMessage(chatId, movieKeywords);
    }

    private void sendRandomMostPopularMovieInfo(String chatId, MostPopularDataDetail mostPopularDataDetail) {
        String randomMostPopularMovieInfo = String.format("Let's take a look what I've just found for you:\n\n"
                        + "<b>%s</b>\n"
                        + "<b>IMDb rating:</b> %s\n"
                        + "<b>Crew:</b> %s\n"
                        + "<a href=\"%s\">&#8204;</a>\n"
                , mostPopularDataDetail.getFullTitle(),
                mostPopularDataDetail.getImDbRating(),
                mostPopularDataDetail.getCrew(),
                mostPopularDataDetail.getImage());
        sendBotMessageService.sendMessage(chatId, randomMostPopularMovieInfo);
    }

    private void sendRandomMovieByKeywordInfo(String chatId, SearchResult searchResult) {
        String randomMovieByKeywordInfo = String.format("Let's take a look what I've just found for you:\n\n"
                        + "<b>%s</b>\n"
                        + "<b>%s</b>\n"
                        + "<a href=\"%s\">&#8204;</a>\n"
                , searchResult.getTitle(),
                searchResult.getDescription(),
                searchResult.getImage());
        sendBotMessageService.sendMessage(chatId, randomMovieByKeywordInfo);
    }

    private void sendMovieNotFoundMessage(String chatId, String movieKeywords) {
        String movieNotFoundMessage = String.format("I didn't find a movie by these words: <b>%s</b>", movieKeywords);
        sendBotMessageService.sendMessage(chatId, movieNotFoundMessage);
    }

}
