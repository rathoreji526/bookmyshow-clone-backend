package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Show;
import com.bookmyshow.bmscore.requestDTO.FindShowDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface ShowRepository extends JpaRepository<Show, UUID> {
    public boolean existsBySysId(String sysId);
    public List<Show> findByMovieIdAndStartTimeAfter(UUID movieId , LocalDateTime time);
    @Query("""
    select s from Show s
    where s.screen.id = :screenId
    and(
    s.startTime < :endTime
    and s.endTime > :startTime
    )
""")
    public List<Show> findOverlappingShows(UUID screenId , LocalDateTime startTime , LocalDateTime endTime);
}