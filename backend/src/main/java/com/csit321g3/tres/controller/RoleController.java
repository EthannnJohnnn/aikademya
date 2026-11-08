package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.RoleRequest;
import com.csit321g3.tres.entity.Role;
import com.csit321g3.tres.service.RoleService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }

    @GetMapping
    public List<Role> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Role getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Role create(
            @Valid @RequestBody RoleRequest request) {

        return service.create(request);
    }

    @PutMapping("/{id}")
    public Role update(
            @PathVariable Integer id,
            @Valid @RequestBody RoleRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
