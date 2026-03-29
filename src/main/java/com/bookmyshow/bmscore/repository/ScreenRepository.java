package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Screen;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ScreenRepository extends JpaRepository<Screen, UUID> {
    public Optional<Screen> findBySysId(String sysId);
    public Optional<Screen> findByNameAndTheaterId(String name , UUID theaterId);
}
