package com.csit321g3.tres.service;

import com.csit321g3.tres.dto.CertificateRequest;
import com.csit321g3.tres.entity.Certificate;
import com.csit321g3.tres.entity.User;
import com.csit321g3.tres.repository.CertificateRepository;
import com.csit321g3.tres.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CertificateService {

    private final CertificateRepository repository;
    private final UserRepository userRepository;

    public CertificateService(CertificateRepository repository, UserRepository userRepository) {
        this.repository = repository;
        this.userRepository = userRepository;
    }

    public List<Certificate> findAll() {
        return repository.findAll();
    }

    public Certificate findById(Integer id) {
        return repository.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Certificate not found"));
    }

    public Certificate create(CertificateRequest request) {
        Certificate certificate = new Certificate();
        apply(certificate, request, null);
        return repository.save(certificate);
    }

    public Certificate update(Integer id, CertificateRequest request) {
        Certificate certificate = findById(id);
        apply(certificate, request, id);
        return repository.save(certificate);
    }

    public void delete(Integer id) {
        repository.delete(findById(id));
    }

    private void apply(Certificate certificate, CertificateRequest request, Integer currentId) {
        User user = userRepository.findById(request.userId()).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        String code = request.verificationCode().trim();
        boolean taken = (currentId == null)
                ? repository.existsByVerificationCode(code)
                : repository.existsByVerificationCodeAndCertificateIdNot(code, currentId);
        if (taken) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Verification code already exists");
        }

        certificate.setUser(user);
        certificate.setVerificationCode(code);
    }
}