package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.UserRoleRequest;
import com.csit321g3.tres.dto.UserRoleResponse;

import com.csit321g3.tres.entity.User;
import com.csit321g3.tres.entity.Role;
import com.csit321g3.tres.entity.UserRole;

import com.csit321g3.tres.repository.UserRepository;
import com.csit321g3.tres.repository.RoleRepository;
import com.csit321g3.tres.repository.UserRoleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserRoleService(
            UserRoleRepository repository,
            UserRepository userRepository,
            RoleRepository roleRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    private UserRoleResponse toResponse(UserRole userRole) {
        return new UserRoleResponse(
            userRole.getUserRoleId(),
            userRole.getUser().getUserId(),
            userRole.getRole().getRoleId()
        );
    }

    private User findUser(Integer id) {
        return userRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));
    }

    private Role findRole(Integer id) {
        return roleRepository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role not found"
            ));
    }

    private UserRole findEntityById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "UserRole not found"
            ));
    }

    public List<UserRoleResponse> findAll() {
        return repository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public UserRoleResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    public UserRoleResponse create(UserRoleRequest request) {

        User user = findUser(request.userId());
        Role role = findRole(request.roleId());

        if (repository.existsByUserAndRole(user, role)) {
            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "User already has this role"
            );
        }

        UserRole userRole = new UserRole();
        userRole.setUser(user);
        userRole.setRole(role);

        return toResponse(repository.save(userRole));
    }

    public UserRoleResponse update(
            Integer id, UserRoleRequest request) {

        UserRole userRole = findEntityById(id);

        User user = findUser(request.userId());
        Role role = findRole(request.roleId());

        boolean sameAssignment =
            userRole.getUser().getUserId().equals(user.getUserId())
            && userRole.getRole().getRoleId().equals(role.getRoleId());

        if (!sameAssignment &&
                repository.existsByUserAndRole(user, role)) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "User already has this role"
            );
        }

        userRole.setUser(user);
        userRole.setRole(role);

        return toResponse(repository.save(userRole));
    }

    public void delete(Integer id) {
        UserRole userRole = findEntityById(id);
        repository.delete(userRole);
    }
}
