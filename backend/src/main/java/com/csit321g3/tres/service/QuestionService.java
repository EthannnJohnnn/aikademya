package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.QuestionRequest;
import com.csit321g3.tres.entity.*;
import com.csit321g3.tres.repository.QuestionRepository;
import com.csit321g3.tres.repository.AssessmentRepository;
import com.csit321g3.tres.repository.ResponseRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class QuestionService {
    private final QuestionRepository repository;
    private final AssessmentRepository assessmentRepository;
    private final ResponseRepository responseRepository;

    public QuestionService(QuestionRepository repository, AssessmentRepository assessmentRepository, ResponseRepository responseRepository) {
        this.repository = repository;
        this.assessmentRepository = assessmentRepository;
        this.responseRepository = responseRepository;
    }

    public List<Question> findAll() { return repository.findAll(); }

    public Question findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
    }

    public Question create(QuestionRequest request) {
        Question entity = new Question();
        apply(entity, request);
        return repository.save(entity);
    }

    public Question update(Integer id, QuestionRequest request) {
        Question entity = findById(id);
        apply(entity, request);
        return repository.save(entity);
    }

    public void delete(Integer id) {
        Question entity = findById(id);
        if (responseRepository.existsByQuestion_QuestionId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Question has responses");
        }
        repository.delete(entity);
    }

    private void apply(Question entity, QuestionRequest request) {
        Assessment assessment = assessmentRepository.findById(request.assessmentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment not found"));
        entity.setAssessment(assessment);
        entity.setCompetencyId(request.competencyId());
    }
}
