package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.ResponseRequest;
import com.csit321g3.tres.entity.*;
import com.csit321g3.tres.repository.ResponseRepository;
import com.csit321g3.tres.repository.QuestionRepository;
import com.csit321g3.tres.repository.AssessmentAttemptRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class ResponseService {
    private final ResponseRepository repository;
    private final AssessmentAttemptRepository attemptRepository;
    private final QuestionRepository questionRepository;

    public ResponseService(ResponseRepository repository, AssessmentAttemptRepository attemptRepository, QuestionRepository questionRepository) {
        this.repository = repository;
        this.attemptRepository = attemptRepository;
        this.questionRepository = questionRepository;
    }

    public List<Response> findAll() { return repository.findAll(); }

    public Response findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Response not found"));
    }

    public Response create(ResponseRequest request) {
        Response entity = new Response();
        apply(entity, request);
        return repository.save(entity);
    }

    public Response update(Integer id, ResponseRequest request) {
        Response entity = findById(id);
        apply(entity, request);
        return repository.save(entity);
    }

    public void delete(Integer id) {
        Response entity = findById(id);

        repository.delete(entity);
    }

    private void apply(Response entity, ResponseRequest request) {
        AssessmentAttempt attempt = attemptRepository.findById(request.attemptId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Assessment attempt not found"));
        Question question = questionRepository.findById(request.questionId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Question not found"));
        if (!attempt.getAssessment().getAssessmentId().equals(question.getAssessment().getAssessmentId())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Question is not in this attempt's assessment");
        }
        if (repository.existsByAttempt_AttemptIdAndQuestion_QuestionId(request.attemptId(), request.questionId())
                && (entity.getResponseId() == null
                    || !entity.getAttempt().getAttemptId().equals(request.attemptId())
                    || !entity.getQuestion().getQuestionId().equals(request.questionId()))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Response for this question already exists in this attempt");
        }
        entity.setAttempt(attempt);
        entity.setQuestion(question);
    }
}
