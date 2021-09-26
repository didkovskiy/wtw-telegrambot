package com.github.didkovskiy.wtwtelegrambot.client.dto;

import lombok.Data;
import lombok.ToString;

/**
 * DTO class for trailer data from YouTube.
 */
@Data
@ToString
public class YouTubeTrailerData {
    private String imDbId;
    private String title;
    private String fullTitle;
    private String type;
    private String year;
    private String videoId;
    private String videoUrl;
    private String errorMessage;
}
