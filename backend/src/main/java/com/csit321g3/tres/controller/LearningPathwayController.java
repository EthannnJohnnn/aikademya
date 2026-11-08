package com.csit321g3.tres.controller;
import com.csit321g3.tres.dto.LearningPathwayRequest;
import com.csit321g3.tres.entity.LearningPathway;
import com.csit321g3.tres.service.LearningPathwayService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/learning-pathways")
public class LearningPathwayController {

    private final LearningPathwayService service;

    public LearningPathwayController(LearningPathwayService service) {
        this.service = service;
    }

    @GetMapping
    public List<LearningPathway> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public LearningPathway findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<LearningPathway> create(@Valid @RequestBody LearningPathwayRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public LearningPathway update(@PathVariable Integer id, @Valid @RequestBody LearningPathwayRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}