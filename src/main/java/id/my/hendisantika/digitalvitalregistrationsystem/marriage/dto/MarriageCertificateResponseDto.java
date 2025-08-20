package id.my.hendisantika.digitalvitalregistrationsystem.marriage.dto;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.enums.CertificateStatus;
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
 * Time: 06.09
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MarriageCertificateResponseDto implements Serializable {
    private Long id;
    private String partnerName;
    private String partnerEmail;
    private String marriagePlace;
    private LocalDate marriageDate;
    private CertificateStatus marriageStatus;
    private String requestedBy;
    private String scheduledTime;
}
