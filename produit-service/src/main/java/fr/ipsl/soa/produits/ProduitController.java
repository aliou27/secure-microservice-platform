package fr.ipsl.soa.produits;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/produits")
public class ProduitController {

    private final List<Map<String, Object>> produits = List.of(
            Map.of("id", 1, "nom", "Laptop", "prix", 850000),
            Map.of("id", 2, "nom", "Souris", "prix", 15000),
            Map.of("id", 3, "nom", "Clavier", "prix", 25000)
    );

    @GetMapping
    public List<Map<String, Object>> listerTout() {
        return produits;
    }

    @GetMapping("/{id}")
    public Map<String, Object> parId(@PathVariable int id) {
        return produits.stream()
                .filter(p -> p.get("id").equals(id))
                .findFirst()
                .orElseThrow();
    }
}