package com.example.demo.component;

import com.example.demo.entity.User;
import com.example.demo.entity.enums.RoleName;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Value("${spring.datasource.initialization-mode}")
    private String initialMode;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (initialMode.equals("always")) {

            userRepository.save(new User(
                    "Temubek",

                    "Zarifov Temurbek",
                    passwordEncoder.encode("password"),
                    roleRepository.findAll()));

                    userRepository.save(new User(
                            "giyos",

                            "Zarifov",
                            passwordEncoder.encode("password1"),
                            roleRepository.findAllByName(RoleName.ROLE_ADMIN)));


        }
    }
}
