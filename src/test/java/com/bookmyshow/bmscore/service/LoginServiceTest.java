package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.controller.LoginController;
import com.bookmyshow.bmscore.requestDTO.UserLoginDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest//it starts the complete application, creates bean and we can use it here if we don't call it we will not be able to do database operations
public class LoginServiceTest{
    @Autowired
    private LoginController loginController;
    @Test
    public void loginTest(){
        UserLoginDTO userLoginDTO = new UserLoginDTO();
        userLoginDTO.setUsername("rathoreji526");
        userLoginDTO.setPassword("Kamal@123");
        String message =  loginController.login(userLoginDTO).toString();
        System.out.println(message);
    }
}
