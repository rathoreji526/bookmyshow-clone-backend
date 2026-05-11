package com.bookmyshow.bmscore.kafka.producer;

import com.bookmyshow.bmscore.kafka.events.BookingConfirmedEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingConfirmedEventProducer {
    @Autowired
    private KafkaTemplate<String , BookingConfirmedEvent> kafkaTemplate;

    public void sendBookingTicketOnEmail(UUID bookingId) {
        BookingConfirmedEvent event = new BookingConfirmedEvent();
        event.setBookingId(bookingId);
        kafkaTemplate.send("ticket.email.events", bookingId.toString(), event);
    }
}
