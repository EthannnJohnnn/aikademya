package com.csit321g3.tres.controller;

import com.csit321g3.tres.dto.CertificateRequest;
import com.csit321g3.tres.entity.Certificate;
import com.csit321g3.tres.service.CertificateService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService service;

    public CertificateController(CertificateService service) {
        this.service = service;
    }

    @GetMapping
    public List<Certificate> findAll() { return service.findAll(); }

    @GetMapping("/{id}")
    public Certificate findById(@PathVariable Integer id) { return service.findById(id); }

    @PostMapping
    public ResponseEntity<Certificate> create(@Valid @RequestBody CertificateRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(request));
    }

    @PutMapping("/{id}")
    public Certificate update(@PathVariable Integer id, @Valid @RequestBody CertificateRequest request) {
        return service.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) { service.delete(id); }
}