package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SeatRepository extends JpaRepository<Seat, UUID> {
}
