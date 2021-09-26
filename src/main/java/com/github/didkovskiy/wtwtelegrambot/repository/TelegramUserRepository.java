package com.github.didkovskiy.wtwtelegrambot.repository;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * {@link Repository} for handling with {@link TelegramUser} entity.
 */
public interface TelegramUserRepository extends JpaRepository<TelegramUser, String> {
    List<TelegramUser> findAllByActiveTrue();
}