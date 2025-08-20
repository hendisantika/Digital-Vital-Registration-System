package id.my.hendisantika.digitalvitalregistrationsystem.kafka.consumer;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.email.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.03
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaNotificationConsumer {
    private final EmailService emailService;
    private final NotificationRepository notificationRepository;
    private final CertificateFileRepository certificateFileRepository;

}
