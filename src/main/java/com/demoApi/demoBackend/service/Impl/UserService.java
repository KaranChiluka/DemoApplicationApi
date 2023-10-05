package com.demoApi.demoBackend.service.Impl;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;

import java.util.List;

public interface UserService {
    List<UserDetailsBO> getUsers();
    UserDetailsBO createUser(SignupDto signupDto);
}
