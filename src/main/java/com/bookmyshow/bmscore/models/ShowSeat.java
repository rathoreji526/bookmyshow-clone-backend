package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "show_seats")
public class ShowSeat extends GlobalFields{
    @ManyToOne
    private Seat seat;

    @ManyToOne
    private Movie movie;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus;

    private LocalDateTime lockTime; //for auto unlock on failed bookings
}
