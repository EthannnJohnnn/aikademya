package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.DiagnosticResultRequest;
import com.csit321g3.tres.entity.*;
import com.csit321g3.tres.repository.DiagnosticResultRepository;
import com.csit321g3.tres.repository.AssessmentAttemptRepository;
import com.csit321g3.tres.repository.LearningPathwayRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class DiagnosticResultService {
    private final DiagnosticResultRepository repository;
    private final AssessmentAttemptRepository attemptRepository;
    private final LearningPathwayRepository pathwayRepository;

    public DiagnosticResultService(DiagnosticResultRepository repository, AssessmentAttemptRepository attemptRepository, LearningPathwayRepository pathwayRepository) {
        this.repository = repository;
        this.attemptRepository = attemptRepository;
        this.pathwayRepository = pathwayRepository;
    }

    public List<DiagnosticResult> findAll() { return repository.findAll(); }

    public DiagnosticResult findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "DiagnosticResult not found"));
    }

    public DiagnosticResult create(DiagnosticResultRequest request) {
        DiagnosticResult entity = new DiagnosticResult();
        apply(entity, request);
        return repository.save(entity);
    }

    public DiagnosticResult update(Integer id, DiagnosticResultRequest request) {
        DiagnosticResult entity = findById(id);
        apply(entity, request);
        return repository.save(entity);
    }

    public void delete(Integer id) {
        DiagnosticResult entity = findById(id);
        if (pathwayRepository.existsByResult_ResultId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Diagnostic result is used by a learning pathway");
        }
        repository.delete(entity);
    }

    private void apply(DiagnosticResult entity, DiagnosticResultRequest request) {
        AssessmentAttempt attempt = attemptRepository.findById(request.attemptId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment attempt not found"));
        entity.setAttempt(attempt);
        entity.setTotalScore(request.totalScore());
    }
}
