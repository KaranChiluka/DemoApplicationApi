package com.demoApi.demoBackend.config;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.util.CustomUserDetails;
import com.demoApi.demoBackend.util.CustomUserDetailsServices;
import com.demoApi.demoBackend.util.JwtTokenProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private final JwtTokenProvider jwtTokenProvider;
    @Autowired
    CustomUserDetails customUserDetails;

    public  JwtAuthorizationFilter(JwtTokenProvider jwtTokenProvider){
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = jwtTokenProvider.resolveToken(request);

        if(token != null && jwtTokenProvider.validateToken(token) != null){
            UserDetailsBO userDetailsBO = jwtTokenProvider.validateToken(token);

            UsernamePasswordAuthenticationToken authenticationToke = new UsernamePasswordAuthenticationToken(userDetailsBO,null,customUserDetails.getAuthorities());

            authenticationToke.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticationToke);
        }

        filterChain.doFilter(request,response);
    }
}
