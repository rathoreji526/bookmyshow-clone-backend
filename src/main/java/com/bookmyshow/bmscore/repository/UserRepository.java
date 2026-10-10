package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Booking;
import com.bookmyshow.bmscore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    public Optional<User> findByUsername(String username);
    public boolean existsBySysId(String sysId);
    public boolean existsById(UUID id);
    public boolean existsByUsername(String username);
    @Query("""
            select u.bookings from User u where u.username = :username
            """)
    public List<Booking> getBookingsWithUsername(@Param("username") String username);
    public int countByEmail(String email);
}
