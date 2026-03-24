package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.ConfirmPasswordMismatchException;
import com.bookmyshow.bmscore.customExceptions.UserAlreadyExistsException;
import com.bookmyshow.bmscore.enums.Role;
import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.repository.UserRepository;
import com.bookmyshow.bmscore.requestDTO.SaveUserRequestDTO;
import com.bookmyshow.bmscore.utilities.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

        userRepo.save(user);
    }
}