package id.my.hendisantika.digitalvitalregistrationsystem.marriage.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
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
 * Time: 06.05
 * To change this template use File | Settings | File Templates.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignPerson implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String nationality;
    private String passportNumber;
    private String personCitizenshipNumber; // Optional: other ID type
    private String email;
    private String contactNumber;

    private LocalDate dateOfBirth;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String passportFileData;
    private String passportFileName;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String photoFileData;
    private String photoFileName;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String personCitizenshipFileData;
    private String personCitizenshipFileName;

    @JsonManagedReference(value = "foreign-marriage")
    @OneToOne(mappedBy = "foreignPartner")
    private MarriageCertificateRequest marriageCertificateRequest;
}
