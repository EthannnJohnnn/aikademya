package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.ResponseRequest;
import com.csit321g3.tres.entity.Response;
import com.csit321g3.tres.service.ResponseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/responses")
public class ResponseController {
    private final ResponseService service;

    public ResponseController(ResponseService service) { this.service = service; }

    @GetMapping
    public List<Response> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Response findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Response> create(@Valid @RequestBody ResponseRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public Response update(@PathVariable Integer id, @Valid @RequestBody ResponseRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}
