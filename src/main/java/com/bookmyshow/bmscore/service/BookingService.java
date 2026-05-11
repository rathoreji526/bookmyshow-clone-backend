package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.*;
import com.bookmyshow.bmscore.enums.BookingStatus;
import com.bookmyshow.bmscore.enums.SeatStatus;
import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.kafka.producer.BookingConfirmedEventProducer;
import com.bookmyshow.bmscore.models.*;
import com.bookmyshow.bmscore.repository.*;
import com.bookmyshow.bmscore.requestDTO.InitiatePaymentRequestDTO;
import com.bookmyshow.bmscore.requestDTO.SeatLockingRequestDTO;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class BookingService {
    @Autowired
    private BookingRepository bookingRepo;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private UserService userService;
    @Autowired
    private ShowService  showService;
    @Autowired
    private TheaterService theaterService;
    @Autowired
    private ShowSeatService showSeatService;
    @Autowired
    private BookingConfirmedEventProducer bookingConfirmedEventProducer;

    @Transactional
    public UUID initiatePayment(InitiatePaymentRequestDTO dto) {
        //lock seat first
        showSeatService.lockSeat(dto.getShowSeatIds() , dto.getUserId() , dto.getShowId());

        User user = userService.findById(dto.getUserId());
        Set<UUID> showSeatIDs = new HashSet<>(dto.getShowSeatIds());
        List<ShowSeat> showSeatList = showSeatService.findByIdIn(dto.getShowSeatIds());

        Double bookingValue = 0.0;
        if (showSeatIDs.size() != showSeatList.size()) {
            throw new SeatNotExistsException("Some seats do not exist");
        }

        for (ShowSeat ss : showSeatList) {
            if(ss.getLockedBy()==null || !ss.getSeatStatus().equals(SeatStatus.LOCKED)){
                throw new SeatNotLockedException("Seat is not locked! Please go back to the seat selection page and retry.");
            }
            if (!ss.getLockedBy().equals(dto.getUserId())) {
                throw new SeatAlreadyBookedOrLockedException("Seat booked or locked by another user");
            }
            if (ss.getLockExpiry().isBefore(LocalDateTime.now())) {
                throw new BookingTimeoutException("Booking timed out");
            }
            bookingValue += ss.getPrice();
        }
        Show show = showService.findById(showSeatList.get(0).getShow().getId());
        Transaction transaction = transactionService.createAndSave(user.getId() , bookingValue);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookedSeats(showSeatList);
        booking.setTotalPrice(bookingValue);
        booking.setBookingExpiry(LocalDateTime.now().plusMinutes(5));
        booking.setShow(show);

        booking.setTransaction(transaction);
        bookingRepo.save(booking);

        return transaction.getId();
    }

    @Transactional
    public void handleConfirmedTransaction(UUID txnId) {
        Booking booking = findByTransactionId(txnId);
        log.info("booking fetched from DB, bookingId: "+booking.getId());

        if(!booking.getBookingStatus().equals(BookingStatus.PENDING)){
            return;
        }
        List<ShowSeat> ssList =  booking.getBookedSeats();
        for(ShowSeat ss : ssList) {
            if(!ss.getSeatStatus().equals(SeatStatus.LOCKED)) {
                throw new SeatNotLockedException("Seat is not in locked state.");
            }
            ss.setSeatStatus(SeatStatus.BOOKED);
            ss.setLockedBy(null);
            ss.setLockExpiry(null);
        }
        showSeatService.saveAll(ssList);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingExpiry(null);
        bookingRepo.save(booking);

        bookingConfirmedEventProducer.sendBookingTicketOnEmail(booking.getId());

        log.info("\nBooking confirmed and tickets sent successfully.\n");
    }
    /*
    name of user, bookingId , movieName , theaterName , showStartTime , showEndTime , bookedSeats ,
    */

    @Transactional
    public void handleCancelledTransaction(UUID transactionId) {
        Booking booking = findByTransactionId(transactionId);
        if(!booking.getBookingStatus().equals(BookingStatus.PENDING)){
            return;
        }
        List<ShowSeat> ssList = booking.getBookedSeats();
        for(ShowSeat ss : ssList) {
            if(!ss.getSeatStatus().equals(SeatStatus.LOCKED)) {
                throw new SeatNotLockedException("Seat is not in locked state.");
            }
            ss.setSeatStatus(SeatStatus.AVAILABLE);
            ss.setLockExpiry(null);
            ss.setLockedBy(null);
        }
        showSeatService.saveAll(ssList);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setBookingExpiry(null);
        bookingRepo.save(booking);
    }
    public List<Booking> findAllBookings(UUID userId){
        if(!userService.existsById(userId)){
            throw new InvalidUserException("User not found");
        }
        return bookingRepo.findByUserId(userId);
    }
    public Booking findByTransactionId(UUID transactionId){
        return bookingRepo.findByTransactionId(transactionId)
                .orElseThrow(()-> new BookingNotFoundException("Booking not found for transaction id: " + transactionId));
    }

        //if transaction is confirmed change the booking status to confirm and send all the seat ids on email

        /*
       checks before booking ticket
       purpose:
        reject if:-
        1. status of showSeat is not locked.
        2. showSeat is not locked by the user.
        3. locked time+5min is less than current time.

        after all checks
        create transaction with status pending
        it is dummy transaction so we will do only one thing we will make transaction status to completed/failed
        in every 10 seconds we will check the status of the transaction if it is completed make all the showSeats booked.

        */

}
