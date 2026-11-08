package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.ActivityRequest;
import com.csit321g3.tres.entity.Activity;
import com.csit321g3.tres.repository.ActivityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ActivityService {

    private final ActivityRepository repository;
    private final LessonService lessonService;

    public ActivityService(ActivityRepository repository, LessonService lessonService) {
        this.repository = repository;
        this.lessonService = lessonService;
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
        repository.delete(getById(id));
    }

    private void apply(Activity activity, ActivityRequest req) {
        activity.setLesson(lessonService.getById(req.lessonId())); // 404 if lesson doesn't exist
        activity.setTitle(req.title());
        activity.setInstructions(req.instructions());
    }
}