package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class DateRangeDTO {
    private LocalDateTime startDate;
    private LocalDateTime endDate;
}
