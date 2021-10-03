package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.Impl.IMDbSearchMovieClientImpl;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchData;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration-level testing for IMDbSearchMovieClient")
class IMDbSearchMovieClientTest {

    private final String imdbApiPath = "https://imdb-api.com/en/API/";
    private final String apiKey = "apikey";

    private final IMDbSearchMovieClient movieClient = new IMDbSearchMovieClientImpl(imdbApiPath, apiKey);

    @Test
    public void shouldReturnEmptyResultList() {
        //given
        String query = "74t1kk4r3wehlku34bjb";

        //when
        List<SearchResult> resultList = movieClient.getFullSearchData(query).getResults();

        //then
        assertTrue(resultList.isEmpty());
    }

    @Test
    public void shouldReturnMovieTitle() {
        //given
        String query = "Matrix";

        //when
        SearchData searchData = movieClient.getFullSearchData(query);
        SearchResult result = movieClient.getFirstSearchResult(query);

        //then
        assertEquals("The Matrix", result.getTitle());
        assertEquals(query, searchData.getExpression());
    }

    @Test
    public void shouldContainNonEmptyResult() {
        //given
        String query = "sex drugs alcohol";

        //when
        SearchResult randomResult = movieClient.getRandomSearchResult(query);

        //then
        assertNotNull(randomResult);
    }
}