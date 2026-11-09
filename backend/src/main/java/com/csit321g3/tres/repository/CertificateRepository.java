package com.csit321g3.tres.repository;

import com.csit321g3.tres.entity.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRepository extends JpaRepository<Certificate, Integer> {

    boolean existsByVerificationCode(String verificationCode);
    boolean existsByUser_UserId(Integer userId);

    boolean existsByVerificationCodeAndCertificateIdNot(String verificationCode, Integer certificateId);
}