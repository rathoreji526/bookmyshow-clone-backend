package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.SeatStatus;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "show_seats",
       uniqueConstraints={
        @UniqueConstraint(columnNames = {"seat_id" , "show_id"})
       })
public class ShowSeat extends GlobalFields{
    private double price;
    @JsonBackReference
    @ManyToOne
    private Seat seat;

    @JsonBackReference
    @ManyToOne
    private Show show;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus = SeatStatus.AVAILABLE;
    private UUID lockedBy;
    private LocalDateTime lockExpiry; //for auto unlock on failed bookings
}
