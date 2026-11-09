package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.AssessmentAttemptRequest;
import com.csit321g3.tres.entity.*;
import com.csit321g3.tres.repository.AssessmentAttemptRepository;
import com.csit321g3.tres.repository.AssessmentRepository;
import com.csit321g3.tres.repository.ResponseRepository;
import com.csit321g3.tres.repository.DiagnosticResultRepository;
import com.csit321g3.tres.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class AssessmentAttemptService {
    private final AssessmentAttemptRepository repository;
    private final UserRepository userRepository;
    private final AssessmentRepository assessmentRepository;
    private final ResponseRepository responseRepository;
    private final DiagnosticResultRepository resultRepository;

    public AssessmentAttemptService(AssessmentAttemptRepository repository, UserRepository userRepository, AssessmentRepository assessmentRepository,
            ResponseRepository responseRepository, DiagnosticResultRepository resultRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
        this.assessmentRepository = assessmentRepository;
        this.responseRepository = responseRepository;
        this.resultRepository = resultRepository;
    }

    public List<AssessmentAttempt> findAll() { return repository.findAll(); }

    public AssessmentAttempt findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "AssessmentAttempt not found"));
    }

    public AssessmentAttempt create(AssessmentAttemptRequest request) {
        AssessmentAttempt entity = new AssessmentAttempt();
        apply(entity, request);
        return repository.save(entity);
    }

    public AssessmentAttempt update(Integer id, AssessmentAttemptRequest request) {
        AssessmentAttempt entity = findById(id);
        apply(entity, request);
        return repository.save(entity);
    }

    public void delete(Integer id) {
        AssessmentAttempt entity = findById(id);
        if (responseRepository.existsByAttempt_AttemptId(id)
                || resultRepository.existsByAttempt_AttemptId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Attempt has responses or diagnostic results");
        }
        repository.delete(entity);
    }

    private void apply(AssessmentAttempt entity, AssessmentAttemptRequest request) {
        User user = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        Assessment assessment = assessmentRepository.findById(request.assessmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));
        entity.setUser(user);
        entity.setAssessment(assessment);
    }
}
