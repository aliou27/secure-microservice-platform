package fr.ipsl.soa.commande_service.commandes;

import java.time.LocalDate;

public class Commande {
    private Long id;
    private Long produitId;
    private int quantite;
    private String statut; // "EN_COURS", "LIVREE", "ANNULEE"
    private LocalDate date;

    public Commande(Long id, Long produitId, int quantite, String statut, LocalDate date) {
        this.id = id;
        this.produitId = produitId;
        this.quantite = quantite;
        this.statut = statut;
        this.date = date;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public Long getProduitId() {
        return produitId;
    }

    public int getQuantite() {
        return quantite;
    }

    public String getStatut() {
        return statut;
    }

    public LocalDate getDate() {
        return date;
    }
}