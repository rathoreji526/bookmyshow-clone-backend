package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Booking;

import com.bookmyshow.bmscore.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    public Optional<Booking> findByTransactionId(UUID transactionId);
    public List<Booking>  findByUserId(UUID userId);

    @Query("""
            select b.user
            from Booking b
            where b.bookingDate = :date
            group by b.user
            having count(*) > 5
            """)
    public List<User> findUsersWithMoreThanFiveBookings(LocalDateTime date);
    public List<Booking> findByIdIn(List<UUID> ids);
    @Query("""
            select count(b)
            from Booking b
            where b.show.movie.id = :movieId and b.show.screen.theater.id in :theaterIds
            """)
    public int getBookingsInACity(@Param("theaterIds") List<UUID> theaterIds,
                                  @Param("movieId") UUID movieId);

}
