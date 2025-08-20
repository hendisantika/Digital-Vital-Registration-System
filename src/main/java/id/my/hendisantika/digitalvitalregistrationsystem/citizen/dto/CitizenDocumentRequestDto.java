package id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.model.DeathCertificateRequest;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

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
 * Time: 08.52
 * To change this template use File | Settings | File Templates.
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class CitizenDocumentRequestDto implements Serializable {
    private Long citizenId;
    private DocumentType documentType;
    //private byte[] fileData;  Citizen Gives Multipart file needs to convert
    private Long verifiedBy;
    private MultipartFile file;
    private LocalDate uploadDate = LocalDate.now();
    private LocalDate verifiedDate;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;
    private DeathCertificateRequest deathCertificateRequestId;
}
