package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.CompetencyRequest;
import com.csit321g3.tres.entity.Competency;
import com.csit321g3.tres.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompetencyService {

    private final CompetencyRepository repository;
    private final CompetencyScoreRepository scoreRepository;
    private final QuestionRepository questionRepository;

    public CompetencyService(
            CompetencyRepository repository,
            CompetencyScoreRepository scoreRepository,
            QuestionRepository questionRepository) {

        this.repository = repository;
        this.scoreRepository = scoreRepository;
        this.questionRepository = questionRepository;
    }

    public List<Competency> findAll() {
        return repository.findAll();
    }

    public Competency findById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Competency not found"
            ));
    }

    public Competency create(CompetencyRequest request) {
        Competency competency = new Competency();
        competency.setName(request.name().trim());
        return repository.save(competency);
    }

    public Competency update(
            Integer id, CompetencyRequest request) {

        Competency competency = findById(id);
        competency.setName(request.name().trim());
        return repository.save(competency);
    }

    public void delete(Integer id) {
        Competency competency = findById(id);

        if (scoreRepository.existsByCompetency_CompetencyId(id)
                || questionRepository.existsByCompetencyId(id)) {

            throw new ResponseStatusException(
                HttpStatus.CONFLICT,
                "Competency is referenced by scores or questions"
            );
        }

        repository.delete(competency);
    }
}
