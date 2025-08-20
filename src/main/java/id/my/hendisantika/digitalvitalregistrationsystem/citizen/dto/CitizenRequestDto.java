package id.my.hendisantika.digitalvitalregistrationsystem.citizen.dto;

import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.CitizenStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.MarriageStatus;
import jakarta.validation.constraints.NotNull;
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
 * Time: 08.54
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CitizenRequestDto implements Serializable {
    private Long id;
    @NotNull(message = "Cannot be Null")
    private String firstName;
    @NotNull(message = "Cannot be Null")
    private String lastName;
    @NotNull(message = "Cannot be Null")
    private Gender gender;
    @NotNull(message = "Cannot be Null")
    private String nationality;
    @NotNull(message = "Cannot be Null")
    private String district;
    @NotNull(message = "Cannot be Null")
    private String municipality;
    @NotNull(message = "Cannot be Null")
    private int wardNo;
    @NotNull(message = "Cannot be Null")
    private String tole;
    private String fatherName;
    private String motherName;
    private String spouseName;
    private CitizenStatus status;

    private Long phoneNo;
    private String grandfatherName;
    private String grandmotherName;
    @NotNull(message = "Cannot be Null")
    private LocalDate dateOfBirth;
    private String middleName;
    private LocalDate verifiedDate = LocalDate.now();
    private LocalDate createdAt = LocalDate.now();
    private LocalDate updatedAt = LocalDate.now();
    private Long verifiedBy;
    private String citizenshipNumber;
    private MarriageStatus marriageStatus;
}
