package id.my.hendisantika.digitalvitalregistrationsystem.email.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.email.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 05.59
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/email/otp")
public class OTPController {
    private final OTPService otpService;
    private final JwtUtil jwtUtil;
}
