package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.ScenarioRequest;
import com.csit321g3.tres.entity.Scenario;
import com.csit321g3.tres.repository.ScenarioRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ScenarioService {

    private final ScenarioRepository repository;
    private final LessonService lessonService;

    public ScenarioService(ScenarioRepository repository, LessonService lessonService) {
        this.repository = repository;
        this.lessonService = lessonService;
    }

    public List<Scenario> getAll() {
        return repository.findAll();
    }

    public Scenario getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Scenario not found"));
    }

    public Scenario create(ScenarioRequest req) {
        Scenario scenario = new Scenario();
        apply(scenario, req);
        return repository.save(scenario);
    }

    public Scenario update(Long id, ScenarioRequest req) {
        Scenario scenario = getById(id);
        apply(scenario, req);
        return repository.save(scenario);
    }

    public void delete(Long id) {
        repository.delete(getById(id));
    }

    private void apply(Scenario scenario, ScenarioRequest req) {
        scenario.setLesson(lessonService.getById(req.lessonId())); // 404 if lesson doesn't exist
        scenario.setSituation(req.situation());
        scenario.setOptions(req.options());
    }
}