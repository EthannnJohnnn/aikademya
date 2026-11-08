package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.SchoolRequest;
import com.csit321g3.tres.dto.SchoolResponse;
import com.csit321g3.tres.service.SchoolService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schools")
public class SchoolController {

    private final SchoolService service;

    public SchoolController(SchoolService service) {
        this.service = service;
    }

    @GetMapping
    public List<SchoolResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public SchoolResponse getById(
            @PathVariable Integer id) {

        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolResponse create(
            @Valid @RequestBody SchoolRequest request) {

        return service.create(request);
    }

    @PutMapping("/{id}")
    public SchoolResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody SchoolRequest request) {

        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
