package com.demoApi.demoBackend.service.Impl.impl;

import com.demoApi.demoBackend.dto.SignupDto;
import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import com.demoApi.demoBackend.service.Impl.UserService;
import com.demoApi.demoBackend.util.JwtTokenProvider;
import org.dozer.DozerBeanMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.StreamSupport;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserDetailsRepository userDetailsRepository;
    @Autowired
    BCryptPasswordEncoder encoder;
    @Autowired
    DozerBeanMapper mapper;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    private static final String ERROR_MESSAGE = "The requested user with id %s is not found";

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
    @Override
    public UserDetailsBO getCurrentUser(String token){
        String username = jwtTokenProvider.getUsernameFromJwt(token);
        return userDetailsRepository.findByEmail(username).get();
    }
    @Override
    public SignupDto updateUser(SignupDto signupDto){
        UserDetailsBO userDetailsBO = userDetailsRepository.findById(signupDto.getId())
                  .orElseThrow(() -> new NoSuchElementException(String.format(ERROR_MESSAGE, signupDto.getId())));
        mapper.map(signupDto,userDetailsBO);
        userDetailsRepository.save(userDetailsBO);
        return mapper.map(userDetailsBO, SignupDto.class);
    }
}
