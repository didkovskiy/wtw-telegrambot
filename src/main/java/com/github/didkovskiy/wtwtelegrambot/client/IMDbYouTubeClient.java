package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;

/**
 * Client for IMDb API to get YouTube data information.
 */
public interface IMDbYouTubeClient {
    /**
     * Get {@link YouTubeTrailerData} object from provided IMDb film ID.
     *
     * @param id provided IMDb film ID.
     * @return {@link YouTubeTrailerData} object.
     */
    YouTubeTrailerData getYouTubeTrailer(String id);
}
