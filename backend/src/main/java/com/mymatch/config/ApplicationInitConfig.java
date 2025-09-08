package com.mymatch.config;

import com.mymatch.entity.Role;
import com.mymatch.entity.User;
import com.mymatch.enums.RoleType;
import com.mymatch.repository.RoleRepository;
import com.mymatch.repository.UserRepository;
import lombok.experimental.NonFinal;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationInitConfig {
    PasswordEncoder passwordEncoder;
    @NonFinal
    @Value("${app.admin.username:admin}")
    protected String adminUsername;
    @NonFinal
    @Value("${app.admin.password:admin}")
    protected String adminPassword;
    private static final Map<RoleType, String> DESCRIPTIONS = new EnumMap<>(RoleType.class);

    static {
        DESCRIPTIONS.put(RoleType.STUDENT, "Student user for reviewing lecturers");
        DESCRIPTIONS.put(RoleType.MANAGER, "Manager for administering the review system");
    }
    @Bean
    @ConditionalOnProperty(
            prefix = "spring.datasource",
            name = "driver-class-name",
            havingValue = "com.mysql.cj.jdbc.Driver")
    public ApplicationRunner initializer(UserRepository userRepo, RoleRepository roleRepo) {
        return new ApplicationRunner() {
            @Override
            @Transactional
            public void run(ApplicationArguments args) {
                log.info("-- Initializing default roles and admin user in dev environment for MyMatch Review Giảng Viên --");

                for (RoleType type : RoleType.values()) {
                    if (!roleRepo.existsByName(type)) {
                        Role r = Role.builder()
                                .name(type)
                                .description(DESCRIPTIONS.get(type))
                                .build();
                        roleRepo.save(r);
                        log.info("Created role: {}", type);
                    }
                }

                Optional<User> adminOpt = userRepo.findByUsername(adminUsername);
                if (adminOpt.isEmpty()) {
                    Role managerRole = roleRepo.findByName(RoleType.MANAGER)
                            .orElseThrow(() -> new IllegalStateException("Manager role not found"));

                    User admin = User.builder()
                            .username(adminUsername)
                            .password(passwordEncoder.encode(adminPassword))
                            .role(managerRole)
                            .build();
                    userRepo.save(admin);
                    log.warn(
                            "Default admin created (username='{}', password='{}'), please change password immediately",
                            adminUsername,
                            adminPassword);
                }

                log.info("-- Application initialization completed --");
            }
        };
    }
}
