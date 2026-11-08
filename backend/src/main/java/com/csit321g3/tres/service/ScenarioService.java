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
    private final ActivityService activityService;

    public ScenarioService(ScenarioRepository repository, ActivityService activityService) {
        this.repository = repository;
        this.activityService = activityService;
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
        scenario.setActivity(activityService.getById(req.activityId())); // 404 if activity doesn't exist
        scenario.setQuestion(req.question());
        scenario.setOptions(req.options());
    }
}