package com.example.demo.banque.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "compte")
public class Compte {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titulaire;

    private double solde;

    // Constructeurs
    public Compte() {}

    public Compte(String titulaire, double solde) {
        this.titulaire = titulaire;
        this.solde = solde;
    }

    // Getters & setters
    public int getId() { return id; }

    public String getTitulaire() { return titulaire; }

    public void setTitulaire(String titulaire) { this.titulaire = titulaire; }

    public double getSolde() { return solde; }

    public void setSolde(double solde) { this.solde = solde; }

    // Méthodes utiles
    public void depot(double montant) {
        if (montant > 0) {
            solde += montant;
        }
    }

    public boolean retrait(double montant) {
        if (montant > 0 && montant <= solde) {
            solde -= montant;
            return true;
        }
        return false;
    }
}



    
