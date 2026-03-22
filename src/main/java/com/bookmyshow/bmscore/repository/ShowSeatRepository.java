package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.enums.SeatStatus;
import com.bookmyshow.bmscore.models.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ShowSeatRepository extends JpaRepository<ShowSeat, UUID> {
    @Modifying
    @Query("""
    UPDATE ShowSeat ss
    SET ss.seatStatus = 'LOCKED',
    ss.lockedBy = :userId,
    ss.lockExpiry = :expiry
    WHERE ss.id IN :showSeatIDs
    AND(
    ss.seatStatus = 'AVAILABLE'
    OR
    (ss.seatStatus = 'LOCKED' AND ss.lockExpiry < :now)
    )
""")
    public int lockSeatsBulk(List<UUID> showSeatIDs, UUID userId, LocalDateTime expiry, LocalDateTime now);
    public List<ShowSeat> findByLockedByAndSeatStatus(UUID lockedBy , SeatStatus seatStatus);
}
