package com.example.ediary.init;

import com.example.ediary.models.Role;
import com.example.ediary.models.User;
import com.example.ediary.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminInitializer(UserRepository userRepository,
                            PasswordEncoder passwordEncoder
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (userRepository.findFirstByRole(Role.TEACHER).isEmpty()) {
            var teacher = User.builder()
                    .name("teacher")
                    .username("teacher")
                    .password(passwordEncoder.encode("password"))
                    .teamName("teacher")
                    .role(Role.TEACHER)
                    .approved(true)
                    .build();
            userRepository.save(teacher);
        }
    }
}