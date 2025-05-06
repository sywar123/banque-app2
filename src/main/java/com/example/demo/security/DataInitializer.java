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
            // Ajout de l'utilisateur "admin"
            if (utilisateurRepository.findByUsername("admin") == null) {
                Utilisateur admin = new Utilisateur();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("1234"));
                admin.setRole("ROLE_ADMIN");
                admin.setEmail("admin@banque.com");
                utilisateurRepository.save(admin);
                
                System.out.println("Utilisateur admin ajouté !");
            } else {
                System.out.println("Utilisateur admin déjà existant.");
            }

            // Ajout d 'un autre utilisateur 
            if (utilisateurRepository.findByUsername("Siwar") == null) {
                Utilisateur user = new Utilisateur();
                user.setUsername("Siwar");
                user.setPassword(passwordEncoder.encode("userpass"));
                user.setRole("ROLE_USER");
                user.setEmail("siwar@banque.com");
                utilisateurRepository.save(user);
                
                System.out.println("Utilisateur Siwar ajouté !");
            } else {
                System.out.println("Utilisateur Siwar déjà existant.");
            }
        };
    }
}

