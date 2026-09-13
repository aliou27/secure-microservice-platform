package fr.ipsl.soa.service_inventaire;

public class StockItem {
    private Long produitId;
    private int quantite;
    private int seuilAlerte;

    public StockItem(Long produitId, int quantite, int seuilAlerte) {
        this.produitId = produitId;
        this.quantite = quantite;
        this.seuilAlerte = seuilAlerte;
    }

    public Long getProduitId() { return produitId; }
    public int getQuantite() { return quantite; }
    public int getSeuilAlerte() { return seuilAlerte; }
    public void setQuantite(int q) { this.quantite = q; }
}