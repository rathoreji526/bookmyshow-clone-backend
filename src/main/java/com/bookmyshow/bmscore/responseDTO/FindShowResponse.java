package com.bookmyshow.bmscore.responseDTO;

import com.bookmyshow.bmscore.enums.Format;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;
import java.util.UUID;

public interface FindShowResponse {
    UUID getId();
    LocalDateTime getStartTime();
    LocalDateTime getEndTime();
    Format getFormat();
    @Value("#{target.screen.theater.location.city}")
    String getCity();
    @Value("#{target.screen.theater.location.zipcode}")
    String getZipcode();
    @Value("#{target.screen.theater.name}")
    String getTheaterName();
}
