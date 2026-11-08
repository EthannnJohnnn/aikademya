package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.LessonRequest;
import com.csit321g3.tres.entity.Lesson;
import com.csit321g3.tres.repository.ActivityRepository;
import com.csit321g3.tres.repository.LessonRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;

@Service
public class LessonService {

    private final LessonRepository repository;
    private final LearningModuleService moduleService;
    private final ActivityRepository activityRepository;


    public LessonService(LessonRepository repository,
                     LearningModuleService moduleService,
                     ActivityRepository activityRepository) {
    this.repository = repository;
    this.moduleService = moduleService;
    this.activityRepository = activityRepository;
}


    public List<Lesson> getAll() { return repository.findAll(); }

    public Lesson getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Lesson not found"));
    }

    public Lesson create(LessonRequest req) {
        Lesson lesson = new Lesson();
        apply(lesson, req);
        return repository.save(lesson);
    }

    public Lesson update(Long id, LessonRequest req) {
        Lesson lesson = getById(id);
        apply(lesson, req);
        return repository.save(lesson);
    }

    public void delete(Long id) {
    Lesson lesson = getById(id);
    if (activityRepository.existsByLessonId(id)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
                "Delete this lesson's activities first");
    }
    repository.delete(lesson);
}

    private void apply(Lesson lesson, LessonRequest req) {
        lesson.setModule(moduleService.getById(req.moduleId())); // 404 if module doesn't exist
        lesson.setTitle(req.title());
        lesson.setContent(req.content());
    }
}