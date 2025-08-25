package id.my.hendisantika.digitalvitalregistrationsystem.marriage.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums.CertificateStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.model.StaffUser;
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
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.06
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarriageCertificateRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference(value = "marriage-request")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "requested_by")
    private Citizen requestedBy;


    // Citizen husband or wife (if not foreign)
    @JsonBackReference(value = "marriage-request-husband")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "husband_id", referencedColumnName = "id")
    private Citizen husband;

    @JsonBackReference(value = "marriage-request-wife")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wife_id", referencedColumnName = "id")
    private Citizen wife;

    // Foreign husband or wife
    @JsonBackReference(value = "foreign-marriage")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    @JoinColumn(name = "foreign_partner_id")
    private ForeignPerson foreignPartner;


    private Long partnerId;

    private String scheduledTime;

    private LocalDate marriageDate;
    private String municipality;

    // Ward office recommendation file
    @Lob
    @Column(columnDefinition = "TEXT")
    private String wardOfficeFileData;
    private String wardOfficeFileName;

    // Verification picture of both
    @Lob
    @Column(columnDefinition = "TEXT")
    private String marriagePhotoData;
    private String marriagePhotoFileName;

    // Status: PENDING, VERIFIED, REJECTED, APPROVED
    @Enumerated(EnumType.STRING)
    private CertificateStatus status;

    private String videoVerificationLink;
    private LocalDate requestedAt;
    private LocalDate verifiedAt;
    private Long verifiedBy;

    @JsonManagedReference(value = "certificate-file-marriage-request")
    @OneToOne(cascade = CascadeType.MERGE, mappedBy = "marriageCertificateRequest")
    private CertificateFile certificateFile;


    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.MERGE)
    private StaffUser staffUser;


    @PrePersist
    private void prePersist() {
        if (requestedAt == null) {
            requestedAt = LocalDate.now();
        }
        if (status == null) {
            setStatus(CertificateStatus.PENDING);
        }
    }
}
