package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LocationRepository extends JpaRepository<Location , UUID> {
    public Optional<Location> findByZipcode(String zipcode);
}
