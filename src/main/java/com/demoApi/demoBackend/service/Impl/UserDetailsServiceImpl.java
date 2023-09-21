package com.demoApi.demoBackend.service.Impl;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.dto.request.LoginDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.exception.InvalidCredentialsException;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import com.demoApi.demoBackend.service.UserDetailsService;
import com.demoApi.demoBackend.util.JwtTokenProvider;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.function.Supplier;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    DozerBeanMapper mapper;
    @Autowired
    JwtTokenProvider jwtTokenProvider;

    private BCryptPasswordEncoder encoder= new BCryptPasswordEncoder();

    @Override
    public UserDetailsBO saveUser(SignupDto signupDto){
        UserDetailsBO userDetailsBO = mapper.map(signupDto, UserDetailsBO.class);
        userDetailsBO.setPassword(encoder.encode(signupDto.getPassword()));
        return userDetailsRepository.save(userDetailsBO);
    }
    @Override
    public String tokenGeneration(LoginDto loginDto){
        Supplier<InvalidCredentialsException> expSupplier =
                () ->
                        new InvalidCredentialsException(
                                String.format("The requested user %s not found", loginDto.getUsername()));
        UserDetailsBO userDetailsBO = userDetailsRepository.findByUsername(loginDto.getUsername()).orElseThrow(expSupplier);
        return jwtTokenProvider.tokenGeneration(userDetailsBO.getUsername());
    }
}
