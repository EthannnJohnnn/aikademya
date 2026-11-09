package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.CompetencyRequest;
import com.csit321g3.tres.entity.Competency;
import com.csit321g3.tres.service.CompetencyService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competencies")
public class CompetencyController {

    private final CompetencyService service;

    public CompetencyController(CompetencyService service) {
        this.service = service;
    }

    @GetMapping
    public List<Competency> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public Competency getById(@PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Competency create(
            @Valid @RequestBody CompetencyRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public Competency update(
            @PathVariable Integer id,
            @Valid @RequestBody CompetencyRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
