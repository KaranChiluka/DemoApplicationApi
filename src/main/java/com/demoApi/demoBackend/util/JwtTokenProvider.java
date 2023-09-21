package com.demoApi.demoBackend.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String tokenGeneration(String username){
        Date current = new Date();
        Date tokenExpiration = new Date(current.getTime() + jwtExpiration);
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(current)
                .setExpiration(tokenExpiration)
                .signWith(SignatureAlgorithm.HS512,jwtSecret)
                .compact();
    }
}
