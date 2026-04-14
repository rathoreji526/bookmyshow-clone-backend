package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.ShowSeat;
import jakarta.transaction.Transactional;
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
    AND ss.show.id = :showId
""")
    public int lockSeatsBulk(List<UUID> showSeatIDs, UUID userId,UUID showId, LocalDateTime expiry, LocalDateTime now);
    @Transactional
    @Modifying
    @Query("""
    UPDATE ShowSeat ss
    SET ss.seatStatus = 'AVAILABLE',
    ss.lockedBy = NULL,
    ss.lockExpiry = NULL
    WHERE (ss.seatStatus = 'LOCKED' AND ss.lockExpiry < :now)
""")
    public int releaseLockedSeat(LocalDateTime now);
    public List<ShowSeat> findByIdIn(List<UUID> showSeatIds);
    public List<ShowSeat> findByShowId(UUID showId);
}
