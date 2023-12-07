package project.vilsoncake.Flowt.config;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import project.vilsoncake.Flowt.entity.UserEntity;
import project.vilsoncake.Flowt.property.AdminProperties;
import project.vilsoncake.Flowt.repository.UserRepository;

import static project.vilsoncake.Flowt.entity.enumerated.Role.ADMIN;

@Configuration
@Slf4j
@RequiredArgsConstructor
public class InitAdminUserConfig {

    private final AdminProperties adminProperties;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initAdminUser() {
        if (userRepository.existsUserByUsername(adminProperties.getUsername())) {
            return;
        }

        UserEntity admin = new UserEntity();
        admin.setUsername(adminProperties.getUsername());
        admin.setEmail(adminProperties.getEmail());
        admin.setPassword(passwordEncoder.encode(adminProperties.getPassword()));
        admin.getRoles().add(ADMIN);
        admin.setEmailVerify(true);
        admin.setArtistVerify(true);
        userRepository.save(admin);

        log.info("Admin user has been initialized");
    }
}
