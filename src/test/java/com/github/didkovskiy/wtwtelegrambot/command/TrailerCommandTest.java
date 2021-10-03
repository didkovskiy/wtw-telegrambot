package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.IMDbYouTubeClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-leve testing for TrailerCommand")
class TrailerCommandTest {

    private SendBotMessageService sendBotMessageService;
    private IMDbSearchMovieClient imDbSearchMovieClient;
    private IMDbYouTubeClient imDbYouTubeClient;
    private TrailerCommand trailerCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        imDbSearchMovieClient = Mockito.mock(IMDbSearchMovieClient.class);
        imDbYouTubeClient = Mockito.mock(IMDbYouTubeClient.class);

        trailerCommand = new TrailerCommand(sendBotMessageService, imDbSearchMovieClient, imDbYouTubeClient);
    }

    @Test
    public void shouldSendTrailerNotFoundMessage() {
        //given
        String chatId = "1";
        String wrongQuery = "=/ .-+2";
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message.getText()).thenReturn("/trailer " + wrongQuery);
        update.setMessage(message);

        String trailerNotFoundMessage = String.format("I didn't find a trailer for a movie with a title <b>%s</b>", wrongQuery);

        //when
        trailerCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, trailerNotFoundMessage);
    }

    @Test
    public void shouldProperlySendTrailerInfo() {
        //given
        String chatId = "1";
        String query = "John Wick 3";
        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message.getText()).thenReturn("/trailer " + query);
        update.setMessage(message);

        SearchResult searchResult = new SearchResult();
        searchResult.setId("id of movie John Wick 3");

        YouTubeTrailerData youTubeTrailerData = new YouTubeTrailerData();
        youTubeTrailerData.setFullTitle("John Wick 3");
        youTubeTrailerData.setVideoUrl("https://youtube.com/trailer_of_john_wick_3");

        Mockito.when(imDbSearchMovieClient.getFirstSearchResult(query)).thenReturn(searchResult);
        Mockito.when(imDbYouTubeClient.getYouTubeTrailer(searchResult.getId())).thenReturn(youTubeTrailerData);

        String trailerInfo = String.format("Hey, I found this: \n\n"
                        + "<b>%s</b>\n"
                        + "%s\n"
                , "John Wick 3", "https://youtube.com/trailer_of_john_wick_3");

        //when
        trailerCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, trailerInfo);
    }

}