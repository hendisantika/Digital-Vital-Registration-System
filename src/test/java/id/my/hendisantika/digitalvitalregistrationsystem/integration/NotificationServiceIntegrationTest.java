package id.my.hendisantika.digitalvitalregistrationsystem.integration;

import id.my.hendisantika.digitalvitalregistrationsystem.TestApplication;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.CitizenStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.DeliveryChannel;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationEvent;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.enums.NotificationType;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.model.Notification;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.repository.NotificationRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.notification.service.NotificationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test for NotificationService with real database
 */
@SpringBootTest(classes = TestApplication.class)
@Testcontainers
class NotificationServiceIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5-alpine3.22")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @MockBean
    private StringRedisTemplate stringRedisTemplate;

    @MockBean
    private JavaMailSender javaMailSender;
    @Autowired
    private NotificationService notificationService;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private CitizenRepository citizenRepository;
    private Citizen testCitizen;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        // Disable external services for this test
        registry.add("spring.kafka.consumer.bootstrap-servers", () -> "");
        registry.add("spring.kafka.producer.bootstrap-servers", () -> "");
        registry.add("spring.kafka.bootstrap-servers", () -> "");
        registry.add("spring.data.redis.host", () -> "localhost");
        registry.add("spring.data.redis.port", () -> "6379");
        registry.add("spring.cache.type", () -> "none");
        registry.add("jwt.secret", () -> "test-secret-for-notification-integration");
    }

    @BeforeEach
    void setUp() {
        // Clean up database
        notificationRepository.deleteAll();
        citizenRepository.deleteAll();

        // Create test citizen
        testCitizen = Citizen.builder()
                .firstName("Test")
                .lastName("Citizen")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .citizenshipNumber("TEST123456789")
                .userEmail("test.citizen@example.com")
                .phoneNo(1234567890L)
                .district("Test District")
                .municipality("Test City")
                .status(CitizenStatus.PENDING)
                .userEmail("test.citizen@example.com")
                .createdAt(LocalDate.now())
                .build();

        testCitizen = citizenRepository.save(testCitizen);
    }

    @Test
    void canCreateAndStoreNotification() {
        // Given
        Notification notification = Notification.builder()
                .event(NotificationEvent.VERIFICATION_APPROVED)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(testCitizen.getUserEmail())
                .message("Your application has been approved!")
                .citizen(testCitizen)
                .status(NotificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        // When
        notificationService.sendAndDispatch(notification);

        // Then
        assertNotNull(notification.getId(), "Notification should have been saved with an ID");

        // Verify it was persisted
        Notification savedNotification = notificationRepository.findById(notification.getId()).orElse(null);
        assertNotNull(savedNotification, "Notification should be persisted in database");
        assertEquals(NotificationEvent.VERIFICATION_APPROVED, savedNotification.getEvent());
        assertEquals(testCitizen.getId(), savedNotification.getCitizen().getId());
    }

    @Test
    void canRetrieveNotificationsByCitizen() {
        // Given - create multiple notifications for the test citizen
        Notification notification1 = createTestNotification(NotificationEvent.VERIFICATION_APPROVED, "Approved!");
        Notification notification2 = createTestNotification(NotificationEvent.VERIFICATION_REJECTED, "Rejected!");

        notificationService.sendAndDispatch(notification1);
        notificationService.sendAndDispatch(notification2);

        // When
        var notifications = notificationRepository.findAll();

        // Then
        assertTrue(notifications.size() >= 2, "Should have at least 2 notifications");

        // Verify both notifications are associated with our test citizen
        notifications.forEach(notification -> {
            assertNotNull(notification.getCitizen());
            assertEquals(testCitizen.getId(), notification.getCitizen().getId());
        });
    }

    @Test
    void notificationHasCorrectTimestamp() {
        // Given
        LocalDateTime beforeCreation = LocalDateTime.now().minusSeconds(1);

        Notification notification = createTestNotification(NotificationEvent.REVIEWING, "Under review");

        // When
        notificationService.sendAndDispatch(notification);

        LocalDateTime afterCreation = LocalDateTime.now().plusSeconds(1);

        // Then
        assertNotNull(notification.getCreatedAt());
        assertTrue(notification.getCreatedAt().isAfter(beforeCreation) || notification.getCreatedAt().isEqual(beforeCreation));
        assertTrue(notification.getCreatedAt().isBefore(afterCreation) || notification.getCreatedAt().isEqual(afterCreation));
    }

    private Notification createTestNotification(NotificationEvent event, String message) {
        return Notification.builder()
                .event(event)
                .channel(DeliveryChannel.EMAIL)
                .type(NotificationType.EMAIL)
                .email(testCitizen.getUserEmail())
                .message(message)
                .citizen(testCitizen)
                .status(NotificationStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
    }
}