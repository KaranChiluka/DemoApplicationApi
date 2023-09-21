package com.demoApi.demoBackend.repository;

import com.demoApi.demoBackend.entity.UserDetailsBO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserDetailsRepository extends CrudRepository<UserDetailsBO,Integer> {
    Optional<UserDetailsBO> findByUsername(String username);
}
