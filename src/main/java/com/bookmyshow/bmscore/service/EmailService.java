package com.bookmyshow.bmscore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public void sendEmail(String to, String subject, String text) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }

//    @EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
    public void send(){
        this.sendEmail("mr.rathoreji52@gmail.com" , "Application started" , "Hello mr.rathoreji52@gmail.com\nYour application is started now.");
        log.info("Email successfully sent.");
    }
    public void sendTicketOnEmail(String email ,
                           String subject,
                           String name,
                           String bookingId,
                           String movieName,
                           String theaterName,
                           String showStartTime,
                           String showEndTime,
                           String bookedSeats){
        this.sendEmail(email , subject, "Hi "+name+",\n"
                +"Your ticket has been successfully booked. \uD83C\uDF89\n"
                +"Here are your booking details:\n"
                +"Booking id: "+bookingId+"\n"
                +"Movie name: "+movieName+"\n"
                +"Theater: "+theaterName+"\n"
                +"Show timing: "+showStartTime +" - "+showEndTime+"\n"
                +"Seats: "+bookedSeats+"\n"
                +"Please keep this Ticket ID safe. You will need it for verification at the entry.\n"
                +"We’ll soon send you a QR code for faster check-in.\n"
                +"Enjoy your show! \uD83C\uDF7F\n" +
                "\n" +
                "Regards,  \n" +
                "BookMyShow Team");
        log.info("Email successfully sent to: "+email);
    }
}