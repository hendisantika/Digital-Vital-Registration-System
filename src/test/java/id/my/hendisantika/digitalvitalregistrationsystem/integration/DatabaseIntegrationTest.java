package id.my.hendisantika.digitalvitalregistrationsystem.integration;

import id.my.hendisantika.digitalvitalregistrationsystem.TestApplication;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.CitizenStatus;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.enums.Gender;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.model.Citizen;
import id.my.hendisantika.digitalvitalregistrationsystem.citizen.repository.CitizenRepository;
import id.my.hendisantika.digitalvitalregistrationsystem.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Integration test using Testcontainers for PostgreSQL database
 */
@SpringBootTest(classes = TestApplication.class)
@Testcontainers
@Import(TestConfig.class)
class DatabaseIntegrationTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:17.5-alpine3.22")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    @MockBean
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private CitizenRepository citizenRepository;

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");

        // Disable external services
        registry.add("spring.kafka.consumer.bootstrap-servers", () -> "");
        registry.add("spring.kafka.producer.bootstrap-servers", () -> "");
        registry.add("spring.kafka.bootstrap-servers", () -> "");
        registry.add("spring.data.redis.host", () -> "localhost");
        registry.add("spring.data.redis.port", () -> "6379");
        registry.add("spring.cache.type", () -> "none");
        registry.add("jwt.secret", () -> "test-secret-for-integration-tests");
    }

    @Test
    void contextLoadsWithPostgreSQL() {
        assertTrue(postgres.isRunning(), "PostgreSQL container should be running");
        assertNotNull(citizenRepository, "CitizenRepository should be available");
    }

    @Test
    void canSaveAndRetrieveCitizen() {
        // Given
        Citizen citizen = Citizen.builder()
                .firstName("John")
                .lastName("Doe")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender(Gender.MALE)
                .citizenshipNumber("ID123456789")
                .userEmail("john.doe@test.com")
                .phoneNo(1234567890L)
                .district("Test District")
                .municipality("Test City")
                .status(CitizenStatus.PENDING)
                .userEmail("john.doe@test.com")
                .createdAt(LocalDate.now())
                .build();

        // When
        Citizen savedCitizen = citizenRepository.save(citizen);

        // Then
        assertNotNull(savedCitizen.getId());
        assertEquals("John", savedCitizen.getFirstName());
        assertEquals("Doe", savedCitizen.getLastName());
        assertEquals(Gender.MALE, savedCitizen.getGender());
        assertEquals(CitizenStatus.PENDING, savedCitizen.getStatus());

        // Verify we can retrieve it
        Citizen foundCitizen = citizenRepository.findById(savedCitizen.getId()).orElse(null);
        assertNotNull(foundCitizen);
        assertEquals("John", foundCitizen.getFirstName());
    }

    @Test
    void canCountCitizensByGender() {
        // Given - create test data
        citizenRepository.save(createTestCitizen("Male1", Gender.MALE));
        citizenRepository.save(createTestCitizen("Male2", Gender.MALE));
        citizenRepository.save(createTestCitizen("Female1", Gender.FEMALE));

        // When
        Long maleCount = citizenRepository.countByGender(Gender.MALE);
        Long femaleCount = citizenRepository.countByGender(Gender.FEMALE);

        // Then
        assertTrue(maleCount >= 2, "Should have at least 2 male citizens");
        assertTrue(femaleCount >= 1, "Should have at least 1 female citizen");
    }

    private Citizen createTestCitizen(String firstName, Gender gender) {
        long timestamp = System.currentTimeMillis();
        return Citizen.builder()
                .firstName(firstName)
                .lastName("TestLastName")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .gender(gender)
                .citizenshipNumber("ID" + timestamp)
                .userEmail(firstName.toLowerCase() + "@test.com")
                .phoneNo(timestamp % 9000000000L + 1000000000L) // Generate unique phone numbers
                .district("Test District")
                .municipality("Test City")
                .status(CitizenStatus.PENDING)
                .userEmail(firstName.toLowerCase() + "@test.com")
                .createdAt(LocalDate.now())
                .build();
    }
}