package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.customExceptions.InvalidUserCredentialsException;
import com.bookmyshow.bmscore.requestDTO.UserLoginDTO;
import com.bookmyshow.bmscore.responseDTO.LoginResponseDTO;
import com.bookmyshow.bmscore.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/app")
@Slf4j
public class LoginController {
    @Autowired
    private LoginService loginService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody UserLoginDTO userLogin) {
        try{
            LoginResponseDTO response = loginService.login(userLogin);

            log.info("Login successful."+response);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (InvalidUserCredentialsException e){
            return new ResponseEntity<>(new LoginResponseDTO(e.getMessage(),null), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(new LoginResponseDTO(e.getMessage(),null), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
