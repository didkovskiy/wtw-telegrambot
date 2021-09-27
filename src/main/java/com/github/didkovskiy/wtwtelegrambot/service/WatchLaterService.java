package com.github.didkovskiy.wtwtelegrambot.service;

import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * {@link Service} for manipulating {@link com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater} entities.
 */
public interface WatchLaterService {
    /**
     * Save {@link WatchLater} entity in Database.
     *
     * @param chatId       chat id of a specific Telegram user.
     * @param searchResult {@link SearchResult} object provided data of a specific movie to save.
     * @return {@link WatchLater} saved object;
     */
    WatchLater save(String chatId, SearchResult searchResult);

    /**
     * Save {@link WatchLater} entity in Database.
     *
     * @param watchLater {@link WatchLater} object you need to save.
     * @return {@link WatchLater} saved object;
     */
    WatchLater save(WatchLater watchLater);

    /**
     * Find {@link WatchLater} entity by id.
     *
     * @param id id of a specific WatchLater record.
     * @return {@link Optional} of {@link WatchLater} object.
     */
    Optional<WatchLater> findById(String id);

}
