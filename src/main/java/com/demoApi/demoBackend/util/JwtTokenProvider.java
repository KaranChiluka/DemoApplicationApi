package com.demoApi.demoBackend.util;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.exception.InvalidCredentialsException;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    public String tokenGeneration(String username){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,username);
    }

    public String createToken(Map<String,Object> claims,String username){
        Date current = new Date();
        Date tokenExpiration = new Date(current.getTime() + jwtExpiration);
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(current)
                .setExpiration(tokenExpiration)
                .signWith(getSignKey(),SignatureAlgorithm.HS512)
                .compact();
    }
    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSecret);
        return Keys.hmacShaKeyFor(keyBytes);
    }
    public boolean validateToken(String token,UserDetails userDetails){
        String username = getUsernameFromJwt(token);
        return (username.equals(userDetails.getUsername())) && !tokenExpiration(token);
    }
    public boolean tokenExpiration(String token){
        return getTokenExpiration(token).before(new Date());
    }
    public Date getTokenExpiration(String token){
        return claims(token,Claims::getExpiration);
    }
    public String getUsernameFromJwt(String token){
        return claims(token,Claims::getSubject);
    }
    public <T> T claims(String token, Function<Claims,T> claimsResolver){
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    public Claims extractAllClaims(String token){
        return Jwts
                .parserBuilder()
                .setSigningKey(jwtSecret)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
