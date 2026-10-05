package com.bookmyshow.bmscore.security;

import com.bookmyshow.bmscore.customUserDetails.CustomUserDetailsServices;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private CustomUserDetailsServices customUserDetailsServices;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");

        if(header == null || header.isBlank() || !header.startsWith("Bearer ")){
            filterChain.doFilter(request, response);
            return;
        }

        String token = header.substring(7).trim();
        if(!jwtUtil.isTokenValid(token)){
            filterChain.doFilter(request, response);
            return;
        }

        Claims claims = jwtUtil.decryptToken(token);
        String username = claims.get("username" , String.class);
        String role =  claims.get("role" , String.class);
        List<SimpleGrantedAuthority> authorities =
                List.of(new SimpleGrantedAuthority(role));

        UserDetails userDetails = customUserDetailsServices.loadUserByUsername(username);

        UsernamePasswordAuthenticationToken authentication = new
                UsernamePasswordAuthenticationToken(
                        userDetails,
                null,
                userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }
}

