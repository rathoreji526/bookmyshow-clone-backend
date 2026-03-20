package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface MovieRepository extends JpaRepository<Movie, UUID> {
    public Optional<Movie> findBySysId(String sysId);
}
