package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.dto.MostPopularDataDetail;

/**
 * Client for IMDb API to get information about most popular movies.
 */
public interface IMDbMostPopularDataClient {
    /**
     * Get random {@link MostPopularDataDetail} object from the list of most popular movies.
     *
     * @return {@link MostPopularDataDetail} object.
     */
    MostPopularDataDetail getRandomMovieDetailsFromMostPopularMovies();
}
