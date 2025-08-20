package id.my.hendisantika.digitalvitalregistrationsystem.email.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.email.service.OTPService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @PostMapping("/send")
    public ResponseEntity<Map<String, String>> sendOTP(@RequestParam String email) {
        try {
            otpService.generateAndSendOTP(email);
            return ResponseEntity.ok(Map.of("message", "OTP sent to " + email));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyOTP(@RequestParam String email, @RequestParam String otp) {
        boolean isVerified = otpService.verifyOTP(email, otp);
        if (isVerified) {
            String token = jwtUtil.generateToken(email.trim().toLowerCase());
            return ResponseEntity.ok(Map.of("message", "OTP verified", "token", token));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Invalid OTP."));
        }
    }
}
