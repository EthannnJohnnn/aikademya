package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository
        extends JpaRepository<Role, Integer> {
}
