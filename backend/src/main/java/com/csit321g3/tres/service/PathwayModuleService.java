package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.PathwayModuleRequest;
import com.csit321g3.tres.entity.LearningModule;
import com.csit321g3.tres.entity.LearningPathway;
import com.csit321g3.tres.entity.PathwayModule;
import com.csit321g3.tres.repository.LearningModuleRepository;
import com.csit321g3.tres.repository.LearningPathwayRepository;
import com.csit321g3.tres.repository.PathwayModuleRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PathwayModuleService {

    private final PathwayModuleRepository repository;
    private final LearningPathwayRepository pathwayRepository;
    private final LearningModuleRepository moduleRepository;

    public PathwayModuleService(PathwayModuleRepository repository,
                                LearningPathwayRepository pathwayRepository,
                                LearningModuleRepository moduleRepository) {
        this.repository = repository;
        this.pathwayRepository = pathwayRepository;
        this.moduleRepository = moduleRepository;
    }

    public List<PathwayModule> findAll() {
        return repository.findAll();
    }

    public PathwayModule findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Pathway module not found"));
    }

    public PathwayModule create(PathwayModuleRequest request) {
        PathwayModule pathwayModule = new PathwayModule();
        apply(pathwayModule, request);
        return repository.save(pathwayModule);
    }

    public PathwayModule update(Integer id, PathwayModuleRequest request) {
        PathwayModule pathwayModule = findById(id);
        apply(pathwayModule, request);
        return repository.save(pathwayModule);
    }

    public void delete(Integer id) {
        repository.delete(findById(id));
    }

    private void apply(PathwayModule pathwayModule, PathwayModuleRequest request) {
        LearningPathway pathway = pathwayRepository.findById(request.pathwayId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Learning pathway not found"));
        LearningModule module = moduleRepository.findById(request.moduleId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Learning module not found"));

        boolean samePairAsBefore = pathwayModule.getPathway() != null
                && pathwayModule.getModule() != null
                && pathwayModule.getPathway().getPathwayId().equals(pathway.getPathwayId())
                && pathwayModule.getModule().getId().equals(module.getId());

        if (!samePairAsBefore
                && repository.existsByPathway_PathwayIdAndModule_Id(pathway.getPathwayId(), module.getId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Module is already in this pathway");
        }

        pathwayModule.setPathway(pathway);
        pathwayModule.setModule(module);
        pathwayModule.setSequenceNumber(request.sequenceNumber());
    }
}