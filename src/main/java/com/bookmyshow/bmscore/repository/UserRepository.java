package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByUsername(String username);
    public boolean existsBySysId(String sysId);
}
