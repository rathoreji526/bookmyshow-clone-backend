package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    public Optional<Booking> findByTransactionId(UUID transactionId);
}
