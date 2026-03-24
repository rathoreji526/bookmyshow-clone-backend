package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Show;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ShowRepository extends JpaRepository<Show, UUID> {
    public boolean existsBySysId(String sysId);
}
