package com.github.didkovskiy.wtwtelegrambot.repository;

import com.github.didkovskiy.wtwtelegrambot.repository.entity.WatchLater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link Repository} for handling with {@link WatchLater} entity.
 */
public interface WatchLaterRepository extends JpaRepository<WatchLater, String> {
}
