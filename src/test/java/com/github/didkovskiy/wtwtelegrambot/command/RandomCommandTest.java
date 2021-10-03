package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMostPopularDataClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.MostPopularDataDetail;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-leve testing for RandomCommand")
class RandomCommandTest {

    private SendBotMessageService sendBotMessageService;
    private IMDbMostPopularDataClient imDbMostPopularDataClient;
    private RandomCommand randomCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        IMDbSearchMovieClient imDbSearchMovieClient = Mockito.mock(IMDbSearchMovieClient.class);
        imDbMostPopularDataClient = Mockito.mock(IMDbMostPopularDataClient.class);

        randomCommand = new RandomCommand(sendBotMessageService, imDbSearchMovieClient, imDbMostPopularDataClient);
    }

    @Test
    public void shouldSendMovieNotFoundMessage() {
        //given
        String chatId = "1";
        String wrongQuery = "=/ .-+2";
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message.getText()).thenReturn("/random " + wrongQuery);
        update.setMessage(message);

        String movieNotFoundMessage = String.format("I didn't find a movie by these words: <b>%s</b>", wrongQuery);

        //when
        randomCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, movieNotFoundMessage);
    }

    @Test
    public void shouldSendRandomMostPopularMovieInfo() {
        //given
        String chatId = "1";
        String title = "Random Movie";
        String imdbRating = "10";
        String crew = "Random Crew";
        String image = "Random Image";


        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message.getText()).thenReturn("/random");
        update.setMessage(message);

        MostPopularDataDetail mostPopularDataDetail = new MostPopularDataDetail();
        mostPopularDataDetail.setFullTitle(title);
        mostPopularDataDetail.setImDbRating(imdbRating);
        mostPopularDataDetail.setCrew(crew);
        mostPopularDataDetail.setImage(image);

        Mockito.when(imDbMostPopularDataClient.getRandomMovieDetailsFromMostPopularMovies()).thenReturn(mostPopularDataDetail);
        String randomMostPopularMovieInfo = String.format("Let's take a look what I've just found for you:\n\n"
                        + "<b>%s</b>\n"
                        + "<b>IMDb rating:</b> %s\n"
                        + "<b>Crew:</b> %s\n"
                        + "<a href=\"%s\">&#8204;</a>\n"
                , title,
                imdbRating,
                crew,
                image);

        //when
        randomCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, randomMostPopularMovieInfo);
    }

}