package com.bookmyshow.bmscore.repository;

import com.bookmyshow.bmscore.models.Booking;

import com.bookmyshow.bmscore.models.Movie;
import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.responseDTO.MovieResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookingRepository extends JpaRepository<Booking, UUID> {
    public Optional<Booking> findByTransactionId(UUID transactionId);
    public List<Booking> findByUserUsername(String username);

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
    @Query("""
        select m.id as id,
               m.name as name,
               m.description as description,
               m.posterUrl as posterUrl,
               m.rating as rating
        from Booking b
        join b.show s
        join s.movie m
        where b.createdAt > :thirtyDaysAgo
        group by m.id, m.name, m.description, m.posterUrl, m.rating
        order by count(b.id) desc
        """)
    List<MovieResponse> findTopTenShowsInLast30Days(
            @Param("thirtyDaysAgo") LocalDateTime thirtyDaysAgo,
            Pageable pageable
    );

}
/*
select b.show.movie
from Booking b
where b.createdAt > :thirtyDaysAgo
group by b.show.movie
order by count(b.id) desc


select s.movie
from Booking b
join b.show s
where b.createdAt > :thirtyDaysAgo
group by s.movie
order by count(b.id) desc
* */