package id.my.hendisantika.digitalvitalregistrationsystem.integration;

import id.my.hendisantika.digitalvitalregistrationsystem.DigitalVitalRegistrationSystemApplication;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.CitizenStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.kafka.producer.KafkaNotificationProducer;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.DeliveryChannel;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationEvent;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationType;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Full-stack integration test with PostgreSQL, Redis, and Kafka containers
 */
@SpringBootTest(classes = DigitalVitalRegistrationSystemApplication.class)
@Testcontainers
class FullStackIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5-alpine3.22")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @Container
    static GenericContainer<?> redis = new GenericContainer<>(DockerImageName.parse("redis:8.2-alpine3.22"))
            .withExposedPorts(6379);

    @Container
    static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:7.4.0"));

    @MockBean
    private JavaMailSender javaMailSender;
    @Autowired
    private CitizenRepository citizenRepository;
    @Autowired
    private KafkaNotificationProducer kafkaNotificationProducer;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        // PostgreSQL configuration
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        // Redis configuration
        registry.add("spring.data.redis.host", redis::getHost);
        registry.add("spring.data.redis.port", () -> redis.getMappedPort(6379).toString());
        registry.add("spring.data.redis.timeout", () -> "2000");

        // Kafka configuration
        registry.add("spring.kafka.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.consumer.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.producer.bootstrap-servers", kafka::getBootstrapServers);
        registry.add("spring.kafka.consumer.group-id", () -> "test-group");
        registry.add("spring.kafka.consumer.auto-offset-reset", () -> "earliest");

        // Test configuration
        registry.add("jwt.secret", () -> "test-secret-for-full-integration-tests");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");

        // Disable mail for tests
        registry.add("spring.mail.host", () -> "");
        registry.add("spring.mail.port", () -> "");
    }

    @Test
    void allContainersAreRunning() {
        assertTrue(postgres.isRunning(), "PostgreSQL container should be running");
        assertTrue(redis.isRunning(), "Redis container should be running");
        assertTrue(kafka.isRunning(), "Kafka container should be running");
    }

    @Test
    void canInteractWithPostgreSQL() {
        // Test database operations
        Citizen citizen = createTestCitizen("PostgreSQL", "Test");
        Citizen saved = citizenRepository.save(citizen);

        assertNotNull(saved.getId());
        assertEquals("PostgreSQL", saved.getFirstName());

        // Test query methods
        Long count = citizenRepository.count();
        assertTrue(count > 0);
    }

    @Test
    void canInteractWithRedis() {
        // Test Redis operations
        String key = "test-key";
        String value = "test-value";

        stringRedisTemplate.opsForValue().set(key, value);
        String retrieved = stringRedisTemplate.opsForValue().get(key);

        assertEquals(value, retrieved);

        // Clean up
        stringRedisTemplate.delete(key);
    }

    @Test
    void canProduceToKafka() {
        // Create a test notification
        Citizen citizen = createTestCitizen("Kafka", "Producer");
        Citizen savedCitizen = citizenRepository.save(citizen);

        Notification notification = Notification.builder()
                .event(NotificationEvent.VERIFICATION_APPROVED)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email("kafka.test@example.com")
                .message("Test message for Kafka")
                .citizen(savedCitizen)
                .createdAt(LocalDateTime.now())
                .build();

        // This should not throw an exception
        assertDoesNotThrow(() -> {
            kafkaNotificationProducer.sendNotification(notification);
        });
    }

    @Test
    void testFullWorkflow() {
        // Test a complete workflow involving all services

        // 1. Save citizen to PostgreSQL
        Citizen citizen = createTestCitizen("Workflow", "Test");
        Citizen savedCitizen = citizenRepository.save(citizen);
        assertNotNull(savedCitizen.getId());

        // 2. Store some data in Redis
        String cacheKey = "citizen:" + savedCitizen.getId();
        String cacheValue = savedCitizen.getFirstName() + " " + savedCitizen.getLastName();
        stringRedisTemplate.opsForValue().set(cacheKey, cacheValue);

        // 3. Send notification via Kafka
        Notification notification = Notification.builder()
                .event(NotificationEvent.VERIFICATION_APPROVED)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(savedCitizen.getUserEmail())
                .message("Welcome " + savedCitizen.getFirstName() + "!")
                .citizen(savedCitizen)
                .createdAt(LocalDateTime.now())
                .build();

        assertDoesNotThrow(() -> kafkaNotificationProducer.sendNotification(notification));

        // 4. Verify Redis cache
        String cachedValue = stringRedisTemplate.opsForValue().get(cacheKey);
        assertEquals(cacheValue, cachedValue);

        // 5. Verify database state
        Citizen retrievedCitizen = citizenRepository.findById(savedCitizen.getId()).orElse(null);
        assertNotNull(retrievedCitizen);
        assertEquals("Workflow", retrievedCitizen.getFirstName());

        // Clean up Redis
        stringRedisTemplate.delete(cacheKey);
    }

    private Citizen createTestCitizen(String firstName, String lastName) {
        long timestamp = System.currentTimeMillis();
        return Citizen.builder()
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(LocalDate.of(1985, 6, 15))
                .gender(Gender.MALE)
                .citizenshipNumber("TEST" + timestamp)
                .userEmail(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@testcontainers.com")
                .phoneNo(timestamp % 9000000000L + 1000000000L) // Generate unique phone numbers
                .district("Container District")
                .municipality("Container City")
                .status(CitizenStatus.PENDING)
                .userEmail(firstName.toLowerCase() + "." + lastName.toLowerCase() + "@testcontainers.com")
                .createdAt(LocalDate.now())
                .build();
    }
}