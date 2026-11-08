package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.DivisionRequest;
import com.csit321g3.tres.entity.Division;
import com.csit321g3.tres.service.DivisionService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/divisions")
public class DivisionController {

    private final DivisionService service;

    public DivisionController(DivisionService service) {
        this.service = service;
    }

    @GetMapping
    public List<Division> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Division getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Division create(
            @Valid @RequestBody DivisionRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Division update(
            @PathVariable Integer id,
            @Valid @RequestBody DivisionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
