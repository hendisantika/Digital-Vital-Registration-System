package id.my.hendisantika.digitalvitalregistrationsystem.staff.dto;

import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Department;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.42
 * To change this template use File | Settings | File Templates.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StaffUserRequestDto {
    private String fullName;
    private String email;
    private String password;
    private String phoneNumber;
    private String designation;
    private Department department;
    private String municipality;
    private String district;
    private String addedBy;
    private Role role;
}
