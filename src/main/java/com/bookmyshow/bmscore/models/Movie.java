package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.Language;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "movies")
public class Movie extends GlobalFields{
    private String name;
    private String description;
    private Integer duration;
    @Enumerated(EnumType.STRING)
    private Language language;
    private LocalDateTime releaseDate;
    private String posterUrl;
    private boolean isActive = true;
    private Double rating;
}
