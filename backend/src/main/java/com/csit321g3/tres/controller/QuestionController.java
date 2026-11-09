package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.QuestionRequest;
import com.csit321g3.tres.entity.Question;
import com.csit321g3.tres.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {
    private final QuestionService service;

    public QuestionController(QuestionService service) { this.service = service; }

    @GetMapping
    public List<Question> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Question findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Question> create(@Valid @RequestBody QuestionRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public Question update(@PathVariable Integer id, @Valid @RequestBody QuestionRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}
