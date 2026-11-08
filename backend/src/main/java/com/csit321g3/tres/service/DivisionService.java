package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.DivisionRequest;
import com.csit321g3.tres.entity.Division;
import com.csit321g3.tres.repository.DivisionRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class DivisionService {

    private final DivisionRepository repository;

    public DivisionService(DivisionRepository repository) {
        this.repository = repository;
    }

    public List<Division> findAll() {
        return repository.findAll();
    }

    public Division findById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Division not found"
            ));
    }

    public Division create(DivisionRequest request) {
        Division division = new Division();
        division.setName(request.name().trim());
        return repository.save(division);
    }

    public Division update(Integer id, DivisionRequest request) {
        Division division = findById(id);
        division.setName(request.name().trim());
        return repository.save(division);
    }

    public void delete(Integer id) {
        Division division = findById(id);
        repository.delete(division);
    }
}
