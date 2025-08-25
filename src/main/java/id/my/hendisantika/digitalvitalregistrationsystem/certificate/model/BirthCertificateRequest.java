package id.my.hendisantika.digitalvitalregistrationsystem.certificate.model;

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
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 20/08/25
 * Time: 05.59
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Table(
        name = "birth_certificate_request",
        uniqueConstraints = @UniqueConstraint(
                name = "uk_child_date_citizen",
                columnNames = {"child_name", "date_of_birth", "citizen_id"}
        )
)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BirthCertificateRequest implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "child_name", nullable = false)
    private String childName;

    private String gender;

    @Column(name = "date_of_birth", nullable = false)
    private LocalDate dateOfBirth;

    @ManyToOne
    @JsonBackReference(value = "birth-certificate-request")
    @JoinColumn(name = "citizen_id", referencedColumnName = "id", nullable = false)
    private Citizen citizen;

    private Long requestedBy;

    private String municipality;
    private String district;
    private int wardNo;
    private String nationality;

    private LocalDate requestedAt;

    @Enumerated(EnumType.STRING)
    private CertificateStatus status;

    @ToString.Exclude
    @JsonManagedReference(value = "certificate-file-birth-request")
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "birthCertificateRequest")
    private CertificateFile certificateFile;

    @ManyToOne(cascade = CascadeType.ALL)
    private StaffUser staffUser;
}
