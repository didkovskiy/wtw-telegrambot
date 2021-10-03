package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.Impl.IMDbMostPopularDataClientImpl;
import com.github.didkovskiy.wtwtelegrambot.client.dto.MostPopularDataDetail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DisplayName("Integration-level testing for IMDbMostPopularDataClient")
class IMDbMostPopularDataClientTest {

    private final String imdbApiPath = "https://imdb-api.com/en/API/";
    private final String apiKey = "apikey";

    private final IMDbMostPopularDataClient imDbMostPopularDataClient = new IMDbMostPopularDataClientImpl(imdbApiPath, apiKey);

    @Test
    public void shouldContainNonEmptyMostPopularDataDetails(){
        //when
        MostPopularDataDetail randomMovieDetails = imDbMostPopularDataClient.getRandomMovieDetailsFromMostPopularMovies();

        //then
        assertNotNull(randomMovieDetails);
    }

}