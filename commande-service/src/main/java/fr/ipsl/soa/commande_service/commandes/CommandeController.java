package fr.ipsl.soa.commande_service.commandes;

import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/commandes")
public class CommandeController {

    private final List<Map<String, Object>> commandes = new ArrayList<>(List.of(
            Map.of("id", 1, "client", "Moussa", "total", 850000),
            Map.of("id", 2, "client", "Fatou", "total", 40000)
    ));

    @GetMapping
    public List<Map<String, Object>> listerTout() {
        return commandes;
    }

    @GetMapping("/{id}")
    public Map<String, Object> parId(@PathVariable int id) {
        return commandes.stream()
                .filter(c -> c.get("id").equals(id))
                .findFirst()
                .orElseThrow();
    }
}