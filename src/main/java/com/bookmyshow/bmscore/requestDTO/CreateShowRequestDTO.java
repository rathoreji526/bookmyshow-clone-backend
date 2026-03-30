package com.bookmyshow.bmscore.requestDTO;

import com.bookmyshow.bmscore.enums.Format;
import com.bookmyshow.bmscore.enums.Language;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateShowRequestDTO {
    private UUID theaterId;
    private UUID movieId;
    private UUID screenId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Language language;
    private Format format;
    private Double price_silver , price_gold , price_premium;
}
