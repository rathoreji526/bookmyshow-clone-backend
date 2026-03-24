package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.customExceptions.ConfirmPasswordMismatchException;
import com.bookmyshow.bmscore.customExceptions.UserAlreadyExistsException;
import com.bookmyshow.bmscore.requestDTO.SaveUserRequestDTO;
import com.bookmyshow.bmscore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/apis/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody SaveUserRequestDTO dto){
        try{
            userService.saveUser(dto);
            return new ResponseEntity<>("User saved successfully." , HttpStatus.CREATED);
        }catch(ConfirmPasswordMismatchException |
               UserAlreadyExistsException e     ){
            return new ResponseEntity<>(e.getMessage() , HttpStatus.BAD_REQUEST);
        }
    }
}
