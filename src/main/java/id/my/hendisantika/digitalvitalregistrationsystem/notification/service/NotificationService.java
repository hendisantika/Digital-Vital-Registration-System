package id.my.hendisantika.digitalvitalregistrationsystem.notification.service;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.kafka.producer.KafkaNotificationProducer;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    public Notification sendAndDispatch(Notification notification) {
        log.info("Preparing to send notification: {}", notification);

        Long citizenId = notification.getCitizen() != null ? notification.getCitizen().getId() : null;
        if (citizenId == null) {
            throw new RuntimeException("Citizen is missing in the notification.");
        }

        // ✅ Fetch fully managed Citizen entity
        var citizen = citizenRepository.findById(citizenId)
                .orElseThrow(() -> new RuntimeException("Citizen not found with id: " + citizenId));

        Notification.NotificationBuilder builder = Notification.builder()
                .citizen(citizen) // ✅ use managed entity
                .event(notification.getEvent())
                .channel(notification.getChannel())
                .message(notification.getMessage())
                .email(notification.getEmail())
                .status(NotificationStatus.PENDING)
                .createdAt(notification.getCreatedAt() != null ? notification.getCreatedAt() : LocalDateTime.now())
                .type(notification.getType())
                .readStatus(Boolean.FALSE)
                .certificateId(notification.getCertificateId())
                .sentAt(LocalDateTime.now());

        if (notification.getCertificate() != null && notification.getCertificate().getId() != null) {
            certificateFileRepository.findById(notification.getCertificate().getId())
                    .ifPresent(builder::certificate);

        }
        Notification savedNotification = notificationRepository.save(builder.build());

        // Copy the ID back to the original notification for test purposes
        notification.setId(savedNotification.getId());

        kafkaNotificationProducer.sendNotification(savedNotification);
        log.info("Notification sent to Kafka and saved to DB: {}", savedNotification);

        return savedNotification;
    }
}
