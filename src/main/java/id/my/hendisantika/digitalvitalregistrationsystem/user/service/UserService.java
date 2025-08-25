package id.my.hendisantika.digitalvitalregistrationsystem.user.service;

import id.my.hendisantika.digitalvitalregistrationsystem.jwt.utils.JwtUtil;
import id.my.hendisantika.digitalvitalregistrationsystem.user.mapper.UserMapper;
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
}
