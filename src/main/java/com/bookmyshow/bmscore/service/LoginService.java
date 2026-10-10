package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.customExceptions.InvalidUserCredentialsException;
import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.requestDTO.UserLoginDTO;
import com.bookmyshow.bmscore.responseDTO.LoginResponseDTO;
import com.bookmyshow.bmscore.security.JwtUtil;
import com.bookmyshow.bmscore.utilities.CommonUtilities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private UserService userService;
    @Autowired
    private CommonUtilities commonUtilities;

    public LoginResponseDTO login(UserLoginDTO userLogin) {
        String username = userLogin.getUsername().trim();
        String password = userLogin.getPassword().trim();

        if(username.isEmpty() || password.isEmpty()) {
            throw new InvalidUserCredentialsException("Invalid user credentials!");
        }

        User  user;
        try{
            user = userService.findByUsername(username);
        }catch (Exception e){
            throw new InvalidUserCredentialsException("Invalid user credentials!");
        }

        if(user==null || !commonUtilities.matchPassword(user.getPassword() , password)) {
            throw new InvalidUserCredentialsException("Invalid user credentials!");
        }
        String token = jwtUtil.generateToken(user);
        LoginResponseDTO response = new LoginResponseDTO();
        response.setMessage("Login successful.");
        response.setToken(token);

        return response;
    }
}
