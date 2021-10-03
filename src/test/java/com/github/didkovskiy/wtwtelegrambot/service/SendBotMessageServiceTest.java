package com.github.didkovskiy.wtwtelegrambot.service;

import com.github.didkovskiy.wtwtelegrambot.bot.WhatToWatchTelegramBot;
import com.github.didkovskiy.wtwtelegrambot.service.impl.SendBotMessageServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@DisplayName("Unit-level testing for SendBotMessageService")
class SendBotMessageServiceTest {

    private SendBotMessageService sendBotMessageService;
    private WhatToWatchTelegramBot wtwtelegrambot;

    @BeforeEach
    public void init() {
        wtwtelegrambot = Mockito.mock(WhatToWatchTelegramBot.class);
        sendBotMessageService = new SendBotMessageServiceImpl(wtwtelegrambot);
    }

    @Test
    public void shouldSendMessage() throws TelegramApiException {
        //given
        String chatId = "test_chat_id";
        String message = "test_message";

        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);

        //when
        sendBotMessageService.sendMessage(chatId, message);

        //then
        Mockito.verify(wtwtelegrambot).execute(sendMessage);
    }
}