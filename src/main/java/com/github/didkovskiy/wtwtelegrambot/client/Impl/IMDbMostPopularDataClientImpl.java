package com.github.didkovskiy.wtwtelegrambot.client.Impl;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbMostPopularDataClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.MostPopularData;
import com.github.didkovskiy.wtwtelegrambot.client.dto.MostPopularDataDetail;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Implementation of the {@link IMDbMostPopularDataClient} interface.
 */
@Component
@Slf4j
public class IMDbMostPopularDataClientImpl implements IMDbMostPopularDataClient {

    private final String imdbApiMostPopularMoviesPath;

    public IMDbMostPopularDataClientImpl(@Value("${imdb.api.path}") String imdbApi, @Value("${api.key}") String apiKey) {
        this.imdbApiMostPopularMoviesPath = imdbApi + "MostPopularMovies/" + apiKey;
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
