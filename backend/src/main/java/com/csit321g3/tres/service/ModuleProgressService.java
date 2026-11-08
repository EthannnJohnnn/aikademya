package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.ModuleProgressRequest;
import com.csit321g3.tres.dto.ModuleProgressResponse;

import com.csit321g3.tres.entity.ModuleProgress;
import com.csit321g3.tres.entity.User;
import com.csit321g3.tres.entity.LearningModule;

import com.csit321g3.tres.repository.ModuleProgressRepository;
import com.csit321g3.tres.repository.UserRepository;
import com.csit321g3.tres.repository.LearningModuleRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class ModuleProgressService {

    private final ModuleProgressRepository repository;
    private final UserRepository userRepository;
    private final LearningModuleRepository moduleRepository;

    public ModuleProgressService(
            ModuleProgressRepository repository,
            UserRepository userRepository,
            LearningModuleRepository moduleRepository) {

        this.repository = repository;
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
    }

    private ModuleProgressResponse toResponse(ModuleProgress progress) {
        return new ModuleProgressResponse(
            progress.getProgressId(),
            progress.getUser().getUserId(),
            progress.getModule().getId()
        );
    }

    private ModuleProgress findEntityById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "ModuleProgress not found"
            ));
    }

    private void apply(
            ModuleProgress progress,
            ModuleProgressRequest request) {

        User user = userRepository.findById(request.userId())
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));

        LearningModule module =
            moduleRepository.findById(request.moduleId())
                .orElseThrow(() -> new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Learning module not found"
                ));

        progress.setUser(user);
        progress.setModule(module);
    }

    public List<ModuleProgressResponse> findAll() {
        return repository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public ModuleProgressResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    public ModuleProgressResponse create(ModuleProgressRequest request) {
        ModuleProgress progress = new ModuleProgress();

        apply(progress, request);

        return toResponse(repository.save(progress));
    }

    public ModuleProgressResponse update(
            Integer id,
            ModuleProgressRequest request) {

        ModuleProgress progress = findEntityById(id);

        apply(progress, request);

        return toResponse(repository.save(progress));
    }

    public void delete(Integer id) {
        ModuleProgress progress = findEntityById(id);
        repository.delete(progress);
    }
}
