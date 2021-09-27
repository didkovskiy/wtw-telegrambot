package com.github.didkovskiy.wtwtelegrambot.client.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Most Popular Data Detail DTO class for storing details about most popular movies.
 */
@Data
@ToString
public class MostPopularDataDetail {
    private String id;
    private String rank;
    private String rankUpDown;
    private String title;
    private String fullTitle;
    private String year;
    private String image;
    private String crew;
    private String imDbRating;
    private String imDbRatingCount;
}
