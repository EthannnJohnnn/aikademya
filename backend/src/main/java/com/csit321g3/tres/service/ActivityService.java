package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.ActivityRequest;
import com.csit321g3.tres.entity.Activity;
import com.csit321g3.tres.repository.ActivityRepository;
import com.csit321g3.tres.repository.ScenarioRepository;


import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository repository;
    private final LessonService lessonService;
    private final ScenarioRepository scenarioRepository;


    public ActivityService(ActivityRepository repository,
                       LessonService lessonService,
                       ScenarioRepository scenarioRepository) {
    this.repository = repository;
    this.lessonService = lessonService;
    this.scenarioRepository = scenarioRepository;
}


    public List<Activity> getAll() {
        return repository.findAll();
    }

    public Activity getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Activity not found"));
    }

    public Activity create(ActivityRequest req) {
        Activity activity = new Activity();
        apply(activity, req);
        return repository.save(activity);
    }

    public Activity update(Long id, ActivityRequest req) {
        Activity activity = getById(id);
        apply(activity, req);
        return repository.save(activity);
    }

  public void delete(Long id) {
    Activity activity = getById(id);
    if (scenarioRepository.existsByActivityId(id)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Delete this activity's scenarios first");
    }
    repository.delete(activity);
}

    private void apply(Activity activity, ActivityRequest req) {
        activity.setLesson(lessonService.getById(req.lessonId())); // 404 if lesson doesn't exist
        activity.setTitle(req.title());
        activity.setInstructions(req.instructions());
    }
}