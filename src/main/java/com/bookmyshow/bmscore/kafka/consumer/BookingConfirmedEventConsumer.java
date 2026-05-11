package com.bookmyshow.bmscore.kafka.consumer;

import com.bookmyshow.bmscore.customExceptions.BookingNotFoundException;
import com.bookmyshow.bmscore.kafka.events.BookingConfirmedEvent;
import com.bookmyshow.bmscore.models.Booking;
import com.bookmyshow.bmscore.models.Show;
import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.repository.BookingRepository;
import com.bookmyshow.bmscore.service.BookingService;
import com.bookmyshow.bmscore.service.EmailService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Slf4j
@Component
public class BookingConfirmedEventConsumer {
    @Autowired
    private EmailService emailService;
    @Autowired
    private BookingRepository bookingRepository;

    @Transactional
    @KafkaListener(topics = "ticket.email.events")
    @Retryable(maxAttempts = 3)
    public void processEmailSending(BookingConfirmedEvent event) {
        Booking booking = bookingRepository.findById(event.getBookingId())
                        .orElseThrow(()-> new BookingNotFoundException("Booking not found!"));
        User user = booking.getUser();
        Show show = booking.getShow();
        emailService.sendTicketOnEmail(
                user.getEmail(),
                "Booking confirmed.",
                user.getName(),
                event.getBookingId().toString(),
                show.getMovie().getName(),
                show.getScreen().getTheater().getName(),
                show.getStartTime().toString().replace('T',' '),
                show.getEndTime().toString().replace('T' , ' '),
                booking.getBookedSeats().stream()
                .map(ss -> ss.getSeat().getSeatNumber())
                .collect(Collectors.joining(", ")));
        log.info("Email has been finally sent by Kafka to :"+user.getEmail());
    }
}
