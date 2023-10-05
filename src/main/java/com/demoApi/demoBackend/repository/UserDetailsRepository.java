package com.demoApi.demoBackend.repository;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailsRepository extends JpaRepository<UserDetailsBO,Integer> {
    Optional<UserDetailsBO> findByEmail(String email);
}
