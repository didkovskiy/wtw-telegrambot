package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.Impl.IMDbYouTubeClientImpl;
import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Integration-level testing for IMDbYouTubeClient")
class IMDbYouTubeClientTest {

    private final String imdbApiPath = "https://imdb-api.com/en/API/";
    private final String apiKey = "apikey";

    private final IMDbYouTubeClient youTubeClient = new IMDbYouTubeClientImpl(imdbApiPath, apiKey);

    @Test
    public void shouldReturnRightMovieTrailer() {
        //given
        String imdbIdOfMovie = "tt1375666";

        //when
        YouTubeTrailerData trailerData = youTubeClient.getYouTubeTrailer(imdbIdOfMovie);

        //then
        assertEquals("Inception (2010)", trailerData.getFullTitle());
    }

    @Test
    public void shouldContainYoutubeLink() {
        //given
        String imdbIdOfMovie = "tt1375666";
        String youTubeLinkPatter = "^https://www.youtube.com/watch.*";
        Pattern compiledPattern = Pattern.compile(youTubeLinkPatter);

        //when
        YouTubeTrailerData trailerData = youTubeClient.getYouTubeTrailer(imdbIdOfMovie);

        //then
        Matcher matcher = compiledPattern.matcher(trailerData.getVideoUrl());
        assertTrue(matcher.matches());
    }

}