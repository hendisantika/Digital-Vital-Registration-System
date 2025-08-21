package id.my.hendisantika.digitalvitalregistrationsystem.whatsapp.service;

import id.my.hendisantika.digitalvitalregistrationsystem.whatsapp.properties.WhatsappProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.36
 * To change this template use File | Settings | File Templates.
 */
@Service
@RequiredArgsConstructor
public class WhatsappService {
    private final WhatsappProperties whatsappProperties;
    private final String WHATSAPP_API_URL = "https://graph.facebook.com/v19.0/";

    public void sendOtp(String recipientPhoneNumber, String otp) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(whatsappProperties.getAccessToken());
        headers.set("Content-Type", "application/json");
        Map<String, Object> body = Map.of(
                "messaging_product", "whatsapp",
                "to", recipientPhoneNumber,
                "type", "template",
                "template", Map.of(
                        "name", "otp_template",
                        "language", Map.of("code", "en_US"),
                        "components", List.of(
                                Map.of(
                                        "type", "body",
                                        "parameters", List.of(
                                                Map.of("type", "text", "text", otp)
                                        )
                                )
                        )
                )
        );
        String url = WHATSAPP_API_URL + whatsappProperties.getPhoneNumberId() + "/messages";
        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);

        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
            System.out.println("WhatsApp API response: " + response.getBody());
        } catch (Exception e) {
            System.err.println("Error sending WhatsApp message: " + e.getMessage());
        }
    }
}
