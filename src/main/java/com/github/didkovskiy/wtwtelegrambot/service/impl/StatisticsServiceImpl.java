package com.github.didkovskiy.wtwtelegrambot.service.impl;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.StatisticsService;
import com.github.didkovskiy.wtwtelegrambot.service.WatchLaterService;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Implementation of {@link StatisticsService}
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private final WatchLaterService watchLaterService;

    public StatisticsServiceImpl(WatchLaterService watchLaterService) {
        this.watchLaterService = watchLaterService;
    }

    @Override
    public List<WatchLater> getMostPopularWatchLaterRecords() {
        return watchLaterService.findAll().stream()
                .sorted(Comparator.comparing(WatchLater::getUsersCount).reversed())
                .limit(5)
                .collect(Collectors.toList());
    }
}
