package id.my.hendisantika.digitalvitalregistrationsystem.staff.model;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.BirthCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.DeathCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.MarriageCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Department;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Status;
import id.my.hendisantika.digitalvitalregistrationsystem.user.model.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.40
 * To change this template use File | Settings | File Templates.
 */
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StaffUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    private String phoneNumber;

    @Enumerated(value = EnumType.STRING)
    private Role role; // ADMIN or SUPER_ADMIN

    private String designation;

    @Enumerated(value = EnumType.STRING)
    private Department department;

    @Enumerated(EnumType.STRING)
    private Status status; // ACTIVE or INACTIVE

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    private String district;

    private String municipality;


    private String addedBy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "staffUser")
    private User user;

    @OneToMany(mappedBy = "staffUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<BirthCertificateRequest> birthCertificateRequest;

    @OneToMany(mappedBy = "staffUser", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DeathCertificateRequest> deathCertificateRequest;

    @OneToMany(mappedBy = "staffUser")
    private List<MarriageCertificateRequest> marriageCertificateRequest;
}
