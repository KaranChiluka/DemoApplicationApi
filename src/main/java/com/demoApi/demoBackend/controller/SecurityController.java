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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
@RequestMapping("/security")
//@CrossOrigin(origins = {"*"},methods = {RequestMethod.GET,RequestMethod.POST,RequestMethod.DELETE,RequestMethod.OPTIONS})
public class SecurityController {
    @Autowired
    UserDetailsService userDetailsService;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserService userService;

    @PostMapping("/login")
    public String login(@RequestBody LoginDto loginDto){
        Authentication authentication =authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDto.getUsername(),loginDto.getPassword()));
        if(authentication.isAuthenticated()){
            return jwtTokenProvider.tokenGeneration(loginDto.getUsername());
        }else{
            throw new UsernameNotFoundException("Invalid User");
        }
    }
    @PostMapping("/signup")
    public UserDetailsBO CreateUser(@RequestBody SignupDto signupDto){
        return userService.createUser(signupDto);
    }
    @GetMapping("/users")
    public List<UserDetailsBO> getAllUsers(){
        return userService.getUsers();
    }
}
