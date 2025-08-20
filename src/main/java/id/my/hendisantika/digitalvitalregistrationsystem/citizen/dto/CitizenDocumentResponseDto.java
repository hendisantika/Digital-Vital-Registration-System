package id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.DocumentType;
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
 * Date: 20/08/25
 * Time: 08.53
 * To change this template use File | Settings | File Templates.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CitizenDocumentResponseDto implements Serializable {
    private Long id;
    private String fileName;
    private DocumentType documentType;
    private Long citizenId;
    private Long verifiedBy;
    private LocalDate verifiedDate;
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt;
    private String fileData;
}
