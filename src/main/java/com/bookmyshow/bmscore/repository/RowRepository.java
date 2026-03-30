package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RowRepository extends JpaRepository<Row, UUID> {
}
