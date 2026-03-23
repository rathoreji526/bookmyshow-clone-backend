package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.BookingStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "bookings")
public class Booking extends GlobalFields{
    private UUID userId;
    private UUID showId;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus =  BookingStatus.PENDING;
    private List<UUID> bookedSeats;
    private LocalDateTime bookingExpiry;
    private UUID transactionId;
}
