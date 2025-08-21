package id.my.hendisantika.digitalvitalregistrationsystem.staff.service;

import id.my.hendisantika.digitalvitalregistrationsystem.jwt.utils.JwtUtil;
import id.my.hendisantika.digitalvitalregistrationsystem.staff.repository.StaffUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

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
}
