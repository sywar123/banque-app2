package com.example.demo.banque.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String type; // "DEPOT", "RETRAIT", "TRANSFERT"

    private double montant;

    private LocalDateTime date;

    @ManyToOne
    @JoinColumn(name = "compte_id")
    private Compte compte;

    public Transaction() {
        this.date = LocalDateTime.now();
    }

    public Transaction(String type, double montant, Compte compte) {
        this.type = type;
        this.montant = montant;
        this.compte = compte;
        this.date = LocalDateTime.now();
    }

    
    public int getId() { return id; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }
    public LocalDateTime getDate() { return date; }
    public void setDate(LocalDateTime date) { this.date = date; }
    public Compte getCompte() { return compte; }
    public void setCompte(Compte compte) { this.compte = compte; }
}

