package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.CompetencyRequest;
import com.csit321g3.tres.entity.Competency;
import com.csit321g3.tres.repository.CompetencyRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompetencyService {

    private final CompetencyRepository repository;

    public CompetencyService(CompetencyRepository repository) {
        this.repository = repository;
    }

    public List<Competency> findAll() {
        return repository.findAll();
    }

    public Competency findById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Competency not found"
            ));
    }

    public Competency create(CompetencyRequest request) {
        Competency competency = new Competency();
        competency.setName(request.name().trim());
        return repository.save(competency);
    }

    public Competency update(Integer id, CompetencyRequest request) {
        Competency competency = findById(id);
        competency.setName(request.name().trim());
        return repository.save(competency);
    }

    public void delete(Integer id) {
        Competency competency = findById(id);
        repository.delete(competency);
    }
}
