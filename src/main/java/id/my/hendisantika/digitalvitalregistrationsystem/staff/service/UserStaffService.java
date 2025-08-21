package id.my.hendisantika.digitalvitalregistrationsystem.staff.service;

import id.my.hendisantika.digitalvitalregistrationsystem.jwt.utils.JwtUtil;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.dto.StaffUserRequestDto;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.dto.StaffUserResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Role;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Status;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.mapper.StaffUserDtoMapper;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.model.StaffUser;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.repository.StaffUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.46
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class UserStaffService {
    private final StaffUserRepository staffUserRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;


    public StaffUserResponseDto createSuperAdmin(StaffUserRequestDto staffUserRequestDto) {
        if (staffUserRepository.findByEmail(staffUserRequestDto.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists.");
        }

        // Create StaffUser
        StaffUser superAdmin = StaffUser.builder()
                .fullName(staffUserRequestDto.getFullName())
                .email(staffUserRequestDto.getEmail())
                .role(Role.SUPERADMIN)
                .department(staffUserRequestDto.getDepartment())
                .designation(staffUserRequestDto.getDesignation())
                .phoneNumber(staffUserRequestDto.getPhoneNumber())
                .password(passwordEncoder.encode(staffUserRequestDto.getPassword()))
                .createdAt(LocalDateTime.now())
                .district(staffUserRequestDto.getDistrict())
                .municipality(staffUserRequestDto.getMunicipality())
                .status(Status.ACTIVE)
                .addedBy(staffUserRequestDto.getAddedBy())
                .build();

        StaffUser savedStaff = staffUserRepository.save(superAdmin);

        // Create corresponding User
        String token = jwtUtil.generateToken(savedStaff.getEmail());

        User user = User.builder()
                .email(savedStaff.getEmail())
                .password(savedStaff.getPassword())
                .role(savedStaff.getRole())
                .staffUser(savedStaff)
                .createdAt(LocalDateTime.now())
                .jwtToken(token)
                .build();

        userRepository.save(user);

        return StaffUserDtoMapper.staffUserResponseDto(savedStaff);
    }

    public StaffUserResponseDto createStaff(StaffUserRequestDto staffUserRequestDto, StaffUser addedBy) {
        if (staffUserRepository.existsByEmail(staffUserRequestDto.getEmail())) {
            throw new RuntimeException("Email already exists.");
        }
        StaffUser staffUser = StaffUserDtoMapper.toEntityDto(staffUserRequestDto, addedBy, passwordEncoder, jwtUtil);
        StaffUser savedStaff = staffUserRepository.save(staffUser);

        return StaffUserDtoMapper.staffUserResponseDto(savedStaff);
    }

    public List<StaffUserResponseDto> getStaffByRole(Role role) {
        List<StaffUser> adminStaff = staffUserRepository.findByRole(role);
        return adminStaff.stream().map(StaffUserDtoMapper::staffUserResponseDto).collect(Collectors.toList());
    }
}
