package net.asiedlecki.auth.server.demo.AuthServerDemo.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;
import java.util.stream.Collectors;

@Configuration
@ConfigurationProperties(prefix = "test-users")
@Getter
@Setter
public class UsersConfig {

    private List<TestUser> users;

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        List<UserDetails> userDetailsList = users.stream()
                .map(u -> User.builder()
                        .username(u.getUsername())
                        .password(passwordEncoder.encode(u.getPassword()))
                        .roles("USER")
                        .build()
                ).collect(Collectors.toList());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Getter
    @Setter
    public static class TestUser {
        private String username;
        private String password;
        private String email;
        private String firstName;
        private String lastName;
    }
}
