package id.my.hendisantika.digitalvitalregistrationsystem.user.service;

import id.my.hendisantika.digitalvitalregistrationsystem.jwt.utils.JwtUtil;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.enums.Status;
import id.my.hendisantika.digitalvitalregistrationsystem.user.dto.UserRequestDto;
import id.my.hendisantika.digitalvitalregistrationsystem.user.dto.UserResponseDto;
import id.my.hendisantika.digitalvitalregistrationsystem.user.mapper.UserMapper;
import id.my.hendisantika.digitalvitalregistrationsystem.user.model.User;
import id.my.hendisantika.digitalvitalregistrationsystem.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 26/08/25
 * Time: 06.08
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserResponseDto registerUser(UserRequestDto userRequestDto) {
        if (userRepository.existsByEmail(userRequestDto.getEmail())) {

            throw new RuntimeException("Email already in use");
        }
        User user = userMapper.toEntity(userRequestDto);

        user.setJwtToken(jwtUtil.generateToken(user.getEmail()));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    /*public UserResponseDto loginUser(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(()->new RuntimeException("User not found"));

        if(!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }
        String token = jwtUtil.generateToken(user.getEmail());
        user.setJwtToken(token);
        userRepository.save(user);
        return userMapper.toDto(user);
    }*/

    public UserResponseDto loginUser(UserRequestDto userRequestDto) {
        User user = userRepository.findByEmail(userRequestDto.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        // ✅ Check password match
        if (!passwordEncoder.matches(userRequestDto.getPassword(), user.getPassword())) {
            throw new RuntimeException("Wrong password");
        }

        // ✅ Check if it's a staff account and status is ACTIVE
        if (user.getStaffUser() != null) {
            if (user.getStaffUser().getStatus() != Status.ACTIVE) {
                throw new RuntimeException("Your account is inactive. Please contact the system administrator.");
            }
        }

        // ✅ Generate new JWT token
        String token = jwtUtil.generateToken(user.getEmail());
        user.setJwtToken(token);
        userRepository.save(user);

        return userMapper.toDto(user);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserResponseDto findByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("User not found"));
        return userMapper.toDto(user);
    }
}
