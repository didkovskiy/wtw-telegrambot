package com.github.didkovskiy.wtwtelegrambot.command;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.SendBotMessageService;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.telegram.telegrambots.meta.api.objects.Update;

import javax.ws.rs.NotFoundException;

import java.util.List;

import static com.github.didkovskiy.wtwtelegrambot.command.CommandUtils.getChatId;

/**
 * Clear Watch Later {@link Command}.
 */
public class ClearWatchLaterCommand implements Command {

    private final SendBotMessageService sendBotMessageService;
    private final TelegramUserService telegramUserService;
    private final WatchLaterService watchLaterService;

    public ClearWatchLaterCommand(SendBotMessageService sendBotMessageService, TelegramUserService telegramUserService, WatchLaterService watchLaterService) {
        this.sendBotMessageService = sendBotMessageService;
        this.telegramUserService = telegramUserService;
        this.watchLaterService = watchLaterService;
    }

    @Override
    public void execute(Update update) {
        String chatId = getChatId(update);
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        List<WatchLater> allWatchLaterRecords = watchLaterService.findAll();
        allWatchLaterRecords.forEach(watchLater -> {
            watchLater.getUsers().remove(telegramUser);
            watchLaterService.save(watchLater);
        });
        sendBotMessageService.sendMessage(chatId, "Your 'WatchLater' list was cleared \uD83D\uDE3F.");
    }
}
