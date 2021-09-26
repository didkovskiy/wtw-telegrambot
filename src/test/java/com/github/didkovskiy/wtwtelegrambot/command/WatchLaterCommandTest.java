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

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message.getText()).thenReturn("/watchlater " + movieTitle);
        update.setMessage(message);

        Mockito.when(imDbMovieClient.getFirstSearchResult(movieTitle)).thenReturn(null);

        String movieNotFoundMessage = String.format("I didn't find a movie with a title <b>%s</b>", movieTitle);

        //when
        watchLaterCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, movieNotFoundMessage);
    }
}