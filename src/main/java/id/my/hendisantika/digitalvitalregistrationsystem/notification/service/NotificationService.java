package id.my.hendisantika.digitalvitalregistrationsystem.notification.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.kafka.producer.KafkaNotificationProducer;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.repository.NotificationRepository;
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
 * Date: 22/08/25
 * Time: 05.24
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private final CitizenRepository citizenRepository;
    private final CertificateFileRepository certificateFileRepository;
    private final KafkaNotificationProducer kafkaNotificationProducer;
}
