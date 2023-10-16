package com.resid.huseynov;

import com.resid.huseynov.entity.Role;
import com.resid.huseynov.entity.User;
import com.resid.huseynov.repository.RoleRepository;
import com.resid.huseynov.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootApplication
@RequiredArgsConstructor
public class HuseynovApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;


    public static void main(String[] args) {
        SpringApplication.run(HuseynovApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        Role roleAdmin = roleRepository.save(Role.builder().authority("ROLE_ADMIN").build());
        Role roleUser = roleRepository.save(Role.builder().authority("ROLE_USER").build());

        userRepository.save(User.builder()
                .username("resid")
                .password(bCryptPasswordEncoder.encode("residdi"))
                .isEnabled(true)
                .authorities(List.of(roleAdmin))
                .build());

        userRepository.save(User.builder()
                .username("elsad")
                .password(bCryptPasswordEncoder.encode("elsaddi"))
                .isEnabled(true)
                .authorities(List.of(roleUser))
                .build());
    }

}
