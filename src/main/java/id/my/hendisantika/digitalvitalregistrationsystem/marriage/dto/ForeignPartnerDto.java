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
 * Time: 06.08
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignPartnerDto {
    private String fullName;
    private String nationality;
    private String passportNumber;
    private String personCitizenshipNumber;
    private String email;
    private String contactNumber;
    private LocalDate dateOfBirth;
    private FileDto passportFile;
    private FileDto photoFile;
    private FileDto personCitizenshipFile;
}
