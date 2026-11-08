package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.ScenarioRequest;
import com.csit321g3.tres.entity.Scenario;
import com.csit321g3.tres.service.ScenarioService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scenarios")
public class ScenarioController {

    private final ScenarioService service;

    public ScenarioController(ScenarioService service) {
        this.service = service;
    }

    @GetMapping
    public List<Scenario> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Scenario getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<Scenario> create(@Valid @RequestBody ScenarioRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public Scenario update(@PathVariable Long id, @Valid @RequestBody ScenarioRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}