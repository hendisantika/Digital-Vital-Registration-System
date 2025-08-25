package id.my.hendisantika.digitalvitalregistrationsystem.config;

import id.my.hendisantika.digitalvitalregistrationsystem.email.service.EmailService;
import id.my.hendisantika.digitalvitalregistrationsystem.kafka.producer.KafkaNotificationProducer;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.Mockito.mock;

/**
 * Test configuration that provides mock implementations
 * of external service dependencies for unit tests
 */
@TestConfiguration
@Slf4j
public class TestConfig {

    @Bean
    @Primary
    public KafkaTemplate<String, Notification> kafkaTemplate() {
        return mock(KafkaTemplate.class);
    }

    @Bean
    @Primary
    public JavaMailSender javaMailSender() {
        return mock(JavaMailSender.class);
    }

    @Bean
    @Primary
    public KafkaNotificationProducer kafkaNotificationProducer() {
        return new KafkaNotificationProducer(kafkaTemplate()) {
            @Override
            public void sendNotification(Notification notification) {
                log.info("Mock: Kafka notification sent for notification ID: {}",
                        notification != null ? notification.getId() : "null");
            }
        };
    }

    @Bean
    @Primary
    public EmailService emailService() {
        return new EmailService(javaMailSender()) {
            @Override
            public void sendEmail(String to, String subject, String htmlContent) {
                log.info("Mock: Email sent to: {}, Subject: {}", to, subject);
            }

            @Override
            public void sendEmailWithAttachment(String to, String subject, String htmlContent, byte[] attachmentData) {
                log.info("Mock: Email with attachment sent to: {}, Subject: {}", to, subject);
            }
        };
    }
}