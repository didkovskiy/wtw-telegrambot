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
 * Integration-level testing for {@link WatchLaterRepository}.
 */
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
class WatchLaterRepositoryIT {

    @Autowired
    private WatchLaterRepository watchLaterRepository;

    @Sql(scripts = {"/sql/clearDbs.sql", "/sql/fiveWatchLaterRecordsForUser.sql"})
    @Test
    public void shouldGetAllUsersForWatchLater(){
        //when
        Optional<WatchLater> watchLaterRecordFromDB = watchLaterRepository.findById(("id1"));

        //then
        assertTrue(watchLaterRecordFromDB.isPresent());
        assertEquals("id1", watchLaterRecordFromDB.get().getId());
        List<TelegramUser> users = watchLaterRecordFromDB.get().getUsers();
        for (int i = 0; i < users.size(); i++) {
            assertEquals(String.valueOf(i + 1), users.get(i).getChatId());
            assertTrue(users.get(i).isActive());
        }

    }
}