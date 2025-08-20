package id.my.hendisantika.digitalvitalregistrationsystem.marriage.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class MarriageCertificateReviewResponseDto {
    private Long id;

    private String requestedByFullName;
    private String status;
    private String videoVerificationLink;
    private String placeOfMarriage;
    private LocalDate marriageDate;
    private LocalDate requestedAt;
    private LocalDate verifiedAt;

    private CitizenDto partner;
    private ForeignPartnerDto foreignPartner;

    private FileDto wardOfficeFile;
    private FileDto marriagePhoto;
}
