package id.my.hendisantika.digitalvitalregistrationsystem.whatsapp.controller;

import id.my.hendisantika.digitalvitalregistrationsystem.whatsapp.service.WhatsappService;
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
 * Date: 22/08/25
 * Time: 05.37
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/whatsapp/otp")
public class OtpController {

    private final WhatsappService whatsappService;

    // Validate Nepal phone number
    public boolean isValidNepalPhoneNumber(String number) {
        return number != null && number.matches("^\\+9779[78]\\d{8}$");
    }

}
