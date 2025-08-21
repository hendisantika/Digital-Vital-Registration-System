package id.my.hendisantika.digitalvitalregistrationsystem.staff.dto;

import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Department;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.43
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffUserResponseDto {
    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private Role role;
    private String designation;
    private Department department;
    private String district;
    private String municipality;
    private Status status;
    private LocalDateTime createdAt;
    private String addedBy;
}
