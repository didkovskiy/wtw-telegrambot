package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchData;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Integration-level testing for IMDbMovieClient")
class IMDbMovieClientTest {

    private final String imdbApiPath = "https://imdb-api.com/en/API/";
    private final String apiKey = "apikey";

    private final IMDbMovieClient movieClient = new IMDbMovieClientImpl(imdbApiPath, apiKey);

    @Test
    public void shouldProperlyReturnEmptyResultList(){
        //given
        String query = "74t1kk4r3wehlku34bjb";

        //when
        List<SearchResult> resultList = movieClient.getFullSearchData(query).getResults();

        //then
        assertTrue(resultList.isEmpty());
    }

    @Test
    public void shouldProperlyReturnMovieTitle(){
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
    public void shouldProperlyContainNonEmptyResult(){
        //given
        String query = "sex drugs alcohol";

        //when
        SearchResult randomResult = movieClient.getRandomSearchResult(query);

        //then
        assertNotNull(randomResult);
    }

    @Test
    public void shouldProperlyReturnRightMovieTrailer(){
        //given
        String imdbIdOfMovie = "tt1375666";

        //when
        YouTubeTrailerData trailerData = movieClient.getYouTubeTrailer(imdbIdOfMovie);

        //then
        assertEquals("Inception (2010)", trailerData.getFullTitle());
    }

    @Test
    public void shouldContainYoutubeLink(){
        //given
        String imdbIdOfMovie = "tt1375666";
        String youTubeLinkPatter = "^https://www.youtube.com/watch.*";
        Pattern compiledPattern = Pattern.compile(youTubeLinkPatter);

        //when
        YouTubeTrailerData trailerData = movieClient.getYouTubeTrailer(imdbIdOfMovie);

        //then
        Matcher matcher = compiledPattern.matcher(trailerData.getVideoUrl());
        assertTrue(matcher.matches());
    }
}