package id.my.hendisantika.digitalvitalregistrationsystem;

import id.my.hendisantika.digitalvitalregistrationsystem.config.TestConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("test")
@Import(TestConfig.class)
class DigitalVitalRegistrationSystemApplicationTests {

    @MockitoBean
    private StringRedisTemplate stringRedisTemplate;

    @Test
    void contextLoads() {
        // This test will pass if the Spring Boot context loads successfully
        // with H2 in-memory database and mocked external services
    }

}
