package com.bookmyshow.bmscore.service;

import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.requestDTO.UserLoginDTO;
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

    public String login(UserLoginDTO userLogin) {
        String username = userLogin.getUsername().trim();
        String password = userLogin.getPassword().trim();

        if(username.isEmpty() || password.isEmpty()) {
            return "Please enter valid username and password";
        }
        User user = userService.findByUsername(username);
        if(user == null) {
            return "User with username " + username + " not found";
        }
        if(!commonUtilities.matchPassword(user.getPassword() , password)) {
            return "Wrong password";
        }
        return jwtUtil.generateToken(user);
    }
}
