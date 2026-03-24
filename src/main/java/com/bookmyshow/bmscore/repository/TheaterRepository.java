package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TheaterRepository extends JpaRepository<Theater, UUID> {
    public Optional<Theater> findByOwnerId(UUID ownerId);
    public boolean existsBySysId(String sysId);
}
