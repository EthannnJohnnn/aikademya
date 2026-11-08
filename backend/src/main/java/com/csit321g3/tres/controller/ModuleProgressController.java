package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.ModuleProgressRequest;
import com.csit321g3.tres.dto.ModuleProgressResponse;
import com.csit321g3.tres.service.ModuleProgressService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/module-progress")
public class ModuleProgressController {

    private final ModuleProgressService service;

    public ModuleProgressController(ModuleProgressService service) {
        this.service = service;
    }

    @GetMapping
    public List<ModuleProgressResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public ModuleProgressResponse getById(
            @PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ModuleProgressResponse create(
            @Valid @RequestBody ModuleProgressRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public ModuleProgressResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody ModuleProgressRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
