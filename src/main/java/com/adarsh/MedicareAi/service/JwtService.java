package com.adarsh.MedicareAi.service;

import com.adarsh.MedicareAi.entity.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    @Value("${jwt.secret}") // mapping of secretKey key present in Application.prop
    private String secret;

    @Value("${jwt.expiration}")
    private long expiration;

    private SecretKey getSecretKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secret);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String generateToken(User  user) {
        return Jwts.builder()
                .subject(user.getEmail())
                .claim("role", user.getRole().name())
                .claim("userId", user.getId())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis()+expiration))
                .signWith(getSecretKey())
                .compact();
    }

    // Created this method for extracting subject from jwt token
    public String extractEmail(String token) {

        return Jwts.parser()  // I  want to read/ process JWT
                .verifyWith(getSecretKey()) // check the same secret key is involved in both operations?
                .build()
                .parseSignedClaims(token) //This parses the JWT and verifies its signed claims.
                .getPayload() // Get JWT payload
                .getSubject(); // Get Subject
    }

    public Long extractUserId(String token) {
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .get("userId", Long.class);
    }
}