package id.my.hendisantika.digitalvitalregistrationsystem.whatsapp.service;

import id.my.hendisantika.digitalvitalregistrationsystem.whatsapp.properties.WhatsappProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
