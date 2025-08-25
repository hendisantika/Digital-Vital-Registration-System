package id.my.hendisantika.digitalvitalregistrationsystem.staff.mapper;

import id.my.hendisantika.digitalvitalregistrationsystem.jwt.utils.JwtUtil;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.dto.StaffUserRequestDto;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.dto.StaffUserResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Status;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.model.StaffUser;
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
 * Date: 22/08/25
 * Time: 05.43
 * To change this template use File | Settings | File Templates.
 */
@Component
@RequiredArgsConstructor
public class StaffUserDtoMapper {
    public static StaffUserResponseDto staffUserResponseDto(StaffUser staffUser) {
        return StaffUserResponseDto.builder()
                .id(staffUser.getId())
                .fullName(staffUser.getFullName())
                .email(staffUser.getEmail())
                .role(staffUser.getRole())
                .status(staffUser.getStatus())
                .department(staffUser.getDepartment())
                .designation(staffUser.getDesignation())
                .phoneNumber(staffUser.getPhoneNumber())
                .createdAt(staffUser.getCreatedAt())
                .addedBy(staffUser.getAddedBy())
                .municipality(staffUser.getMunicipality())
                .district(staffUser.getDistrict())
                .build();
    }

    public static StaffUser toEntityDto(StaffUserRequestDto staffUserRequestDto, StaffUser addedBy, BCryptPasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        StaffUser savedStaff = StaffUser.builder()
                .fullName(staffUserRequestDto.getFullName())
                .email(staffUserRequestDto.getEmail())
                .password(passwordEncoder.encode(staffUserRequestDto.getPassword()))
                .department(staffUserRequestDto.getDepartment())
                .designation(staffUserRequestDto.getDesignation())
                .phoneNumber(staffUserRequestDto.getPhoneNumber())
                .role(staffUserRequestDto.getRole() != null ? staffUserRequestDto.getRole() : Role.ADMIN)
                .createdAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .addedBy(staffUserRequestDto.getAddedBy())

                .municipality(staffUserRequestDto.getMunicipality())
                .district(staffUserRequestDto.getDistrict())

                .build();


        User user = User.builder()
                .email(savedStaff.getEmail())
                .password(savedStaff.getPassword())
                .role(savedStaff.getRole())
                .staffUser(savedStaff)
                .createdAt(LocalDateTime.now())
                .jwtToken(jwtUtil.generateToken(savedStaff.getEmail()))
                .build();
        savedStaff.setUser(user);
        return savedStaff;


    }
}
