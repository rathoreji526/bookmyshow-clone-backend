package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.BookingStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Booking extends GlobalFields{

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Show show;

    private Double totalPrice;

    @Enumerated(EnumType.STRING)
    private BookingStatus bookingStatus =  BookingStatus.PENDING;

    private LocalDateTime bookingDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "booking")
    private List<ShowSeat> bookedSeats;

    private LocalDateTime bookingExpiry;

    @OneToOne(fetch = FetchType.LAZY,  cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "transaction_id")
    private Transaction transaction;
}