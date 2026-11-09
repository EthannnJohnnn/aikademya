package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.DiagnosticResultRequest;
import com.csit321g3.tres.entity.DiagnosticResult;
import com.csit321g3.tres.service.DiagnosticResultService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/diagnostic-results")
public class DiagnosticResultController {
    private final DiagnosticResultService service;

    public DiagnosticResultController(DiagnosticResultService service) { this.service = service; }

    @GetMapping
    public List<DiagnosticResult> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public DiagnosticResult findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<DiagnosticResult> create(@Valid @RequestBody DiagnosticResultRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public DiagnosticResult update(@PathVariable Integer id, @Valid @RequestBody DiagnosticResultRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}
