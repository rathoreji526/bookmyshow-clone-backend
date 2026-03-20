package com.bookmyshow.bmscore.models;

import com.bookmyshow.bmscore.enums.SeatStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "seats")
public class Seat extends GlobalFields{
    private String seatNumber;
    private boolean isActive;
    @ManyToOne
    private Row row;
}
