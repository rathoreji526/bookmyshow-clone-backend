package com.bookmyshow.bmscore.customUserDetails;

import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServices implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);

        if(user == null) throw new UsernameNotFoundException("User with username: " + username + " not found");
        return new CustomUserDetails(user);
    }
}
