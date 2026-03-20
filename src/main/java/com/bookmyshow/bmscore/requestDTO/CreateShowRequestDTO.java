package com.bookmyshow.bmscore.requestDTO;

import com.bookmyshow.bmscore.enums.Format;
import com.bookmyshow.bmscore.enums.Language;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CreateShowRequestDTO {
    private String movieId;
    private String screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Language language;
    private Format format;
}
