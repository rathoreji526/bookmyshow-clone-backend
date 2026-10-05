package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Theater;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TheaterRepository extends JpaRepository<Theater, UUID> {
    public boolean existsBySysId(String sysId);
    public Optional<Theater> findByNameAndLocation_Id(String name, UUID locationId);
    @Query("""
            select t.id from Theater t
            where t.location.city = :city
            """)
    public List<UUID> findByCity(String city);
}
