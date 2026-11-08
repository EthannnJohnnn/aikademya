package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.RoleRequest;
import com.csit321g3.tres.entity.Role;
import com.csit321g3.tres.repository.RoleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> findAll() {
        return repository.findAll();
    }

    public Role findById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Role not found"
            ));
    }

    public Role create(RoleRequest request) {
        Role role = new Role();
        role.setRoleName(request.roleName().trim());

        return repository.save(role);
    }

    public Role update(Integer id, RoleRequest request) {
        Role role = findById(id);
        role.setRoleName(request.roleName().trim());

        return repository.save(role);
    }

    public void delete(Integer id) {
        Role role = findById(id);
        repository.delete(role);
    }
}
