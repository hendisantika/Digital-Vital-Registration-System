package id.my.hendisantika.digitalvitalregistrationsystem.kafka.consumer;

import id.my.hendisantika.digitalvitalregistrationsystem.certificate.certificateFile.CertificateFile;
import id.my.hendisantika.digitalvitalregistrationsystem.certificate.repository.CertificateFileRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.email.service.EmailService;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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

    @KafkaListener(topics = "notification-topic", groupId = "notification-group")
    public void consume(Notification incomingNotification) {
        log.info("Received notification: {}", incomingNotification.getId());

        try {
            String subject = "Online Vital Registration System";

            CertificateFile certificateFile = null;
            if (incomingNotification.getCertificateId() != null) {
                certificateFile = certificateFileRepository
                        .findById(incomingNotification.getCertificateId())
                        .orElse(null);
            }

            if ("BOTH".equalsIgnoreCase(incomingNotification.getChannel().name())) {
                if (certificateFile != null) {
                    emailService.sendEmailWithAttachment(
                            incomingNotification.getEmail(),
                            subject,
                            incomingNotification.getMessage(),
                            certificateFile.getFileData()
                    );
                } else {
                    log.warn("No certificate file found for notification ID: {}", incomingNotification.getId());
                }
            }

            if ("EMAIL".equalsIgnoreCase(incomingNotification.getChannel().name())) {
                emailService.sendEmail(
                        incomingNotification.getEmail(),
                        subject,
                        incomingNotification.getMessage()
                );
            }

            notificationRepository.findById(incomingNotification.getId())
                    .ifPresent(savedNotification -> {
                        savedNotification.setStatus(NotificationStatus.SENT);
                        savedNotification.setSentAt(LocalDateTime.now());
                        notificationRepository.save(savedNotification);
                    });

        } catch (Exception e) {
            log.error("Failed to send email: {}", e.getMessage());
            notificationRepository.findById(incomingNotification.getId())
                    .ifPresent(savedNotification -> {
                        savedNotification.setStatus(NotificationStatus.FAILED);
                        notificationRepository.save(savedNotification);
                    });
        }
    }
}
