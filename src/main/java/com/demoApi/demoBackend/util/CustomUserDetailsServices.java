package com.demoApi.demoBackend.util;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import com.demoApi.demoBackend.repository.UserDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CustomUserDetailsServices implements UserDetailsService {
    @Autowired
    UserDetailsRepository userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserDetailsBO> userDetailsBO = userDetailsRepository.findByUsername(username);
        return userDetailsBO.map(CustomUserDetails::new)
                .orElseThrow(()->new UsernameNotFoundException("User not found with username: "+username));
    }

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        Optional<UserDetailsBO> user = userDetailsRepository.findByUsername(username);
//        return user.map(u->new CustomUserDetails(u.getUsername(),u.getPassword(),getAuthorities("USER"))
//        ).orElseThrow(()-> new UsernameNotFoundException("User not found with username: "+username));
//    }
//
//    private Collection<? extends GrantedAuthority> getAuthorities(String role) {
//        List<GrantedAuthority> authorities = new ArrayList<>();
//        authorities.add(new SimpleGrantedAuthority(role));
//        return authorities;
//    }
}
