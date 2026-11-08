package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.User;
import com.csit321g3.tres.entity.Role;
import com.csit321g3.tres.entity.UserRole;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository
        extends JpaRepository<UserRole, Integer> {

    boolean existsByUserAndRole(User user, Role role);
}
