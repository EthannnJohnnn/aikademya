package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.UserRequest;
import com.csit321g3.tres.dto.UserResponse;
import com.csit321g3.tres.service.UserService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public List<UserResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public UserResponse getById(
            @PathVariable Integer id) {

        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse create(
            @Valid @RequestBody UserRequest request) {

        return service.create(request);
    }

    @PutMapping("/{id}")
    public UserResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody UserRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
