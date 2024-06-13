package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.GiftcardBuyerDTO;
import com.example.todoappdeel3.dto.GiftcardOwnerDTO;
import com.example.todoappdeel3.dto.GiftcardPurchaseDTO;
import com.example.todoappdeel3.models.Giftcard;
import com.example.todoappdeel3.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class GiftcardDAO {
    private final GiftcardRepository giftcardRepository;
    private final UserRepository userRepository;

    public GiftcardDAO(GiftcardRepository giftcard, UserRepository userRepository) {
        this.giftcardRepository = giftcard;
        this.userRepository = userRepository;
    }

    public List<GiftcardOwnerDTO> getAllGiftcards(String email) {
        if (userRepository.findByEmail(email).getRole().equals("ADMIN")) {
            List<GiftcardOwnerDTO> adminDTOs = new ArrayList<>();
            giftcardRepository.findAll().forEach((n) -> adminDTOs.add(new GiftcardOwnerDTO(n.getId(), n.getMessage(), n.getPrice(), n.getValue(), n.getBuyer().getEmail(), n.getOwner().getEmail())));
            return adminDTOs;
        }
        else {
            return new ArrayList<>();
        }
    }

    public List<GiftcardOwnerDTO> getGiftcardsByOwner(String ownerEmail){
        List<GiftcardOwnerDTO> ownerDTOs = new ArrayList<>();
        giftcardRepository.findByOwner(userRepository.findByEmail(ownerEmail)).forEach((n) -> ownerDTOs.add(new GiftcardOwnerDTO(n.getId(), n.getMessage(), n.getPrice(), n.getValue(), n.getBuyer().getEmail(), "")));
        return ownerDTOs;
    }

    public List<GiftcardBuyerDTO> getGiftcardsByBuyer(String buyerEmail){
        List<GiftcardBuyerDTO> buyerDTOs = new ArrayList<>();
        giftcardRepository.findByBuyer(userRepository.findByEmail(buyerEmail)).forEach((n) -> buyerDTOs.add(new GiftcardBuyerDTO(n.getId(), n.getMessage(), n.getPrice(), n.getOwner().getEmail(), n.getBuyer().getEmail())));
        return buyerDTOs;
    }

    @Transactional
    public ResponseEntity<String> createGiftcard(GiftcardPurchaseDTO purchaseDTO, String buyerEMail){
        System.out.println("purchaseDTO.ownerEmail " + purchaseDTO.ownerEmail);
        if (userRepository.findByEmail(purchaseDTO.ownerEmail) == null) {
            return ResponseEntity.ok("Receiver of this giftcard doesn't exist");
        }
        else {
            Giftcard giftcard = new Giftcard(purchaseDTO.message, purchaseDTO.price, purchaseDTO.price
                    , userRepository.findByEmail(buyerEMail), userRepository.findByEmail(purchaseDTO.ownerEmail));
            this.giftcardRepository.save(giftcard);
            return ResponseEntity.ok().body("Giftcard created");
        }
    }

    public ResponseEntity<String> lowerGiftcardValue(Number value, Long id){
        Optional<Giftcard> giftcard = this.giftcardRepository.findById(id);

        if (giftcard.isPresent()){
            if (giftcard.get().getValue().doubleValue() > value.doubleValue()) {
                giftcard.get().setValue(value);

                System.out.println(value);

                this.giftcardRepository.save(giftcard.get());
                return ResponseEntity.ok("Updated giftcard value");
            }
            else {
                return ResponseEntity.ok("New giftcard value wasn't below old giftcard value");
            }
        }
        else {
            return ResponseEntity.ok("Giftcard with this id doesn't exist");
        }
    }

    public ResponseEntity<String> chargeGiftcard(Number value, Long id){
        Optional<Giftcard> giftcard = this.giftcardRepository.findById(id);

        if (giftcard.isPresent()){
            giftcard.get().setValue(value);

            this.giftcardRepository.save(giftcard.get());
            return ResponseEntity.ok("Updated giftcard value");
        }
        else {
            return ResponseEntity.ok("Giftcard with this id doesn't exist");
        }
    }

    public ResponseEntity<String> deleteGiftcard(Long id, String email) {
        if (userRepository.findByEmail(email) != null) {
            if (userRepository.findByEmail(email).getRole().equals("ADMIN")) {
                this.giftcardRepository.deleteById(id);
                return ResponseEntity.ok("Giftcard canceled");
            }
            return ResponseEntity.ok("You don't have the rights to cancel a giftcard");
        }
        return ResponseEntity.ok("User with this email not found");
    }
}
