package com.csit321g3.tres.controller;

import com.csit321g3.tres.entity.LearningModule;
import com.csit321g3.tres.service.LearningModuleService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/modules")
public class LearningModuleController {

    private final LearningModuleService service;

    public LearningModuleController(LearningModuleService service) {
        this.service = service;
    }

    @GetMapping
    public List<LearningModule> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public LearningModule getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public ResponseEntity<LearningModule> create(@Valid @RequestBody LearningModule module) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(module));
    }

    @PutMapping("/{id}")
    public LearningModule update(@PathVariable Long id, @Valid @RequestBody LearningModule module) {
        return service.update(id, module);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}