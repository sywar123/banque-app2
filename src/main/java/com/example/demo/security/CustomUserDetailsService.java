package com.example.demo.security;




import com.example.demo.banque.model.Utilisateur;
import com.example.demo.banque.repository.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collections;
@Service
public class CustomUserDetailsService implements UserDetailsService {

   @Autowired
   private UtilisateurRepository utilisateurRepository;

   @Override
   public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
       if (utilisateur == null) {
           throw new UsernameNotFoundException("Utilisateur non trouvé");
       }

       // Ici, on ajoute le préfixe "ROLE_" au rôle stocké s’il n’est pas encore dans la base
       String roleWithPrefix = utilisateur.getRole().startsWith("ROLE_")
           ? utilisateur.getRole()
           : "ROLE_" + utilisateur.getRole();

       return new User(
           utilisateur.getUsername(),
           utilisateur.getPassword(),
           Collections.singleton(new SimpleGrantedAuthority(roleWithPrefix))
       );
       
   }
}








