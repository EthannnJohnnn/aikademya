package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.AssessmentAttemptRequest;
import com.csit321g3.tres.entity.AssessmentAttempt;
import com.csit321g3.tres.service.AssessmentAttemptService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/assessment-attempts")
public class AssessmentAttemptController {
    private final AssessmentAttemptService service;

    public AssessmentAttemptController(AssessmentAttemptService service) { this.service = service; }

    @GetMapping
    public List<AssessmentAttempt> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public AssessmentAttempt findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<AssessmentAttempt> create(@Valid @RequestBody AssessmentAttemptRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public AssessmentAttempt update(@PathVariable Integer id, @Valid @RequestBody AssessmentAttemptRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}
