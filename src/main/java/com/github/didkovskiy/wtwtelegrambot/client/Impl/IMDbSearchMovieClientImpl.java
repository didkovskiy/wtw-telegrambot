package com.github.didkovskiy.wtwtelegrambot.client.Impl;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbSearchMovieClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.*;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Implementation of the {@link IMDbSearchMovieClient} interface.
 */
@Component
@Slf4j
public class IMDbSearchMovieClientImpl implements IMDbSearchMovieClient {

    private final String imdbApiSearchMoviePath;

    public IMDbSearchMovieClientImpl(@Value("${imdb.api.path}") String imdbApi, @Value("${api.key}") String apiKey) {
        this.imdbApiSearchMoviePath = imdbApi + "SearchMovie/" + apiKey;
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
}
