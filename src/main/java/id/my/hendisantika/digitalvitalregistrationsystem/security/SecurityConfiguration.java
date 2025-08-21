package id.my.hendisantika.digitalvitalregistrationsystem.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Created by IntelliJ IDEA.
 * Project : Digital-Vital-Registration-System
 * User: hendisantika
 * Link: s.id/hendisantika
 * Email: hendisantika@yahoo.co.id
 * Telegram : @hendisantika34
 * Date: 22/08/25
 * Time: 05.27
 * To change this template use File | Settings | File Templates.
 */
@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                //.httpBasic(httpBasic->httpBasic.disable())
                .httpBasic(AbstractHttpConfigurer::disable)
                //.csrf(csrf-> csrf.disable())
                .csrf(AbstractHttpConfigurer::disable)
                .build();
    }
}
