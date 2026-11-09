package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Movie;
import com.bookmyshow.bmscore.responseDTO.MovieIdName;
import com.bookmyshow.bmscore.responseDTO.MovieResponse;
import com.bookmyshow.bmscore.responseDTO.UpcomingMovieResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    public boolean existsByName(String name);

    @Query("select m.id as id, m.name as name from Movie m")
    public List<MovieIdName> findAllIdAndName();

    public List<MovieResponse> findByReleaseDateBetween(LocalDateTime start , LocalDateTime end);
    public boolean existsById(UUID id);
    public List<UpcomingMovieResponse> findByReleaseDateAfter(LocalDateTime date);
}
