package com.example.demo.banque.model;

public class Compte {
    private static int compteur = 0;

    private int id;
    private String titulaire;
    private double solde;

    public Compte() {
        this.id = ++compteur;
    }

    public Compte(String titulaire, double solde) {
        this.id = ++compteur;
        this.titulaire = titulaire;
        this.solde = solde;
    }

   
    public Compte(int id, String titulaire, double solde) {
        this.id = id;
        this.titulaire = titulaire;
        this.solde = solde;
        if (id > compteur) {
            compteur = id; // Pour éviter des doublons d’ID plus tard
        }
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


    
