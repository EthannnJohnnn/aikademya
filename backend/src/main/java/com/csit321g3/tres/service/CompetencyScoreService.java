package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.CompetencyScoreRequest;
import com.csit321g3.tres.dto.CompetencyScoreResponse;
import com.csit321g3.tres.entity.*;

import com.csit321g3.tres.repository.*;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CompetencyScoreService {

    private final CompetencyScoreRepository repository;
    private final CompetencyRepository competencyRepository;
    private final DiagnosticResultRepository resultRepository;

    public CompetencyScoreService(
            CompetencyScoreRepository repository,
            CompetencyRepository competencyRepository,
            DiagnosticResultRepository resultRepository) {

        this.repository = repository;
        this.competencyRepository = competencyRepository;
        this.resultRepository = resultRepository;
    }

    private CompetencyScoreResponse toResponse(CompetencyScore score) {
        return new CompetencyScoreResponse(
            score.getScoreId(),
            score.getResult().getResultId(),
            score.getCompetency().getCompetencyId()
        );
    }

    private CompetencyScore findEntityById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "CompetencyScore not found"
            ));
    }

    private void apply(
            CompetencyScore score,
            CompetencyScoreRequest request) {

        DiagnosticResult result =
            resultRepository.findById(request.resultId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Diagnostic result not found"
                ));

        Competency competency =
            competencyRepository.findById(request.competencyId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Competency not found"
                ));

        score.setResult(result);
        score.setCompetency(competency);
    }

    public List<CompetencyScoreResponse> findAll() {
        return repository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public CompetencyScoreResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    public CompetencyScoreResponse create(
            CompetencyScoreRequest request) {

        CompetencyScore score = new CompetencyScore();
        apply(score, request);

        return toResponse(repository.save(score));
    }

    public CompetencyScoreResponse update(
            Integer id, CompetencyScoreRequest request) {

        CompetencyScore score = findEntityById(id);
        apply(score, request);

        return toResponse(repository.save(score));
    }

    public void delete(Integer id) {
        CompetencyScore score = findEntityById(id);
        repository.delete(score);
    }
}
