package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository
        extends JpaRepository<User, Integer> {
}
