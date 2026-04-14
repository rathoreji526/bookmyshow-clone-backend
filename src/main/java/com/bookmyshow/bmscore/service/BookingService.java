package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.*;
import com.bookmyshow.bmscore.enums.BookingStatus;
import com.bookmyshow.bmscore.enums.SeatStatus;
import com.bookmyshow.bmscore.enums.TransactionStatus;
import com.bookmyshow.bmscore.models.*;
import com.bookmyshow.bmscore.repository.*;
import com.bookmyshow.bmscore.requestDTO.InitiatePaymentRequestDTO;
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
    private ShowSeatRepository showSeatRepo;
    @Autowired
    private TransactionRepository transactionRepo;
    @Autowired
    private UserRepository userRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private ShowRepository showRepo;
    @Autowired
    private TheaterRepository theaterRepo;

    @Transactional
    public UUID initiatePayment(InitiatePaymentRequestDTO dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new InvalidUserException("User not found"));
        Set<UUID> showSeatIDs = new HashSet<>(dto.getShowSeatIds());
        List<ShowSeat> showSeatList = showSeatRepo.findByIdIn(dto.getShowSeatIds());

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
        Show show = showRepo.findById(showSeatList.get(0).getShow().getId())
                .orElseThrow(() -> new InvalidShowIdException("Show not found"));


        Transaction transaction = new Transaction();
        transaction.setUserId(dto.getUserId());
        transaction.setAmount(bookingValue);
        transaction.setExpiry(LocalDateTime.now().plusMinutes(5));
        transaction.setStatus(TransactionStatus.PENDING);

        Booking booking = new Booking();
        booking.setUser(user);
        booking.setBookedSeats(showSeatList);
        booking.setTotalPrice(bookingValue);
        booking.setBookingExpiry(LocalDateTime.now().plusMinutes(10));
        booking.setShow(show);

        transactionRepo.save(transaction);
        booking.setTransaction(transaction);
        bookingRepo.save(booking);

        return transaction.getId();
    }

    @Transactional
    public void handleConfirmedTransaction(UUID txnId) {
        Booking booking = bookingRepo.findByTransactionId(txnId)
                .orElseThrow(()-> new BookingNotFoundException("Booking not found for transaction id: " + txnId));

        User user = booking.getUser();
        log.info("Show id: " + booking.getShow().getId());
        Show show = booking.getShow();
        log.info("Theater id: "+show.getScreen().getTheater().getId());
        Theater theater = theaterRepo.findById(show.getScreen().getTheater().getId())
                .orElseThrow(()-> new InvalidTheaterIdException("Unable to find theater"));

        if(!booking.getBookingStatus().equals(BookingStatus.PENDING)){
            return;
        }
        List<ShowSeat> ssList =  booking.getBookedSeats();
        StringBuilder bookedTickets = new StringBuilder();
        for(ShowSeat ss : ssList) {
            if(!ss.getSeatStatus().equals(SeatStatus.LOCKED)) {
                throw new SeatNotLockedException("Seat is not in locked state.");
            }
            ss.setSeatStatus(SeatStatus.BOOKED);
            ss.setLockedBy(null);
            ss.setLockExpiry(null);
            bookedTickets.append(ss.getSeat().getSeatNumber()).append(" ");
        }
        showSeatRepo.saveAll(ssList);
        booking.setBookingStatus(BookingStatus.CONFIRMED);
        booking.setBookingExpiry(null);
        bookingRepo.save(booking);
        emailService.sendEmail(user.getEmail() , "Booking confirmed.", "Hi "+user.getName()+",\n"
                +"Your ticket has been successfully booked. \uD83C\uDF89\n"
                +"Here are your booking details:\n"
                +"Booking id: "+booking.getId()+"\n"
                +"Movie name: "+show.getMovie().getName()+"\n"
                +"Theater: "+theater.getName()+"\n"
                +"Show timing: "+show.getStartTime() +"-"+show.getEndTime()+"\n"
                +"Seats: "+bookedTickets.toString()+"\n"
                +"Please keep this Ticket ID safe. You will need it for verification at the entry.\n"
                +"We’ll soon send you a QR code for faster check-in.\n"
                +"Enjoy your show! \uD83C\uDF7F\n" +
                "\n" +
                "Regards,  \n" +
                "BookMyShow Team");
        log.info("Booking confirmed and tickets sent successfully.");
    }

    @Transactional
    public void handleCancelledTransaction(UUID transactionId) {
        Booking booking = bookingRepo.findByTransactionId(transactionId)
                .orElseThrow(()-> new BookingNotFoundException("Booking not found for transaction id: " + transactionId));
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
        showSeatRepo.saveAll(ssList);
        booking.setBookingStatus(BookingStatus.CANCELLED);
        booking.setBookingExpiry(null);
        bookingRepo.save(booking);
    }
    public List<Booking> findAllBookings(UUID userId){
        if(!userRepo.existsById(userId)){
            throw new InvalidUserException("User not found");
        }
        return bookingRepo.findByUserId(userId);
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
