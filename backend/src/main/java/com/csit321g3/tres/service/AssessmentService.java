package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.AssessmentRequest;
import com.csit321g3.tres.entity.Assessment;
import com.csit321g3.tres.repository.AssessmentRepository;
import com.csit321g3.tres.repository.QuestionRepository;
import com.csit321g3.tres.repository.AssessmentAttemptRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class AssessmentService {
    private final AssessmentRepository repository;
    private final QuestionRepository questionRepository;
    private final AssessmentAttemptRepository attemptRepository;

    public AssessmentService(AssessmentRepository repository, QuestionRepository questionRepository, AssessmentAttemptRepository attemptRepository) {
        this.repository = repository;
        this.questionRepository = questionRepository;
        this.attemptRepository = attemptRepository;
    }

    public List<Assessment> findAll() { return repository.findAll(); }

    public Assessment findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));
    }

    public Assessment create(AssessmentRequest request) {
        Assessment entity = new Assessment();
        apply(entity, request);
        return repository.save(entity);
    }

    public Assessment update(Integer id, AssessmentRequest request) {
        Assessment entity = findById(id);
        apply(entity, request);
        return repository.save(entity);
    }

    public void delete(Integer id) {
        Assessment entity = findById(id);
        if (questionRepository.existsByAssessment_AssessmentId(id)
                || attemptRepository.existsByAssessment_AssessmentId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Assessment has questions or attempts");
        }
        repository.delete(entity);
    }

    private void apply(Assessment entity, AssessmentRequest request) {
        entity.setTitle(request.title().trim());
    }
}
