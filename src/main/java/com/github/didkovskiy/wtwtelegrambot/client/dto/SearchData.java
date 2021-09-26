package com.github.didkovskiy.wtwtelegrambot.client.dto;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * DTO class for search data of a specific movie.
 */
@Data
@ToString
public class SearchData {
    private final String searchType;
    private final String expression;
    private final List<SearchResult> results;
    private final String errorMessage;
}
