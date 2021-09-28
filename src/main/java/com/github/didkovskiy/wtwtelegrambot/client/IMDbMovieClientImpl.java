package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.dto.*;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Implementation of the {@link IMDbMovieClient} interface.
 */
@Component
@Slf4j
public class IMDbMovieClientImpl implements IMDbMovieClient {

    private final String imdbApiSearchMoviePath;
    private final String imdbApiYouTubeTrailerPath;
    private final String imdbApiMostPopularMoviesPath;

    public IMDbMovieClientImpl(@Value("${imdb.api.path}") String imdbApi, @Value("${api.key}") String apiKey) {
        this.imdbApiSearchMoviePath = imdbApi + "SearchMovie/" + apiKey;
        this.imdbApiYouTubeTrailerPath = imdbApi + "YouTubeTrailer/" + apiKey;
        this.imdbApiMostPopularMoviesPath = imdbApi + "MostPopularMovies/" + apiKey;
    }

    @Override
    public SearchData getFullSearchData(String query) {
        SearchData searchData = Unirest.get(imdbApiSearchMoviePath + query)
                .asObject(SearchData.class)
                .getBody();
        if (isNotEmpty(searchData.getErrorMessage()))
            log.error(searchData.getErrorMessage());
        return searchData;
    }

    @Override
    public SearchResult getFirstSearchResult(String query) {
        SearchData searchData = Unirest.get(imdbApiSearchMoviePath + query)
                .asObject(SearchData.class)
                .getBody();
        if (isNotEmpty(searchData.getErrorMessage()))
            log.error(searchData.getErrorMessage());
        List<SearchResult> resultList = searchData.getResults();
        if (resultList.isEmpty()) return null;
        else return resultList.get(0);
    }

    @Override
    public SearchResult getRandomSearchResult(String query) {
        SearchData searchData = Unirest.get(imdbApiSearchMoviePath + query)
                .asObject(SearchData.class)
                .getBody();
        if (isNotEmpty(searchData.getErrorMessage()))
            log.error(searchData.getErrorMessage());
        List<SearchResult> resultList = searchData.getResults();
        if (resultList.isEmpty()) return null;
        else return resultList.get(ThreadLocalRandom.current().nextInt(resultList.size()));
    }

    @Override
    public YouTubeTrailerData getYouTubeTrailer(String id) {
        YouTubeTrailerData youTubeTrailerData = Unirest.get(imdbApiYouTubeTrailerPath + id)
                .asObject(YouTubeTrailerData.class)
                .getBody();
        if (isNotEmpty(youTubeTrailerData.getErrorMessage()))
            log.error(youTubeTrailerData.getErrorMessage());
        return youTubeTrailerData;
    }

    @Override
    public MostPopularDataDetail getRandomMovieDetailsFromMostPopularMovies() {
        MostPopularData mostPopularData = Unirest.get(imdbApiMostPopularMoviesPath)
                .asObject(MostPopularData.class)
                .getBody();
        if (isNotEmpty(mostPopularData.getErrorMessage()))
            log.error(mostPopularData.getErrorMessage());
        List<MostPopularDataDetail> items = mostPopularData.getItems();
        return items.get(ThreadLocalRandom.current().nextInt(items.size()));
    }
}
