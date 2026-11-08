package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.LessonRequest;
import com.csit321g3.tres.entity.Lesson;
import com.csit321g3.tres.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService service;

    public LessonController(LessonService service) { this.service = service; }

    @GetMapping
    public List<Lesson> getAll() { return service.getAll(); }

    @GetMapping("/{id}")
    public Lesson getById(@PathVariable Long id) { return service.getById(id); }

    @PostMapping
    public ResponseEntity<Lesson> create(@Valid @RequestBody LessonRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(req));
    }

    @PutMapping("/{id}")
    public Lesson update(@PathVariable Long id, @Valid @RequestBody LessonRequest req) {
        return service.update(id, req);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) { service.delete(id); }
}