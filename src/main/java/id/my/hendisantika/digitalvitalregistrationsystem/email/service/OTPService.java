package id.my.hendisantika.digitalvitalregistrationsystem.email.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 05.57
 * To change this template use File | Settings | File Templates.
 */
@Service
public class OTPService {
    private static final long OTP_EXPIRATION_MINUTES = 5;
    private static final long OTP_COOLDOWN_SECONDS = 60;
    private final Logger log = LoggerFactory.getLogger(OTPService.class);
    private final JavaMailSender mailSender;
    private final StringRedisTemplate redisTemplate;

}
