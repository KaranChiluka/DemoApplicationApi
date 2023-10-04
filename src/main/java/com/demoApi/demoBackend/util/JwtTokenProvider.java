package com.demoApi.demoBackend.util;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.exception.InvalidCredentialsException;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.servlet.http.HttpServletRequest;
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

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public UserDetailsBO validateToken(String token){
        String username = getUsernameFromJwt(token);
        UserDetailsBO userDetailsBO = userDetailsRepository.findByUsername(username)
                .orElseThrow();
        boolean isValid = (username.equals(userDetailsBO.getUsername()) && !tokenExpiration(token));
        if(isValid){
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
