package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.UserRequest;
import com.csit321g3.tres.dto.UserResponse;
import com.csit321g3.tres.entity.School;
import com.csit321g3.tres.entity.User;
import com.csit321g3.tres.repository.UserRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;
    private final SchoolService schoolService;

    public UserService(
            UserRepository repository,
            SchoolService schoolService) {

        this.repository = repository;
        this.schoolService = schoolService;
    }

    private UserResponse toResponse(User user) {
        Integer schoolId = user.getSchool() == null
            ? null
            : user.getSchool().getSchoolId();

        return new UserResponse(
            user.getUserId(),
            user.getEmail(),
            schoolId
        );
    }

    public List<UserResponse> findAll() {
        return repository.findAll()
            .stream()
            .map(this::toResponse)
            .toList();
    }

    public User findEntityById(Integer id) {
        return repository.findById(id)
            .orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "User not found"
            ));
    }

    public UserResponse findById(Integer id) {
        return toResponse(findEntityById(id));
    }

    private void apply(User user, UserRequest request) {
        user.setEmail(request.email().trim());

        if (request.schoolId() == null) {
            user.setSchool(null);
        } else {
            School school =
                schoolService.findEntityById(request.schoolId());

            user.setSchool(school);
        }
    }

    public UserResponse create(UserRequest request) {
        User user = new User();
        apply(user, request);

        return toResponse(repository.save(user));
    }

    public UserResponse update(
            Integer id, UserRequest request) {

        User user = findEntityById(id);
        apply(user, request);

        return toResponse(repository.save(user));
    }

    public void delete(Integer id) {
        User user = findEntityById(id);
        repository.delete(user);
    }
}
