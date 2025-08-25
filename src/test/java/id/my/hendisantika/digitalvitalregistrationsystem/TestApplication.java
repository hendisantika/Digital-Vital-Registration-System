package id.my.hendisantika.digitalvitalregistrationsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;

/**
 * Test application that excludes Kafka components for unit testing
 */
@SpringBootApplication(exclude = {KafkaAutoConfiguration.class})
@ComponentScan(
        basePackages = "id.my.hendisantika.digitalvitalregistrationsystem",
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.REGEX,
                        pattern = "id\\.my\\.hendisantika\\.digitalvitalregistrationsystem\\.kafka\\..*"
                )
        }
)
public class TestApplication {
    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }
}