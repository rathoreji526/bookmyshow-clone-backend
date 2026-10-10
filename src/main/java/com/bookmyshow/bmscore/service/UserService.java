package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.*;
import com.bookmyshow.bmscore.enums.Role;
import com.bookmyshow.bmscore.models.Booking;
import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.repository.UserRepository;
import com.bookmyshow.bmscore.requestDTO.SaveUserRequestDTO;
import com.bookmyshow.bmscore.utilities.CommonUtilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
public class UserService {

    @Autowired
    UserRepository userRepo;
    @Autowired
    CommonUtilities utilities;

    public void saveUser(SaveUserRequestDTO dto){
        if(!dto.getPassword().equals(dto.getConfirmPassword())){
            throw new ConfirmPasswordMismatchException("Password mismatch!");
        }
        if(dto.getPassword().length() < 6){
            throw new PasswordLengthException("Password length should be at least 6.");
        }
        if(dto.getUsername().length() < 5 || dto.getUsername().length() > 15){
            throw new UsernameLengthException("Username length should between 5-15.");
        }
        if(!dto.getEmail().endsWith("@gmail.com")){
            throw new InvalidEmailException("Invalid email!");
        }
        if(userRepo.countByEmail(dto.getEmail()) > 5){
            throw new TooMuchAccountsWithSameEmailException("Too much account with this email! Can't create more.");
        }
        Optional<User> dbUser = userRepo.findByUsername(dto.getUsername());

        if(dbUser.isPresent()){
            throw new UserAlreadyExistsException("User with username: "+dto.getUsername()+" already exists.");
        }

        User user = new User();
        user.setName(dto.getFullName());
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(utilities.hashPassword(dto.getPassword()));
        user.setRole(Role.USER);
        user.setSysId(utilities.generateUserSysId());
        user.setBookings(new ArrayList<>());

        userRepo.save(user);
    }
    public List<Booking> getBookings(String username){
        log.info("fetching bookings of user: "+username+" from database......");
        List<Booking> bookings = userRepo.getBookingsWithUsername(username);
        log.info("fetched from database there are "+ bookings.size()+" bookings.");
        return bookings;
    }
    public User findById(UUID id){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new InvalidUserException("User with id: "+id+" not found."));
        return user;
    }
    public User findByUsername(String username) {
        User user = userRepo.findByUsername(username)
                .orElseThrow(() -> new InvalidUserException("User with username: "+username+" not found."));
        return user;
    }
    public void saveUser(User user){
        userRepo.save(user);
    }
    public boolean existsById(UUID id){
        return userRepo.existsById(id);
    }
}