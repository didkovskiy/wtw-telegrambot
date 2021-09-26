package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMovieClient;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

@DisplayName("Unit-leve testing for WatchLaterCommand")
class WatchLaterCommandTest {

    private SendBotMessageService sendBotMessageService;
    private IMDbMovieClient imDbMovieClient;
    private WatchLaterCommand watchLaterCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        WatchLaterService watchLaterService = Mockito.mock(WatchLaterService.class);
        imDbMovieClient = Mockito.mock(IMDbMovieClient.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);

        watchLaterCommand = new WatchLaterCommand(sendBotMessageService, watchLaterService, imDbMovieClient, telegramUserService);

    }

    @Test
    public void shouldProperlySendMovieNotFoundMessage() {
        //given
        String chatId = "1";
        String movieTitle = "fgweyug78diwed24r";
        String movieTitleWithSlash = "/movie/";

        Update update1 = new Update();
        Message message1 = Mockito.mock(Message.class);
        Mockito.when(message1.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message1.getText()).thenReturn("/watchlater " + movieTitle);
        update1.setMessage(message1);

        Update update2 = new Update();
        Message message2 = Mockito.mock(Message.class);
        Mockito.when(message2.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message2.getText()).thenReturn("/watchlater " + movieTitleWithSlash);
        update2.setMessage(message2);

        Mockito.when(imDbMovieClient.getFirstSearchResult(movieTitle)).thenReturn(null);

        String movieNotFoundMessage = String.format("I didn't find a movie with a title <b>%s</b>", movieTitle);
        String movieNotFoundMessageForSlash = String.format("I didn't find a movie with a title <b>%s</b>", movieTitleWithSlash);

        //when
        watchLaterCommand.execute(update1);
        watchLaterCommand.execute(update2);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, movieNotFoundMessage);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, movieNotFoundMessageForSlash);
    }
}