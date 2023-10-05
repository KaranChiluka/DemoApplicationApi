package com.demoApi.demoBackend.service.Impl.impl;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import com.demoApi.demoBackend.service.Impl.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsServiceImpl implements CustomUserDetailsService {
    @Autowired
    private UserDetailsRepository userDetailsRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsRepository.findByEmail(username).orElseThrow(()->new RuntimeException("User not found !!"));
    }
}
