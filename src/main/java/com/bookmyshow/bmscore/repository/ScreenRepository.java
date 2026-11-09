package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Screen;
import com.bookmyshow.bmscore.responseDTO.ScreenResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ScreenRepository extends JpaRepository<Screen, UUID> {
    public Optional<Screen> findByNameAndTheaterId(String name , UUID theaterId);
    public List<ScreenResponse> findByTheaterId(UUID theaterId);
}
