package com.bookmyshow.bmscore.requestDTO;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class FindShowDTO {
    private UUID movieId;
    private LocalDateTime date;
}
