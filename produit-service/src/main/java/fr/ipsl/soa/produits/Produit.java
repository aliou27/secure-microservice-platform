package fr.ipsl.soa.produits;

public class Produit {
    private Long id;
    private String nom;
    private double prix;
    private int stock;

    public Produit(Long id, String nom, double prix, int stock) {
        this.id = id;
        this.nom = nom;
        this.prix = prix;
        this.stock = stock;
    }

    // Getters
    public Long getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public double getPrix() {
        return prix;
    }

    public int getStock() {
        return stock;
    }
}