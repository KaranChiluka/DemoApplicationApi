package com.demoApi.demoBackend.controller;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.dto.request.LoginDto;
import com.demoApi.demoBackend.dto.request.TokenDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.service.UserDetailsService;
import com.demoApi.demoBackend.util.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/security")
public class SecurityController {
    @Autowired
    UserDetailsService userDetailsService;

    @PostMapping("/signup")
    public UserDetailsBO saveUser(@RequestBody SignupDto signupDto){
        return userDetailsService.saveUser(signupDto);
    }

    @PostMapping("/login")
    public String tokenGeneration( LoginDto loginDto){
        return userDetailsService.tokenGeneration(loginDto);
    }

    @PostMapping("/authentication")
    public UserDetailsBO tokenAuthentication(@RequestBody TokenDto tokenDto){
        return userDetailsService.authenticateToken(tokenDto.getToken());
    }
}
