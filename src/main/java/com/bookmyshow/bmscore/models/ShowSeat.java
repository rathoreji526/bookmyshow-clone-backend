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
@Table(name = "show_seats",
       uniqueConstraints={
        @UniqueConstraint(columnNames = {"seat_id" , "show_id"})
       })
public class ShowSeat extends GlobalFields{
    @ManyToOne
    private Seat seat;

    @ManyToOne
    private Show show;

    @Enumerated(EnumType.STRING)
    private SeatStatus seatStatus = SeatStatus.AVAILABLE;

    private LocalDateTime lockTime; //for auto unlock on failed bookings
}
