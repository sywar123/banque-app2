package com.example.demo.security;

import com.example.demo.banque.model.Utilisateur;
import com.example.demo.banque.repository.UtilisateurRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(UtilisateurRepository utilisateurRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            if (utilisateurRepository.findByUsername("admin") == null) {
                Utilisateur user = new Utilisateur();
                user.setUsername("admin");
                user.setPassword(passwordEncoder.encode("1234"));
                user.setRole("ROLE_USER");
                utilisateurRepository.save(user);
                System.out.println(" Utilisateur admin ajouté !");
            } else {
                System.out.println(" Utilisateur admin déjà existant.");
            }
        };
    }
}
