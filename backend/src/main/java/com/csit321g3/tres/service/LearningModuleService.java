package com.csit321g3.tres.service;

import com.csit321g3.tres.entity.LearningModule;
import com.csit321g3.tres.repository.LearningModuleRepository;
import com.csit321g3.tres.repository.LessonRepository;
import com.csit321g3.tres.repository.ModuleProgressRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import com.csit321g3.tres.repository.PathwayModuleRepository;


@Service
public class LearningModuleService {

    private final LearningModuleRepository repository;
    private final LessonRepository lessonRepository;
    private final PathwayModuleRepository pathwayModuleRepository;
    private final ModuleProgressRepository moduleProgressRepository;

    public LearningModuleService(LearningModuleRepository repository,
                             LessonRepository lessonRepository,
                             ModuleProgressRepository moduleProgressRepository,
                             PathwayModuleRepository pathwayModuleRepository) {
    this.repository = repository;
    this.lessonRepository = lessonRepository;
    this.moduleProgressRepository = moduleProgressRepository;
    this.pathwayModuleRepository = pathwayModuleRepository;
}


    public List<LearningModule> getAll() { return repository.findAll(); }

    public LearningModule getById(Long id) {
        return repository.findById(id).orElseThrow(() ->
            new ResponseStatusException(HttpStatus.NOT_FOUND, "Learning module not found"));
    }

    public LearningModule create(LearningModule input) {
        input.setId(null);
        return repository.save(input);
    }

    public LearningModule update(Long id, LearningModule input) {
        LearningModule existing = getById(id);
        existing.setTitle(input.getTitle());
        return repository.save(existing);
    }

    public void delete(Long id) {
        LearningModule module = getById(id);
        if (lessonRepository.existsByModuleId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Delete this module's lessons first");
        }
        if (moduleProgressRepository.existsByModuleId(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Delete this module's progress records first");
        }
        if (pathwayModuleRepository.existsByModule_Id(id)) {
        throw new ResponseStatusException(HttpStatus.CONFLICT,
            "Remove this module from its pathways first");
}
        repository.delete(module);
    }
}