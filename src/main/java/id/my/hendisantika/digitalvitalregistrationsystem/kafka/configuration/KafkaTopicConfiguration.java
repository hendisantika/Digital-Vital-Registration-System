package id.my.hendisantika.digitalvitalregistrationsystem.kafka.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 21/08/25
 * Time: 06.02
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class KafkaTopicConfiguration {
    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder
                .name("notification-topic")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
