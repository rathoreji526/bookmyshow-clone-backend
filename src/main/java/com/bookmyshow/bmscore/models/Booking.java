package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.BookingStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "bookings")
public class Booking extends GlobalFields{
    private UUID userId;
    private UUID showId;
    private BigDecimal totalPrice;
    private BookingStatus bookingStatus;

    @ManyToOne
    ShowSeat bookedSeats;
}
