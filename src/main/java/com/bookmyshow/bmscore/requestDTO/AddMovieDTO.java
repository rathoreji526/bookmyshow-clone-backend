package com.bookmyshow.bmscore.requestDTO;

import com.bookmyshow.bmscore.enums.Language;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class AddMovieDTO {
    private UUID theaterId;
    private String name;
    private Integer duration;
    private Language language;
    private LocalDateTime releaseDate;
    private Double rating;
}
