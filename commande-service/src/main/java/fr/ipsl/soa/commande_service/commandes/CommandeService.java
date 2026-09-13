package fr.ipsl.soa.commande_service.commandes;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class CommandeService {

    private final List<Commande> commandes = List.of(
        new Commande(1L, 1L, 2, "LIVREE", LocalDate.of(2025, 3, 10)),
        new Commande(2L, 2L, 5, "EN_COURS", LocalDate.of(2025, 4, 1)),
        new Commande(3L, 3L, 1, "ANNULEE", LocalDate.of(2025, 4, 5))
    );

    @CircuitBreaker(name = "commandes", fallbackMethod = "fallbackCommandes")
    @Retry(name = "commandes")
    public List<Commande> getCommandes() {
        // 🔥 FORCE UNE EXCEPTION
        throw new RuntimeException("Base de données des commandes indisponible!");
    }

    public List<Commande> fallbackCommandes(Throwable t) {
        System.err.println("⚠️ CIRCUIT BREAKER OUVERT: " + t.getMessage());
        return List.of(
            new Commande(0L, 0L, 0, "INDISPONIBLE", LocalDate.now())
        );
    }
}