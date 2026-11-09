package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Movie;
import com.bookmyshow.bmscore.models.Show;
import com.bookmyshow.bmscore.responseDTO.MovieResponse;
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
    where (s.showStatus = 'UPCOMING' and :now >= s.startTime)
           or (s.showStatus = 'ONGOING' and :now > s.endTime)
""")
    public int updateShowStatus(@Param("now") LocalDateTime now);
    public boolean existsById(UUID id);
    public int findByMovieId(UUID movieId);
    @Query("""
            select s
            from Show s
            where s.screen.theater.location.city = :city
            and
            s.startTime > :now
            """
    )
    public List<Show> findByCity(String city, LocalDateTime now);
    @Query("""
            select distinct m.id as id,
               m.name as name,
               m.description as description,
               m.posterUrl as posterUrl,
               m.rating as rating
            from Show s
            join s.movie m
            where s.startTime > :now
            """)
    public List<MovieResponse> getUpcomingShowMovies(LocalDateTime now);
}