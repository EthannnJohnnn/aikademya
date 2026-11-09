package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.AssessmentRequest;
import com.csit321g3.tres.entity.Assessment;
import com.csit321g3.tres.service.AssessmentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assessments")
public class AssessmentController {
    private final AssessmentService service;

    public AssessmentController(AssessmentService service) { this.service = service; }

    @GetMapping
    public List<Assessment> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Assessment findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Assessment> create(@Valid @RequestBody AssessmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public Assessment update(@PathVariable Integer id, @Valid @RequestBody AssessmentRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}
