package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface TheaterRepository extends JpaRepository<Theater, UUID> {
    public boolean existsBySysId(String sysId);
    public Optional<Theater> findByNameAndLocation_Id(String name, UUID locationId);
}
