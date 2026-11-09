package com.bookmyshow.bmscore.responseDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public interface MovieResponse {
    UUID getId();
    String getName();
    String getDescription();
    String getPosterUrl();
    Double getRating();
    LocalDateTime getReleaseDate();
    default LocalDate getReleaseDateOnly(){
        return getReleaseDate()==null ? null : getReleaseDate().toLocalDate();
    }
}
