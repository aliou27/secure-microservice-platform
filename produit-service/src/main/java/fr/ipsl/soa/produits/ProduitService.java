package fr.ipsl.soa.produits;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProduitService {

    private final List<Produit> catalogue = List.of(
        new Produit(1L, "Ordinateur portable", 899.99, 15),
        new Produit(2L, "Souris sans fil", 29.90, 80),
        new Produit(3L, "Clavier mecanique", 79.50, 40)
    );

    @CircuitBreaker(name = "catalogue", fallbackMethod = "fallbackCatalogue")
    @Retry(name = "catalogue")
    public List<Produit> getCatalogue() {
        return catalogue;
    }

    // Methode de fallback appelee quand le circuit est ouvert
    public List<Produit> fallbackCatalogue(Throwable t) {
        System.err.println("Circuit ouvert : " + t.getMessage());
        return List.of(new Produit(0L, "Produit par defaut", 0.0, 0));
    }
}