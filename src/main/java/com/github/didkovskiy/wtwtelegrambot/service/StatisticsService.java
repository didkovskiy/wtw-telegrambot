package com.github.didkovskiy.wtwtelegrambot.service;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;

import java.util.List;

/**
 * Service for collecting statistics about telegram bot usage.
 */
public interface StatisticsService {

    /**
     * Get list of most popular {@link WatchLater} records of all users.
     *
     * @return list of {@link WatchLater}'s.
     */
    List<WatchLater> getMostPopularWatchLaterRecords();
}
