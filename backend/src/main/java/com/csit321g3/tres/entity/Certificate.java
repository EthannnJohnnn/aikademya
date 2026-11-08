package com.csit321g3.tres.entity;

import jakarta.persistence.*;

@Entity
@Table(
    name = "certificates",
    uniqueConstraints = @UniqueConstraint(
        name = "uk_certificate_code",
        columnNames = "verification_code"
    )
)
public class Certificate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certificate_id")
    private Integer certificateId;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "verification_code", nullable = false)
    private String verificationCode;

    public Certificate() {}

    public Integer getCertificateId() { return certificateId; }
    public void setCertificateId(Integer certificateId) { this.certificateId = certificateId; }
    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }
    public String getVerificationCode() { return verificationCode; }
    public void setVerificationCode(String verificationCode) { this.verificationCode = verificationCode; }
}