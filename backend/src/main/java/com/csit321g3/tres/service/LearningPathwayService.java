package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.LearningPathwayRequest;
import com.csit321g3.tres.entity.LearningPathway;
import com.csit321g3.tres.entity.User;
import com.csit321g3.tres.repository.LearningPathwayRepository;
import com.csit321g3.tres.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class LearningPathwayService {

    private final LearningPathwayRepository repository;
    private final UserRepository userRepository;

    public LearningPathwayService(LearningPathwayRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<LearningPathway> findAll() {
        return repository.findAll();
    }

    public LearningPathway findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Learning pathway not found"));
    }

    public LearningPathway create(LearningPathwayRequest request) {
        LearningPathway pathway = new LearningPathway();
        apply(pathway, request);
        return repository.save(pathway);
    }

    public LearningPathway update(Integer id, LearningPathwayRequest request) {
        LearningPathway pathway = findById(id);
        apply(pathway, request);
        return repository.save(pathway);
    }

    public void delete(Integer id) {
        repository.delete(findById(id));
    }

private void apply(LearningPathway pathway, LearningPathwayRequest request) {
    User user = userRepository.findById(request.userId()).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    pathway.setUser(user);
}
}