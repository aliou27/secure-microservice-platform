package fr.ipsl.soa.service_inventaire;

import fr.ipsl.soa.service_inventaire.StockItem;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/inventaire")
public class InventaireController {

    private final Map<Long, StockItem> stock = new HashMap<>();

    public InventaireController() {
        stock.put(1L, new StockItem(1L, 15, 5));
        stock.put(2L, new StockItem(2L, 80, 10));
        stock.put(3L, new StockItem(3L, 40, 8));
    }

    @GetMapping
    public Collection<StockItem> listerTout() {
        return stock.values();
    }

    @GetMapping("/{produitId}")
    public ResponseEntity<StockItem> parProduit(@PathVariable Long produitId) {
        StockItem item = stock.get(produitId);
        if (item == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(item);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{produitId}")
    public ResponseEntity<StockItem> mettreAJour(
            @PathVariable Long produitId,
            @RequestParam int quantite) {
        StockItem item = stock.get(produitId);
        if (item == null) return ResponseEntity.notFound().build();
        item.setQuantite(quantite);
        return ResponseEntity.ok(item);
    }
}