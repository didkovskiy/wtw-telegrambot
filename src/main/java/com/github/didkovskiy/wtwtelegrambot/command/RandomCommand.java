package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMovieClient;
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
    private final IMDbMovieClient imDbMovieClient;

    public RandomCommand(SendBotMessageService sendBotMessageService, IMDbMovieClient imDbMovieClient) {
        this.sendBotMessageService = sendBotMessageService;
        this.imDbMovieClient = imDbMovieClient;
    }

    @Override
    public void execute(Update update) {
        String chatId = getChatId(update);
        if (getMessage(update).equalsIgnoreCase(RANDOM.getCommandName())) {
            MostPopularDataDetail mostPopularDataDetail = imDbMovieClient.getRandomMovieDetailsFromMostPopularMovies();
            sendRandomMostPopularMovieInfo(chatId, mostPopularDataDetail);
            return;
        }
        String watchLaterMovieKeywords = Arrays.stream(getMessage(update).split(SPACE)).skip(1).limit(3).collect(Collectors.joining(SPACE));
        if (isAlphanumericSpace(watchLaterMovieKeywords)) {
            SearchResult searchResult = imDbMovieClient.getRandomSearchResult(watchLaterMovieKeywords);
            if (isNull(searchResult)) {
                sendMovieNotFoundMessage(chatId, watchLaterMovieKeywords);
            } else sendRandomMovieByKeywordInfo(chatId, searchResult);
        } else sendMovieNotFoundMessage(chatId, watchLaterMovieKeywords);
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

    private void sendMovieNotFoundMessage(String chatId, String watchLaterMovieKeywords) {
        String movieNotFoundMessage = String.format("I didn't find a movie by these words: <b>%s</b>", watchLaterMovieKeywords);
        sendBotMessageService.sendMessage(chatId, movieNotFoundMessage);
    }

}
