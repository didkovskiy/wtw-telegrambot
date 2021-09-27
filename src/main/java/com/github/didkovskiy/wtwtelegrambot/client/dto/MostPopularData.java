package com.github.didkovskiy.wtwtelegrambot.client.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * DTO class for search data of most popular movies.
 */
@Data
@ToString
public class MostPopularData {
    private final List<MostPopularDataDetail> items;
    private final String errorMessage;
}
