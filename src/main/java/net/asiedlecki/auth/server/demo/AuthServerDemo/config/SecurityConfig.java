package net.asiedlecki.auth.server.demo.AuthServerDemo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {

        List<String> names = List.of(
            "Adam", "Bogdan", "Kamil", "Marek", "Paweł", "Jan", "Michał", "Sławomir",
            "Dariusz", "Tomasz", "Rafał", "Krzysztof", "Agnieszka", "Katarzyna",
            "Anna", "Małgorzata", "Marta", "Olga", "Urszula", "Barbara", "Lucja",
            "Danuta", "Halina", "Zuzanna", "Irena", "Lech", "Maksymilian"
        );

        List<UserDetails> users = names.stream()
            .map(name -> User.builder()
                .username(name.toLowerCase())   // login
                .password(passwordEncoder.encode("password"))
                .roles("USER")
                .build()
            )
            .toList();

        return new InMemoryUserDetailsManager(users);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
