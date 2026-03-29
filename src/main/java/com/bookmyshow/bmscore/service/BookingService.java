package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.*;
import com.bookmyshow.bmscore.enums.BookingStatus;
import com.bookmyshow.bmscore.enums.SeatStatus;
import com.bookmyshow.bmscore.models.Booking;
import com.bookmyshow.bmscore.models.ShowSeat;
import com.bookmyshow.bmscore.models.Transaction;
import com.bookmyshow.bmscore.repository.BookingRepository;
import com.bookmyshow.bmscore.repository.ShowSeatRepository;
import com.bookmyshow.bmscore.repository.TransactionRepository;
import com.bookmyshow.bmscore.requestDTO.InitiatePaymentRequestDTO;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class BookingService {
    @Autowired
    BookingRepository bookingRepo;
    @Autowired
    ShowSeatRepository showSeatRepo;
    @Autowired
    TransactionRepository transactionRepo;

    @Transactional
    public UUID initiatePayment(InitiatePaymentRequestDTO dto) {
        Set<UUID> showSeatIDs = new HashSet<>(dto.getShowSeatIds());
        List<ShowSeat> showSeatList = showSeatRepo.findByIdIn(dto.getShowSeatIds());

        Double bookingValue = 0.0;
        if (showSeatIDs.size() != showSeatList.size()) {
            throw new SeatNotExistsException("Some seats do not exist");
        }

        for (ShowSeat ss : showSeatList) {
            if (!ss.getLockedBy().equals(dto.getUserId())) {
                throw new SeatAlreadyBookedOrLockedException("Seat booked or locked by another user");
            }
            if (ss.getLockExpiry().isBefore(LocalDateTime.now())) {
                throw new BookingTimeoutException("Booking timed out");
            }
            if (!ss.getSeatStatus().equals(SeatStatus.LOCKED)) {
                throw new SeatNotLockedException("Seat is not reserved.");
            }
            bookingValue += ss.getPrice();
        }
        Transaction transaction = new Transaction();
        transaction.setUserId(dto.getUserId());
        transaction.setAmount(bookingValue);
        transaction.setExpiry(LocalDateTime.now().plusMinutes(5));

        Booking booking = new Booking();
        booking.setUserId(dto.getUserId());
        booking.setBookedSeats(dto.getShowSeatIds());
        booking.setTotalPrice(bookingValue);
        booking.setBookingExpiry(LocalDateTime.now().plusMinutes(10));

        transactionRepo.save(transaction);
        booking.setTransactionId(transaction.getId());
        bookingRepo.save(booking);

        return transaction.getId();
    }

    @Transactional
    public void handleConfirmedTransaction(UUID txnId) {
        Booking booking = bookingRepo.findByTransactionId(txnId)
                .orElseThrow(()-> new BookingNotFoundException("Booking not found for transaction id: " + txnId));
        if(!booking.getBookingStatus().equals(BookingStatus.PENDING)){
            return;
        }
        List<ShowSeat> ssList =  showSeatRepo.findByIdIn(booking.getBookedSeats());
        for(ShowSeat ss : ssList) {
            if(!ss.getSeatStatus().equals(SeatStatus.LOCKED)) {
                throw new SeatNotLockedException("Seat is not in locked state.");
            }
            ss.setSeatStatus(SeatStatus.BOOKED);
            ss.setLockedBy(null);
            ss.setLockExpiry(null);
        }
        showSeatRepo.saveAll(ssList);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingExpiry(null);
        bookingRepo.save(booking);
    }

    @Transactional
    public void handleCancelledTransaction(UUID transactionId) {
        Booking booking = bookingRepo.findByTransactionId(transactionId)
                .orElseThrow(()-> new BookingNotFoundException("Booking not found for transaction id: " + transactionId));
        if(!booking.getBookingStatus().equals(BookingStatus.PENDING)){
            return;
        }
        List<ShowSeat> ssList = showSeatRepo.findByIdIn(booking.getBookedSeats());
        for(ShowSeat ss : ssList) {
            if(!ss.getSeatStatus().equals(SeatStatus.LOCKED)) {
                throw new SeatNotLockedException("Seat is not in locked state.");
            }
            ss.setSeatStatus(SeatStatus.AVAILABLE);
            ss.setLockExpiry(null);
            ss.setLockedBy(null);
        }
        showSeatRepo.saveAll(ssList);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setBookingExpiry(null);
        bookingRepo.save(booking);
    }

        //if transaction is confirmed change the booking status to confirm and send all the seat ids on email



        /*
        checks before booking ticket
        purpose:
        1. if status of showSeat is not locked - reject.
        2. if showSeat is not locked by the user - reject.
        3. if locked time+5min is less than current time - reject

        after all checks
        create transaction with status pending
        it is dummy transaction so we will do only one thing we will make transaction status to completed/failed
        in every 10 seconds we will check the status of the transaction if it is completed make all the showSeats booked.

        */

}
