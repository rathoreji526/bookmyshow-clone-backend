package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, UUID> {
}
