package com.sleepkqq.taskmanagement.service;

import com.sleepkqq.taskmanagement.model.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.Password;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtService {

    @Value("${token.secret}")
    private String secret;
    @Value("${token.expiration-millis}")
    private int lifetime;

    public String generateToken(UserDetails userDetails) {
        var user = (User) userDetails;

        var alg = Jwts.KEY.PBES2_HS512_A256KW;
        var enc = Jwts.ENC.A256GCM;

        return Jwts.builder()
                .subject(userDetails.getUsername())
                .claim("email", user.getEmail())
                .claim("role", user.getRole().name())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + lifetime))
                .encryptWith(getSecretKey(), alg, enc)
                .compact();
    }

    public String extractUsername(String token) {
        return Jwts.parser().decryptWith(getSecretKey()).build().parseEncryptedClaims(token).getPayload().getSubject();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        var username = extractUsername(token);
        return (username.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return (long) extractClaim(token, "exp") > System.currentTimeMillis();
    }

    public Object extractClaim(String token, String claim) {
        return Jwts.parser().decryptWith(getSecretKey()).build().parseEncryptedClaims(token).getPayload().get(claim);
    }

    private Password getSecretKey() {
        return Keys.password(secret.toCharArray());
    }

}
