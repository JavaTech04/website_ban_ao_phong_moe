/*
 * Author: Nong Hoang Vu || JavaTech
 * Facebook:https://facebook.com/NongHoangVu04
 * Github: https://github.com/JavaTech04
 * Youtube: https://www.youtube.com/@javatech04/?sub_confirmation=1
 */
package sd79.configuration;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import sd79.model.User;
import sd79.repositories.auth.RoleRepository;
import sd79.repositories.auth.UserRepository;

import java.time.ZoneId;
import java.util.TimeZone;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Slf4j
public class ApplicationInitConfig {

    final PasswordEncoder passwordEncoder;

    final RoleRepository roleRepository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Value("${spring.authentication.username}")
    String username;

    @Value("${spring.authentication.password}")
    String password;

    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
            logConfig();
            setDefaultUser(userRepository);
            kafkaTemplate.send("log-message", "Kafka has been connected");
        };
    }

    private void setDefaultUser(UserRepository userRepository) {
        User user = userRepository.findById(1L).orElse(null);
        assert user != null;
        user.setIsLocked(false);
        user.setIsEnabled(false);
        user.setIsDeleted(false);
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(this.roleRepository.findById(1).orElse(null));
        userRepository.save(user);
    }

    private void logConfig() {
        log.info("System Default TimeZone: {}", TimeZone.getDefault().getID());
        log.info("System Default ZoneId: {}", ZoneId.systemDefault());
    }

    @KafkaListener(topics = "log-message")
    public void logMessage(String message) {
        log.info(message);
    }
}
