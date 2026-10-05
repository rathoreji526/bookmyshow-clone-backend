package com.bookmyshow.bmscore.security;

import com.bookmyshow.bmscore.models.User;
import com.bookmyshow.bmscore.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Slf4j
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secretPassword;
    @Value("${jwt.expiration}")
    private long expirationTime;

    @Autowired
    private UserService userService;

    public String generateToken(User user) {
        Map<String , Object> claims = new HashMap<>();
        claims.put("username",user.getUsername());
        claims.put("role",user.getRole().name());

        log.info("generating token for user:{}",user.getUsername());
        log.info("secret: "+secretPassword +"\nexpiration: "+expirationTime);
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis()+expirationTime))
                .setId(UUID.randomUUID().toString())
                .signWith(SignatureAlgorithm.HS256, secretPassword)
                .compact();
        log.info("generated token for user:{}",user.getUsername()+"\nToken:"+token);
        return token;
    }
    public Claims decryptToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretPassword)
                .build()
                .parseClaimsJws(token)
                .getBody();
        log.info("token: "+ token+" decrypted");
        return claims;
    }
    public boolean isTokenValid(String token) {
        Claims claims = decryptToken(token);
        String username = claims.get("username" ,  String.class);
        String role =  claims.get("role" , String.class);

        User user = userService.findByUsername(username);

        if(user==null) {
            log.info("token invalid because user is null.");
            return false;
        }
        if(!user.getRole().name().equals(role)) {
            log.info("token invalid because role is not match.");
            return false;
        }
        if(isTokenExpired(token)){
            log.info("token invalid because token is expired.");
            return false;
        }
        return true;
    }
    public boolean isTokenExpired(String token) {
        Claims claims = decryptToken(token);
        Date expiration = claims.getExpiration();
        return expiration.before(new Date());
    }
}
