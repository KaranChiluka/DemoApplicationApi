package com.demoApi.demoBackend.service.Impl.impl;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import com.demoApi.demoBackend.service.Impl.UserService;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    DozerBeanMapper mapper;

    @Override
    public List<UserDetailsBO> getUsers() {
        return userDetailsRepository.findAll();
    }

    @Override
    public UserDetailsBO createUser(SignupDto signupDto) {
        signupDto.setPassword(encoder.encode(signupDto.getPassword()));
        UserDetailsBO userDetailsBO = mapper.map(signupDto, UserDetailsBO.class);
        return userDetailsRepository.save(userDetailsBO);
    }
}
