package com.github.didkovskiy.wtwtelegrambot.client.dto;

import lombok.Data;
import lombok.ToString;

/**
 * Search Result DTO class.
 */
@Data
@ToString
public class SearchResult {
    private String id;
    private String resultType;
    private String image;
    private String title;
    private String description;
}
