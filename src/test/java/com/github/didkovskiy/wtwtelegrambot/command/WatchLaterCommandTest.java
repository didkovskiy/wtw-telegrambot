package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Collections;
import java.util.List;

@DisplayName("Unit-leve testing for WatchLaterCommand")
class WatchLaterCommandTest {

    private SendBotMessageService sendBotMessageService;
    private IMDbSearchMovieClient imDbSearchMovieClient;
    private WatchLaterCommand watchLaterCommand;
    private TelegramUserService telegramUserService;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        WatchLaterService watchLaterService = Mockito.mock(WatchLaterService.class);
        imDbSearchMovieClient = Mockito.mock(IMDbSearchMovieClient.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);

        watchLaterCommand = new WatchLaterCommand(sendBotMessageService, watchLaterService, imDbSearchMovieClient, telegramUserService);

    }

    @Test
    public void shouldSendMovieNotFoundMessage() {
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

        Mockito.when(imDbSearchMovieClient.getFirstSearchResult(movieTitle)).thenReturn(null);

        String movieNotFoundMessage = String.format("I didn't find a movie with a title <b>%s</b>", movieTitle);
        String movieNotFoundMessageForSlash = String.format("I didn't find a movie with a title <b>%s</b>", movieTitleWithSlash);

        //when
        watchLaterCommand.execute(update1);
        watchLaterCommand.execute(update2);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, movieNotFoundMessage);
        Mockito.verify(sendBotMessageService).sendMessage(chatId, movieNotFoundMessageForSlash);
    }

    @Test
    public void shouldReturnWatchLaterList() {
        //given
        String chatId = "1";
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        WatchLater watchLater1 = new WatchLater();
        watchLater1.setTitle("Title 1");
        watchLater1.setDescription("Description 1");
        WatchLater watchLater2 = new WatchLater();
        watchLater2.setTitle("Title 2");
        watchLater2.setDescription("Description 2");
        telegramUser.setWatchLaterList(List.of(watchLater1, watchLater2));

        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(java.util.Optional.of(telegramUser));

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message.getText()).thenReturn("/watchlater");
        update.setMessage(message);

        String rightWatchLaterList = "Your WatchLater list: \n\n" + "<b>Title:</b> Title 1 \n" + "<b>Description:</b> Description 1 \n" + "〰️〰️〰️〰️〰️〰️\n\n"
                + "<b>Title:</b> Title 2 \n" + "<b>Description:</b> Description 2 \n" + "〰️〰️〰️〰️〰️〰️\n\n";

        //when
        watchLaterCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, rightWatchLaterList);
    }

    @Test
    public void shouldSendThatWatchLaterListIsEmpty() {
        //given
        String chatId = "1";
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        telegramUser.setWatchLaterList(Collections.emptyList());

        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(java.util.Optional.of(telegramUser));

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        Mockito.when(message.getText()).thenReturn("/watchlater");
        update.setMessage(message);

        String watchLaterListIsEmpty = "Your WatchLater list is empty.";

        //when
        watchLaterCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, watchLaterListIsEmpty);
    }

}