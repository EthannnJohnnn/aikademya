package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.CompetencyScoreRequest;
import com.csit321g3.tres.dto.CompetencyScoreResponse;
import com.csit321g3.tres.service.CompetencyScoreService;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/competency-scores")
public class CompetencyScoreController {

    private final CompetencyScoreService service;

    public CompetencyScoreController(CompetencyScoreService service) {
        this.service = service;
    }

    @GetMapping
    public List<CompetencyScoreResponse> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public CompetencyScoreResponse getById(
            @PathVariable Integer id) {
        return service.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CompetencyScoreResponse create(
            @Valid @RequestBody CompetencyScoreRequest request) {
        return service.create(request);
    }

    @PutMapping("/{id}")
    public CompetencyScoreResponse update(
            @PathVariable Integer id,
            @Valid @RequestBody CompetencyScoreRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        service.delete(id);
    }
}
