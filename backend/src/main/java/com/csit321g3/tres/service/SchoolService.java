package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.SchoolRequest;
import com.csit321g3.tres.dto.SchoolResponse;
import com.csit321g3.tres.entity.Division;
import com.csit321g3.tres.entity.School;
import com.csit321g3.tres.repository.SchoolRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class SchoolService {

    private final SchoolRepository repository;
    private final DivisionService divisionService;

    public SchoolService(
            SchoolRepository repository,
            DivisionService divisionService) {

        this.repository = repository;
        this.divisionService = divisionService;
    }

    private SchoolResponse toResponse(School school) {
        return new SchoolResponse(
            school.getSchoolId(),
            school.getSchoolName(),
            school.getDivision().getDivisionId()
        );
    }

    public List<SchoolResponse> findAll() {
        return repository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public School findEntityById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "School not found"
            ));
    }

    public SchoolResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    public SchoolResponse create(SchoolRequest request) {

        Division division =
            divisionService.findById(request.divisionId());

        School school = new School();
        school.setSchoolName(request.schoolName().trim());
        school.setDivision(division);

        return toResponse(repository.save(school));
    }

    public SchoolResponse update(
            Integer id, SchoolRequest request) {

        School school = findEntityById(id);

        Division division =
            divisionService.findById(request.divisionId());

        school.setSchoolName(request.schoolName().trim());
        school.setDivision(division);

        return toResponse(repository.save(school));
    }

    public void delete(Integer id) {
        School school = findEntityById(id);
        repository.delete(school);
    }
}
