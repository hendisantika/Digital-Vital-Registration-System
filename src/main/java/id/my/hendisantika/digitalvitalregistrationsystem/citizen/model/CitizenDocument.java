package id.my.hendisantika.digitalvitalregistrationsystem.citizen.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.DeathCertificateRequest;
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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.w3c.dom.DocumentType;

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
 * Time: 08.49
 * To change this template use File | Settings | File Templates.
 */
@Data
@ToString(onlyExplicitlyIncluded = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class CitizenDocument implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Many document belong to one citizen
    @JsonBackReference(value = "citizen-document")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "citizen_id", referencedColumnName = "id", nullable = false)
    private Citizen citizen;

    @JsonManagedReference(value = "death-request")
    @ManyToOne(fetch = FetchType.LAZY)
    private DeathCertificateRequest deathCertificateRequest;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;
    @Lob
    @Column(columnDefinition = "TEXT")
    private String fileData;
    private String fileName;
    private LocalDate uploadDate;
    private Long verifiedBy;
    private LocalDate verifiedDate;
    private LocalDate createdAt;
    private LocalDate updatedAt;
}
