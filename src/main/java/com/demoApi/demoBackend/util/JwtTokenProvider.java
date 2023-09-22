package com.demoApi.demoBackend.util;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.exception.InvalidCredentialsException;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

@Component
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    @Autowired
    UserDetailsRepository userDetailsRepository;

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

    public UserDetailsBO validateToken(String token){
        String username = getUsernameFromJwt(token);
        UserDetailsBO userDetailsBO = userDetailsRepository.findByUsername(username)
                .orElseThrow();
        boolean isExist = (username.matches(userDetailsBO.getUsername()) && !tokenExpiration(token));
        if(isExist){
            return userDetailsBO;
        }
        return null;
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
        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody();
        return claimsResolver.apply(claims);
    }
}
