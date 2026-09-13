package fr.ipsl.soa.api_gateway.gateway;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/fallback")
public class FallbackController {

    @GetMapping("/produits")
    public ResponseEntity<Map<String, String>> fallbackProduits() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "erreur", "Service Produits indisponible",
                        "conseil", "Reessayez dans quelques secondes"
                ));
    }

    @GetMapping("/commandes")
    public ResponseEntity<Map<String, String>> fallbackCommandes() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "erreur", "Service Commandes indisponible",
                        "conseil", "Reessayez dans quelques secondes"
                ));
    }

    @GetMapping("/inventaire")
    public ResponseEntity<Map<String, String>> fallbackInventaire() {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(Map.of(
                        "erreur", "Service Inventaire indisponible",
                        "conseil", "Reessayez dans quelques secondes"
                ));
    }
}