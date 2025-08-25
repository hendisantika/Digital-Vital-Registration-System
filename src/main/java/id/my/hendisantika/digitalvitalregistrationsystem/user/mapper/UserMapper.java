package id.my.hendisantika.digitalvitalregistrationsystem.user.mapper;

import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import id.my.hendisantika.digitalvitalregistrationsystem.user.dto.UserRequestDto;
import id.my.hendisantika.digitalvitalregistrationsystem.user.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

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
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final BCryptPasswordEncoder passwordEncoder;

    // Converts incoming request DTO to User entity for saving
    public User toEntity(UserRequestDto userRequestDto) {
        return User.builder()
                .email(userRequestDto.getEmail())
                .password(passwordEncoder.encode(userRequestDto.getPassword()))
                .role(userRequestDto.getRole() != null ? userRequestDto.getRole() : Role.CITIZEN) // set from DTO or default
                .createdAt(LocalDateTime.now())
                .build();
    }
}
