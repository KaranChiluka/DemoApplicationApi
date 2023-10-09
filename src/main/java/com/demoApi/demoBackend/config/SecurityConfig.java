package com.demoApi.demoBackend.config;

import com.demoApi.demoBackend.util.JwtAuthenticationEntryPoint;
import com.demoApi.demoBackend.util.JwtAuthenticationFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    @Autowired
    JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    BCryptPasswordEncoder encoder;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.csrf()
//                .and()
//                .cors()
                .disable()
                .authorizeHttpRequests()
                .requestMatchers("/actuator/**","/security/login","/security/signup")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling(ex->ex.authenticationEntryPoint(jwtAuthenticationEntryPoint))
                .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
//                .addFilterAfter(jwtAuthorizationFilter,UsernamePasswordAuthenticationFilter.class);

//        http.authenticationProvider(daoAuthenticationProvider());
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(encoder);

        return  daoAuthenticationProvider;
    }
}
