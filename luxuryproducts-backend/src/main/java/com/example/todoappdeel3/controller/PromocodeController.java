package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.PromocodeDAO;
import com.example.todoappdeel3.dto.PromocodeDTO;
import com.example.todoappdeel3.models.Promocode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/promocodes")
public class PromocodeController {

    private final PromocodeDAO promocodeDAO;

    public PromocodeController(PromocodeDAO promocodeDAO) {this.promocodeDAO = promocodeDAO;}

    @GetMapping
    public ResponseEntity<List<Promocode>> getAllPromocodes() {
        return ResponseEntity.ok(this.promocodeDAO.getAllPromocodes());
    }

    @PostMapping
    public ResponseEntity<String> createPromocode(@RequestBody PromocodeDTO promocodeDTO) {
        this.promocodeDAO.createPromocode(promocodeDTO);
        return ResponseEntity.ok("Created promocode successfully");
    }

    @PutMapping("update")
    public ResponseEntity<String> updatePromocode(@RequestBody PromocodeDTO promocodeDTO) {
        this.promocodeDAO.updatePromocode(promocodeDTO);
        return ResponseEntity.ok("Updated promocode successfully");
    }

    @GetMapping("/{id}")
    public ResponseEntity<Promocode> getPromocodeById(@PathVariable UUID id) {
        return ResponseEntity.ok(this.promocodeDAO.getPromocodeById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> archivePromocode(@PathVariable UUID id) {
        boolean response = this.promocodeDAO.archivePromocode(id);
        return ResponseEntity.ok("Availabilility set to: " + response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePromocode(@PathVariable UUID id) {
        this.promocodeDAO.deletePromocode(id);
        return ResponseEntity.ok("Deleted promocode successfully with id: " + id);
    }
}
