package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Show;
import com.bookmyshow.bmscore.requestDTO.FindShowDTO;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    @Transactional
    @Modifying
    @Query("""
    update Show s
    set s.showStatus =
           case
             when :now between s.startTime and s.endTime then 'ONGOING'
             when :now > s.endTime then 'CLOSED'
             else 'UPCOMING'
           end
    where s.showStatus != 'ONGOING' and s.showStatus != 'CLOSED'
""")
    public int updateShowStatus(@Param("now") LocalDateTime now);
}