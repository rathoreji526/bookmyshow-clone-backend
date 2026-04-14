package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Booking;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    public Optional<Booking> findByTransactionId(UUID transactionId);
    public List<Booking>  findByUserId(UUID userId);
}
