package com.example.todoappdeel3.controller;


import com.example.todoappdeel3.dao.GiftcardDAO;
import com.example.todoappdeel3.dto.GiftcardBuyerDTO;
import com.example.todoappdeel3.dto.GiftcardOwnerDTO;
import com.example.todoappdeel3.dto.GiftcardPurchaseDTO;
import com.example.todoappdeel3.dto.GiftcardValueDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;


@RestController
@CrossOrigin(origins = {"http://localhost:4200"})
@RequestMapping("/giftcards")

public class GiftcardController {

    private final GiftcardDAO giftcardDAO;

    public GiftcardController(GiftcardDAO giftcardDAO) {
        this.giftcardDAO = giftcardDAO;
    }

    @GetMapping("/getByOwner")
    public ResponseEntity<List<GiftcardOwnerDTO>> getGiftcardsByOwner(Principal principal) {
        return ResponseEntity.ok(this.giftcardDAO.getGiftcardsByOwner(principal.getName()));
    }

    @GetMapping("/getByBuyer")
    public ResponseEntity<List<GiftcardBuyerDTO>> getGiftcardsByBuyer(Principal principal) {
        return ResponseEntity.ok(this.giftcardDAO.getGiftcardsByBuyer(principal.getName()));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<GiftcardOwnerDTO>> getAllGiftcards(Principal principal) {
        return ResponseEntity.ok(this.giftcardDAO.getAllGiftcards(principal.getName()));
    }

    @PostMapping
    public ResponseEntity<String> createGiftcard(@RequestBody GiftcardPurchaseDTO purchaseDTO, Principal principal){
        return this.giftcardDAO.createGiftcard(purchaseDTO, principal.getName());
    }

    @PutMapping("/lowerValue")
    public ResponseEntity<String> lowerGiftcardValue(@RequestBody GiftcardValueDTO value){

        return this.giftcardDAO.lowerGiftcardValue(value.value, value.id);
    }

    @PutMapping("/charge")
    public ResponseEntity<String> chargeGiftcard(@RequestBody GiftcardValueDTO value){
        System.out.println(value.id);
        return this.giftcardDAO.chargeGiftcard(value.value, value.id);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id, Principal principal){
        this.giftcardDAO.deleteGiftcard(id, principal.getName());

        return ResponseEntity.ok("Giftcard deleted with id " + id);
    }
}
