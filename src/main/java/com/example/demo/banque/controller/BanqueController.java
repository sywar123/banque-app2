package com.example.demo.banque.controller;

import com.example.demo.banque.model.Compte;
import com.example.demo.banque.model.Transaction;
import com.example.demo.banque.repository.CompteRepository;
import com.example.demo.banque.repository.TransactionRepository;
import com.example.demo.banque.service.EmailService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class BanqueController {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private EmailService emailService;

    @Autowired
    private CompteRepository compteRepository;

    @GetMapping("/comptes")
    public String listeComptes(Model model) {
        model.addAttribute("comptes", compteRepository.findAll());
        return "listeComptes";
    }

    @GetMapping("/ajouter")
    public String formulaireAjout(Model model) {
        model.addAttribute("compte", new Compte());
        
        return "ajouterCompte";
    }

    @PostMapping("/ajouter")
    public String ajouterCompte(@ModelAttribute Compte compte) {
        compteRepository.save(compte);
       emailService.envoyerEmail(compte.getEmail(), "Bienvenue", "Votre compte a bien été créé !");
            
        
        return "redirect:/comptes";
    }

    @GetMapping("/details/{id}")
    public String detailCompte(@PathVariable int id, Model model) {
        Optional<Compte> compteOpt = compteRepository.findById(id);
        if (compteOpt.isPresent()) {
            Compte compte = compteOpt.get();
            model.addAttribute("compte", compte);
            model.addAttribute("transactions", transactionRepository.findByCompteId(id));
            return "detailsCompte";
        }
        return "redirect:/comptes";
    }


    @PostMapping("/depot/{id}")
    public String depot(@PathVariable int id, @RequestParam double montant) {
        Optional<Compte> compteOpt = compteRepository.findById(id);
        if (compteOpt.isPresent()) {
            Compte c = compteOpt.get();
            c.depot(montant);
            compteRepository.save(c);
            transactionRepository.save(new Transaction("DEPOT", montant, c));
         // ✅ Notification email
            if (c.getEmail() != null) {
                emailService.envoyerEmail(
                    c.getEmail(),
                    "Notification de dépôt",
                    "Bonjour " + c.getTitulaire() + ",\nUn dépôt de " + montant + " a été effectué sur votre compte.\nNouveau solde : " + c.getSolde()
                );
            }
        
        }
        return "redirect:/details/" + id;
    }

    @PostMapping("/retrait/{id}")
    public String retrait(@PathVariable int id, @RequestParam double montant, Model model) {
        Optional<Compte> compteOpt = compteRepository.findById(id);
        if (compteOpt.isPresent()) {
            Compte c = compteOpt.get();
            boolean success = c.retrait(montant);
            if (success) {
                compteRepository.save(c);
                transactionRepository.save(new Transaction("RETRAIT", montant, c));
             // ✅ Notification email
                if (c.getEmail() != null) {
                    emailService.envoyerEmail(
                        c.getEmail(),
                        "Notification de retrait",
                        "Bonjour " + c.getTitulaire() + ",\nUn retrait de " + montant + " a été effectué sur votre compte.\nNouveau solde : " + c.getSolde()
                    );
                }
            }
            model.addAttribute("compte", c);
            model.addAttribute("message", success ? "Retrait effectué avec succès." : "Solde insuffisant pour ce retrait.");
            return "detailsCompte";
        }
        return "redirect:/comptes";
    }


    @GetMapping("/")
    public String index() {
        return "redirect:/comptes";
    }
    @GetMapping("/test-email")
    @ResponseBody // Retourne directement la réponse sans template
    public String testEmail() {
        try {
            // 1. Test basique
            emailService.envoyerEmail(
                "hibadwahem9@gmail.com", // Remplacez par VOTRE email
                "Test SMTP Urgent", 
                "Ceci est un test technique à " + LocalDateTime.now()
            );
            
            // 2. Vérification de la configuration
            JavaMailSenderImpl mailSender = (JavaMailSenderImpl) emailService.mailSender;
            return String.format(
                "Test envoyé à votre.email.reel@gmail.com<br>" +
                "Configuration SMTP:<br>" +
                "Host: %s<br>Port: %d<br>Username: %s<br>",
                mailSender.getHost(),
                mailSender.getPort(),
                mailSender.getUsername()
            );
            
        } catch (Exception e) {
            return "Échec d'envoi: " + e.getMessage();
        }
    }
}
