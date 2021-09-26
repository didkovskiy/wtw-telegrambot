package com.github.didkovskiy.wtwtelegrambot.client;

import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchData;
import com.github.didkovskiy.wtwtelegrambot.client.dto.SearchResult;
import com.github.didkovskiy.wtwtelegrambot.client.dto.YouTubeTrailerData;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Implementation of the {@link IMDbMovieClient} interface.
 */
@Component
public class IMDbMovieClientImpl implements IMDbMovieClient {

    private final String imdbApiSearchMoviePath;
    private final String imdbApiYouTubeTrailerPath;

    public IMDbMovieClientImpl(@Value("${imdb.api.path}") String imdbApi, @Value("${api.key}") String apiKey) {
        this.imdbApiSearchMoviePath = imdbApi + "SearchMovie/" + apiKey;
        this.imdbApiYouTubeTrailerPath = imdbApi + "YouTubeTrailer/" + apiKey;
    }

    @Override
    public SearchData getFullSearchData(String query) {
        return Unirest.get(imdbApiSearchMoviePath + query)
                .asObject(SearchData.class)
                .getBody();
    }

    @Override
    public SearchResult getFirstSearchResult(String query) {
        List<SearchResult> resultList = Unirest.get(imdbApiSearchMoviePath + query)
                .asObject(SearchData.class)
                .getBody()
                .getResults();
        if (resultList.isEmpty()) return null;
        else return resultList.get(0);
    }

    @Override
    public SearchResult getRandomSearchResult(String query) {
        List<SearchResult> resultList = Unirest.get(imdbApiSearchMoviePath + query)
                .asObject(SearchData.class)
                .getBody()
                .getResults();
        if (resultList.isEmpty()) return null;
        else return resultList.get(ThreadLocalRandom.current().nextInt(resultList.size()));
    }

    @Override
    public YouTubeTrailerData getYouTubeTrailer(String id) {
        return Unirest.get(imdbApiYouTubeTrailerPath + id)
                .asObject(YouTubeTrailerData.class)
                .getBody();
    }
}
