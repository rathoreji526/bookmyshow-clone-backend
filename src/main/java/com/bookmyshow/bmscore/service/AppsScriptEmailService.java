package com.bookmyshow.bmscore.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.Map;

@Slf4j
@Service
public class AppsScriptEmailService {
    private final RestClient restClient;
    private final String webhookUrl;
    private final String webhookToken;
    private final String senderName;

    public AppsScriptEmailService(
            @Value("${app.mail.webhook-url}") String webhookUrl,
            @Value("${app.mail.webhook-token}") String webhookToken,
            @Value("${app.mail.sender-name:MoviePlace}") String senderName) {
        this.webhookUrl = webhookUrl;
        this.webhookToken = webhookToken;
        this.senderName = senderName;
        this.restClient = RestClient.create();
    }
    @Async
    public void sendEmail(String to, String subject, String htmlBody) {
        log.info("Sending email to {}", to);
        try {
            Map<String, String> payload = Map.of(
                    "token", webhookToken,
                    "to", to,
                    "subject", subject,
                    "htmlBody", htmlBody,
                    "fromName", senderName
            );

            String response = restClient.post()
                    .uri(webhookUrl)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(payload)
                    .retrieve()
                    .body(String.class);

            log.info("Apps Script mail response for {}: {}", to, response);
        } catch (Exception ex) {
            log.error("Failed to send mail via Apps Script to {}: {}", to, ex.getMessage(), ex);
        }
    }

    @EventListener(org.springframework.boot.context.event.ApplicationReadyEvent.class)
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
//        log.info("came in sendTicketOnEmail");
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