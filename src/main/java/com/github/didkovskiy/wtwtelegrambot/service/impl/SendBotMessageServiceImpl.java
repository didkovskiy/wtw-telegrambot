package com.github.didkovskiy.wtwtelegrambot.service.impl;

import com.github.didkovskiy.wtwtelegrambot.bot.WhatToWatchTelegramBot;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Implementation of {@link SendBotMessageService} interface.
 */
@Service
@Slf4j
public class SendBotMessageServiceImpl implements SendBotMessageService {

    private final WhatToWatchTelegramBot wtwtelegrambot;

    @Autowired
    public SendBotMessageServiceImpl(WhatToWatchTelegramBot wtwtelegrambot) {
        this.wtwtelegrambot = wtwtelegrambot;
    }

    @Override
    public void sendMessage(String chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.enableHtml(true);
        sendMessage.setText(message);

        try {
            wtwtelegrambot.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error(e.getMessage());
        }
    }
}
