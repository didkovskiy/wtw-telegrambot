package com.github.didkovskiy.wtwtelegrambot.service;

import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.repository.WatchLaterRepository;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import com.github.didkovskiy.wtwtelegrambot.service.impl.WatchLaterServiceImlp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

@DisplayName("Unit-level testing for WatchLaterService")
class WatchLaterServiceTest {

    private static final String CHAT_ID = "123";

    private WatchLaterService watchLaterService;
    private WatchLaterRepository watchLaterRepository;
    private TelegramUser newUser;

    @BeforeEach
    public void init(){
        watchLaterRepository = Mockito.mock(WatchLaterRepository.class);
        TelegramUserService telegramUserService = Mockito.mock(TelegramUserService.class);
        watchLaterService = new WatchLaterServiceImlp(watchLaterRepository, telegramUserService);

        newUser = new TelegramUser();
        newUser.setChatId(CHAT_ID);
        newUser.setActive(true);

        Mockito.when(telegramUserService.findByChatId(CHAT_ID)).thenReturn(Optional.of(newUser));
    }

    @Test
    public void shouldProperlySaveNewWatchLater(){
        //given
        SearchResult searchResult = new SearchResult();
        searchResult.setId("id1");
        searchResult.setTitle("Title");
        searchResult.setDescription("Description");

        WatchLater expectedWatchLater = new WatchLater();
        expectedWatchLater.setId(searchResult.getId());
        expectedWatchLater.setTitle(searchResult.getTitle());
        expectedWatchLater.setDescription(searchResult.getDescription());
        expectedWatchLater.addUser(newUser);

        //when
        watchLaterService.save(newUser.getChatId(), searchResult);

        //then
        Mockito.verify(watchLaterRepository).save(expectedWatchLater);
    }

}