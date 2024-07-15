package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.dto.responses.AuthenticationResponse;
import com.sleepkqq.taskmanagement.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

import static com.sleepkqq.taskmanagement.constants.SecurityProperties.BEARER_TYPE;

@Slf4j
@Service
public class JwtService {

    @Value("${token.secret}")
    private String secret;
    @Value("${token.expiration-millis}")
    private int lifetime;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public AuthenticationResponse generateToken(User user) {
        var token = Jwts.builder()
                .subject(user.getUsername())
                .claim("email", user.getEmail())
                .claim("roles", user.getRoles().stream().map(Enum::name).toList())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + lifetime))
                .signWith(secretKey)
                .compact();
        return new AuthenticationResponse(token, lifetime / 1000, BEARER_TYPE);
    }

    public String extractUsername(String token) {
        return Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token).getPayload().getSubject();
    }

}
