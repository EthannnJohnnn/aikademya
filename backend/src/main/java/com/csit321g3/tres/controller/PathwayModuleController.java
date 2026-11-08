package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.PathwayModuleRequest;
import com.csit321g3.tres.entity.PathwayModule;
import com.csit321g3.tres.service.PathwayModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pathway-modules")
public class PathwayModuleController {

    private final PathwayModuleService service;

    public PathwayModuleController(PathwayModuleService service) {
        this.service = service;
    }

    @GetMapping
    public List<PathwayModule> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public PathwayModule findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<PathwayModule> create(@Valid @RequestBody PathwayModuleRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public PathwayModule update(@PathVariable Integer id, @Valid @RequestBody PathwayModuleRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}