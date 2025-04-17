package com.example.demo.banque.controller;

import com.example.demo.banque.model.Compte;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BanqueController {

    private List<Compte> comptes = new ArrayList<>();

    public BanqueController() {
        comptes.add(new Compte(1,"Ahmed", 1500.0));
        comptes.add(new Compte(2,"Ayoub", 2300.5));
        comptes.add(new Compte(3,"Chourouk", 800.75));
    }

    @GetMapping("/comptes")
    public String listeComptes(Model model) {
        System.out.println("✅ Accès à /comptes réussi !");
        model.addAttribute("comptes", comptes);
        return "listeComptes";
    }

    
    @GetMapping("/ajouter")
    public String formulaireAjout(Model model) {
        model.addAttribute("compte", new Compte());
        return "ajouterCompte";
    }

    @PostMapping("/ajouter")
    public String ajouterCompte(@ModelAttribute Compte compte) {
        comptes.add(compte);
        return "redirect:/comptes";
    }

    @GetMapping("/details/{id}")
    public String detailCompte(@PathVariable int id, Model model) {
        for (Compte c : comptes) {
            if (c.getId() == id) {
                model.addAttribute("compte", c);
                return "detailsCompte";
            }
        }
        return "redirect:/comptes";
    }

    @PostMapping("/depot/{id}")
    public String depot(@PathVariable int id, @RequestParam double montant) {
        for (Compte c : comptes) {
            if (c.getId() == id) {
                c.depot(montant);
                break;
            }
        }
        return "redirect:/details/" + id;
    }

    @PostMapping("/retrait/{id}")
    public String retrait(@PathVariable int id, @RequestParam double montant, Model model) {
        for (Compte c : comptes) {
            if (c.getId() == id) {
                boolean success = c.retrait(montant);
                model.addAttribute("compte", c);
                model.addAttribute("message", success ? "Retrait effectué avec succès." : "Solde insuffisant pour ce retrait.");
                return "detailsCompte";
            }
        }
        return "redirect:/comptes";
    }
   
    

    @GetMapping("/")
    public String index() {
        return "redirect:/comptes"; // Redirige directement vers la page des comptes
    }
}
