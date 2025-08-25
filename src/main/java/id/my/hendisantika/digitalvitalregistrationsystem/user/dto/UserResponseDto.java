package id.my.hendisantika.digitalvitalregistrationsystem.user.dto;

import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
 * Date: 26/08/25
 * Time: 06.06
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseDto {
    private Long id;
    private String email;
    private String jwtToken;
    @Enumerated(EnumType.STRING)
    private Role role;
    private LocalDateTime createdAt;
    private String department;
    private String municipality;
}
