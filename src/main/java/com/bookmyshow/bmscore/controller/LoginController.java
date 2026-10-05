package com.bookmyshow.bmscore.controller;

import com.bookmyshow.bmscore.requestDTO.UserLoginDTO;
import com.bookmyshow.bmscore.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app")
public class LoginController {
    @Autowired
    private LoginService loginService;

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserLoginDTO userLogin) {
        try{
            String response = loginService.login(userLogin);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
