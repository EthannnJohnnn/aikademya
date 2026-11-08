package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.UserRoleRequest;
import com.csit321g3.tres.dto.UserRoleResponse;
import com.csit321g3.tres.service.UserRoleService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user-roles")
public class UserRoleController {

    private final UserRoleService service;

    public UserRoleController(UserRoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserRoleResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserRoleResponse getById(
            @PathVariable Integer id) {

        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserRoleResponse create(
            @Valid @RequestBody UserRoleRequest request) {

        return service.create(request);
    }

    @PutMapping("/{id}")
    public UserRoleResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRoleRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
