package id.my.hendisantika.digitalvitalregistrationsystem.kafka.producer;

import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.04
 * To change this template use File | Settings | File Templates.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaNotificationProducer {
    private final KafkaTemplate<String, Notification> kafkaTemplate;

    public void sendNotification(Notification notification) {
        kafkaTemplate.send("notification-topic", notification);
        log.info("Notification sent to topic {}", notification.getId());
    }
}
