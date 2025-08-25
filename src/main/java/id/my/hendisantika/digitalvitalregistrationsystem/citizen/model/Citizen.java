package id.my.hendisantika.digitalvitalregistrationsystem.citizen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.BirthCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.DeathCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.CitizenStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.MarriageStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.marriage.model.MarriageCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 08.47
 * To change this template use File | Settings | File Templates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "citizens")
public class Citizen implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String spouseName;
    private LocalDate dateOfBirth;
    @Column(unique = true, nullable = false)
    private Long phoneNo;
    private String fatherName;
    private String motherName;
    private String grandfatherName;
    private String grandmotherName;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private MarriageStatus marriageStatus;
    private String nationality;
    private String district;
    private String municipality;
    private int wardNo;
    private String tole;
    private CitizenStatus status;
    private String reasonForRejection;
    private LocalDate verifiedDate;
    @Column(unique = true)
    private String citizenshipNumber;
    private Long verifiedBy;
    @CreationTimestamp
    private LocalDate createdAt;
    private LocalDate updatedAt;

    @JsonManagedReference(value = "citizen-document")
    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL)
    private List<CitizenDocument> documents;

    @JsonManagedReference(value = "certificate-file-citizen")
    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL)
    private List<CertificateFile> certificateFiles;

    @JsonManagedReference(value = "birth-certificate-request")
    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL)
    private List<BirthCertificateRequest> birthCertificateRequests;


    // Add these fields
    @Column(name = "user_id_value")
    private Long userId;

    @Column(name = "user_email_value")
    private String userEmail;

    // Keep the existing relationship
    @JsonManagedReference("user-data")
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    @JsonBackReference("notification-data")
    @OneToMany(mappedBy = "citizen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Notification> notifications = new ArrayList<>();

    @JsonManagedReference(value = "death-certificate-request")
    @OneToMany(mappedBy = "requestedBy")
    private List<DeathCertificateRequest> deathCertificateRequests;

    @JsonManagedReference(value = "marriage-request-husband")
    @OneToMany(mappedBy = "husband", cascade = CascadeType.ALL)
    private List<MarriageCertificateRequest> marriageRequestsAsHusband;

    @JsonManagedReference(value = "marriage-request-wife")
    @OneToMany(mappedBy = "wife", cascade = CascadeType.ALL)
    private List<MarriageCertificateRequest> marriageRequestsAsWife;

    @JsonManagedReference(value = "marriage-request")
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "requestedBy")
    private List<MarriageCertificateRequest> requestedBy;
}
