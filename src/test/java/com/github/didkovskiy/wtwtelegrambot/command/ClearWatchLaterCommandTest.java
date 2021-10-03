package com.github.didkovskiy.wtwtelegrambot.command;

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

import java.util.ArrayList;
import java.util.List;

@DisplayName("Unit-leve testing for ClearWatchLaterCommand")
class ClearWatchLaterCommandTest {

    private SendBotMessageService sendBotMessageService;
    private TelegramUserService telegramUserService;
    private WatchLaterService watchLaterService;
    private ClearWatchLaterCommand clearWatchLaterCommand;

    @BeforeEach
    public void init() {
        sendBotMessageService = Mockito.mock(SendBotMessageService.class);
        watchLaterService = Mockito.mock(WatchLaterService.class);
        telegramUserService = Mockito.mock(TelegramUserService.class);

        clearWatchLaterCommand = new ClearWatchLaterCommand(sendBotMessageService, telegramUserService, watchLaterService);
    }

    @Test
    public void shouldSendWatchLaterListWasCleared() {
        //given
        String chatId = "1";
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId(chatId);
        List<TelegramUser> users = new ArrayList<>();
        users.add(telegramUser);
        WatchLater watchLater1 = new WatchLater();
        watchLater1.setTitle("Title 1");
        watchLater1.setDescription("Description 1");
        watchLater1.setUsers(users);
        WatchLater watchLater2 = new WatchLater();
        watchLater2.setTitle("Title 2");
        watchLater2.setDescription("Description 2");
        watchLater2.setUsers(users);
        List<WatchLater> watchLaterList = List.of(watchLater1, watchLater2);

        Update update = new Update();
        Message message = Mockito.mock(Message.class);
        Mockito.when(message.getChatId()).thenReturn(Long.valueOf(chatId));
        update.setMessage(message);

        Mockito.when(telegramUserService.findByChatId(chatId)).thenReturn(java.util.Optional.of(telegramUser));
        Mockito.when(watchLaterService.findAll()).thenReturn(watchLaterList);
        Mockito.when(watchLaterService.save(watchLater1)).thenReturn(null);
        Mockito.when(watchLaterService.save(watchLater2)).thenReturn(null);

        String successMessage = "Your 'WatchLater' list was cleared \uD83D\uDE3F.";

        //when
        clearWatchLaterCommand.execute(update);

        //then
        Mockito.verify(sendBotMessageService).sendMessage(chatId, successMessage);
    }

}