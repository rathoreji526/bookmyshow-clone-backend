package com.bookmyshow.bmscore.kafka.events;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class BookingConfirmedEvent {
    private UUID bookingId;
}