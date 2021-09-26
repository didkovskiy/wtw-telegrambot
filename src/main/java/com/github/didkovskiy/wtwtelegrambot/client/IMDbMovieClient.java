package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchData;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;

/**
 * Client for IMDb API for information about movies.
 */
public interface IMDbMovieClient {

    /**
     * Get {@link SearchData} object by provided query.
     *
     * @param query provided query.
     * @return {@link SearchData} object.
     */
    SearchData getFullSearchData(String query);

    /**
     * Get first {@link SearchResult} object from provided query.
     *
     * @param query provided search query.
     * @return {@link SearchResult} object.
     */
    SearchResult getFirstSearchResult(String query);

    /**
     * Get random {@link SearchResult} object from provided query words.
     *
     * @param query provided search query.
     * @return {@link SearchResult} object.
     */
    SearchResult getRandomSearchResult(String query);

    /**
     * Get {@link YouTubeTrailerData} object from provided IMDb film ID.
     *
     * @param id provided IMDb film ID.
     * @return {@link YouTubeTrailerData} object.
     */
    YouTubeTrailerData getYouTubeTrailer(String id);

}
