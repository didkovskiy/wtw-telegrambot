package com.github.didkovskiy.wtwtelegrambot.client.Impl;

import com.github.didkovskiy.wtwtelegrambot.client.IMDbYouTubeClient;
import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;
import kong.unirest.Unirest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;

/**
 * Implementation of the {@link IMDbYouTubeClient} interface.
 */
@Component
@Slf4j
public class IMDbYouTubeClientImpl implements IMDbYouTubeClient {

    private final String imdbApiYouTubeTrailerPath;

    public IMDbYouTubeClientImpl(@Value("${imdb.api.path}") String imdbApi, @Value("${api.key}") String apiKey) {
        this.imdbApiYouTubeTrailerPath = imdbApi + "YouTubeTrailer/" + apiKey;
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

}
