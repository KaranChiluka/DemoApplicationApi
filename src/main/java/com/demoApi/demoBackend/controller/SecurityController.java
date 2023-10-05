package com.demoApi.demoBackend.controller;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.dto.request.LoginDto;
import com.demoApi.demoBackend.dto.request.TokenDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.service.Impl.UserService;
import com.demoApi.demoBackend.util.JwtTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    private final Logger logger = LoggerFactory.getLogger(SecurityController.class);

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto){
        this.doAuthenticate(loginDto.getUsername(), loginDto.getPassword());
        UserDetails userDetails = userDetailsService.loadUserByUsername(loginDto.getUsername());
        String token = this.jwtTokenProvider.tokenGeneration(userDetails);

        TokenDto response = TokenDto.builder()
                .token(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }
    @PostMapping("/signup")
    public UserDetailsBO CreateUser(@RequestBody SignupDto signupDto){
        return userService.createUser(signupDto);
    }
    @GetMapping("/UserDetails")
    public List<UserDetailsBO> getAllUsers(@RequestHeader("Authorization") String token){
        return userService.getUsers();
    }
}
