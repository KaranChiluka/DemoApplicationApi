package com.demoApi.demoBackend.repository;

import com.demoApi.demoBackend.entity.RoleBO;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<RoleBO,Integer> {
    RoleBO findByName(String name);
}
