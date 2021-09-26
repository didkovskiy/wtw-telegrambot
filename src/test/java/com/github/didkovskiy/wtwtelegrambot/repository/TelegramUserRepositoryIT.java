package com.github.didkovskiy.wtwtelegrambot.repository;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

/**
 * Integration-level testing for {@link TelegramUserRepository}.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class TelegramUserRepositoryIT {

    @Autowired
    private TelegramUserRepository telegramUserRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/telegram_users.sql"})
    @Test
    public void shouldProperlyFindAllActiveUsers() {
        //when
        List<TelegramUser> users = telegramUserRepository.findAllByActiveTrue();

        //then
        assertEquals(5, users.size());
    }

    @Sql(scripts = {"/sql/clearDbs.sql"})
    @Test
    public void shouldProperlySaveTelegramUser() {
        //given
        TelegramUser telegramUser = new TelegramUser();
        telegramUser.setChatId("1234567890");
        telegramUser.setActive(false);
        telegramUserRepository.save(telegramUser);

        //when
        Optional<TelegramUser> saved = telegramUserRepository.findById(telegramUser.getChatId());

        //then
        assertTrue(saved.isPresent());
        assertEquals(telegramUser, saved.get());
    }

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveWatchLaterRecordsForUser.sql"})
    @Test
    public void shouldProperlyGetAllWatchLaterRecordsForUser() {
        //when
        Optional<TelegramUser> userFromDB = telegramUserRepository.findById("1");

        //then
        assertTrue(userFromDB.isPresent());
        List<WatchLater> watchLaterList = userFromDB.get().getWatchLaterList();
        for (int i = 0; i < watchLaterList.size(); i++) {
            assertEquals(String.format("id%s", (i + 1)), watchLaterList.get(i).getId());
            assertEquals(String.format("t%s", (i + 1)), watchLaterList.get(i).getTitle());
            assertEquals(String.format("desc%s", (i + 1)), watchLaterList.get(i).getDescription());
        }
    }
}