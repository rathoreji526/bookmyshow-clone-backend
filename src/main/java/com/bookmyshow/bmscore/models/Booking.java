package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "bookings")
public class Booking extends GlobalFields{
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Show show;
    private Double totalPrice;
    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus =  BookingStatus.PENDING;
    @OneToMany
    @JsonManagedReference
    private List<ShowSeat> bookedSeats;
    private LocalDateTime bookingExpiry;

    @OneToOne(cascade = CascadeType.ALL)
    private Transaction transaction;
}