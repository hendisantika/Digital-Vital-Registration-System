package id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.bouncycastle.oer.its.CertificateType;

import javax.management.Notification;
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
 * Time: 05.57
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CertificateFile implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String filePath;


    @ToString.Exclude
    @JsonBackReference(value = "certificate-file-birth-request")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "birth_certificate_request_id", referencedColumnName = "id", nullable = true)
    private BirthCertificateRequest birthCertificateRequest;


    @JsonBackReference(value = "certificate-file-marriage-request")
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marriage_certificate_request_id")
    private MarriageCertificateRequest marriageCertificateRequest;


    @JsonBackReference(value = "certificate-file-citizen")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id", nullable = false)
    private Citizen citizen;


    private Long verifiedBy;
    private LocalDate verifiedAt;

    private CertificateStatus status;
    private CertificateType certificateType;

    @Lob
    @JsonIgnore
    private byte[] fileData;

    @Lob
    @JsonIgnore
    @Column(length = 10000)
    private String digitalSignature;

    @Lob
    @JsonIgnore
    @Column(length = 10000)
    private String modulus;


    @Column(length = 10000)
    private String publicKey;

    @Column(unique = true, nullable = false, updatable = false)
    private String referenceNumber;

    @JsonManagedReference(value = "certificate-notification")
    @OneToMany(mappedBy = "certificate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Notification> notifications = new ArrayList<>();


    @ToString.Exclude
    @JsonBackReference(value = "death-certificate")
    @OneToOne
    @JoinColumn(name = "dathCertficateRequest_id", nullable = true)
    private DeathCertificateRequest deathCertificateRequest;
}
