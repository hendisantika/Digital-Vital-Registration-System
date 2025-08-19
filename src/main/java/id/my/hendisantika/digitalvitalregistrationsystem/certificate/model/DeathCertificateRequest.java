package id.my.hendisantika.digitalvitalregistrationsystem.certificate.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Past;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.management.relation.Relation;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 06.18
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class DeathCertificateRequest implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String deceasedName;
    private String fatherName;
    private String motherName;
    @Past(message = "Should be in the Past")
    private LocalDate deceasedDate;
    @Past(message = "Should be in the Past")
    private LocalDate dateOfBirth;
    private String causeOfDeath;
    private String placeOfDeath;
    private String nationality;
    private String district;
    private String municipality;
    private int wardNo;
    @Column(unique = true, nullable = true)
    private String citizenshipNumber;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @Enumerated(EnumType.STRING)
    private Relation relation;
    @Enumerated(EnumType.STRING)
    private CertificateStatus certificateStatus;


    @JsonBackReference(value = "death-certificate-request")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by_id")
    private Citizen requestedBy;

    private LocalDateTime requestedAt;

    @JsonBackReference(value = "death-request")
    @OneToOne
    @JoinColumn(name = "hospital_doc_id")
    private CitizenDocument citizenDocument;

    @JsonManagedReference(value = "death-certificate")
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "deathCertificateRequest")
    private CertificateFile certificateFile;

    @ManyToOne(fetch = FetchType.LAZY)
    private StaffUser staffUser;
}
