package com.github.didkovskiy.wtwtelegrambot.service.impl;

import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.repository.WatchLaterRepository;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.TelegramUserService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.Optional;

/**
 * Implementation of {@link com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService}.
 */
@Service
public class WatchLaterServiceImlp implements WatchLaterService {

    private final WatchLaterRepository watchLaterRepository;
    private final TelegramUserService telegramUserService;

    public WatchLaterServiceImlp(WatchLaterRepository watchLaterRepository, TelegramUserService telegramUserService) {
        this.watchLaterRepository = watchLaterRepository;
        this.telegramUserService = telegramUserService;
    }

    @Override
    public WatchLater save(String chatId, SearchResult searchResult) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //todo add exception handling
        WatchLater watchLater;
        Optional<WatchLater> watchLaterFromDB = watchLaterRepository.findById(searchResult.getId());
        if (watchLaterFromDB.isPresent()) {
            watchLater = watchLaterFromDB.get();
            Optional<TelegramUser> first = watchLater.getUsers().stream()
                    .filter(it -> it.getChatId().equalsIgnoreCase(chatId))
                    .findFirst();
            if (first.isEmpty()) {
                watchLater.addUser(telegramUser);
            }
        } else {
            watchLater = new WatchLater();
            watchLater.setId(searchResult.getId());
            watchLater.setTitle(searchResult.getTitle());
            watchLater.setDescription(searchResult.getDescription());
            watchLater.addUser(telegramUser);
        }
        return watchLaterRepository.save(watchLater);
    }
}
