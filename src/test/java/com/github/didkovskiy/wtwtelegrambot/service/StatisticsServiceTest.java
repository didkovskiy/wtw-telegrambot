package com.github.didkovskiy.wtwtelegrambot.service;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.impl.StatisticsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Unit-level testing for StatisticsService")
class StatisticsServiceTest {

    private WatchLaterService watchLaterService;
    private StatisticsService statisticsService;

    @BeforeEach
    public void init() {
        watchLaterService = Mockito.mock(WatchLaterService.class);
        statisticsService = new StatisticsServiceImpl(watchLaterService);
    }

    @Test
    public void shouldProperlyReturnMostPopularWatchLaterRecords() {
        //given
        List<TelegramUser> telegramUsers = List.of(new TelegramUser(),
                new TelegramUser(), new TelegramUser(), new TelegramUser(), new TelegramUser());

        WatchLater watchLaterWith5Users = new WatchLater();
        watchLaterWith5Users.setUsers(telegramUsers);

        WatchLater watchLaterWith4Users = new WatchLater();
        watchLaterWith4Users.setUsers(telegramUsers.subList(0, 4));

        WatchLater watchLaterWith3Users = new WatchLater();
        watchLaterWith3Users.setUsers(telegramUsers.subList(0, 3));

        WatchLater watchLaterWith2Users = new WatchLater();
        watchLaterWith2Users.setUsers(telegramUsers.subList(0, 2));

        WatchLater watchLaterWith1Users = new WatchLater();
        watchLaterWith1Users.setUsers(telegramUsers.subList(0, 1));

        WatchLater watchLaterWith0Users = new WatchLater();
        watchLaterWith0Users.setUsers(Collections.emptyList());

        List<WatchLater> watchLaterList = List.of(watchLaterWith0Users,
                watchLaterWith1Users, watchLaterWith2Users, watchLaterWith3Users, watchLaterWith4Users, watchLaterWith4Users);

        Mockito.when(watchLaterService.findAll()).thenReturn(watchLaterList);

        //when
        List<WatchLater> mostPopularWatchLaterRecords = statisticsService.getMostPopularWatchLaterRecords();

        //then
        assertEquals(watchLaterWith5Users, mostPopularWatchLaterRecords.get(0));
        assertEquals(watchLaterWith4Users, mostPopularWatchLaterRecords.get(1));
        assertEquals(watchLaterWith3Users, mostPopularWatchLaterRecords.get(2));
        assertEquals(watchLaterWith2Users, mostPopularWatchLaterRecords.get(3));
        assertEquals(watchLaterWith1Users, mostPopularWatchLaterRecords.get(4));

        assertEquals(5, mostPopularWatchLaterRecords.size());
    }

}