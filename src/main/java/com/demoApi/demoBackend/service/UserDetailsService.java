package com.demoApi.demoBackend.service;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.dto.request.LoginDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;

public interface UserDetailsService {
    UserDetailsBO saveUser(SignupDto signupDto);

    String tokenGeneration(LoginDto loginDto);
}
