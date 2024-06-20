package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.PromocodeDTO;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.models.Promocode;
import com.example.todoappdeel3.repositories.PromocodeRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class PromocodeDAO {

    private final PromocodeRepository promocodeRepository;

    public PromocodeDAO(PromocodeRepository promocodeRepository) {
        this.promocodeRepository = promocodeRepository;
    }

    public List<Promocode> getAllPromocodes() {
        return this.promocodeRepository.findAll();
    }

    @Transactional
    public void createPromocode(PromocodeDTO promocodeDTO) {
        Promocode promocode = new Promocode(
                promocodeDTO.name,
                promocodeDTO.description,
                promocodeDTO.percentageOff,
                promocodeDTO.maxUsages,
                promocodeDTO.usages,
                promocodeDTO.totalPriceOrders,
                promocodeDTO.available,
                promocodeDTO.dedicatedPromocode,
                promocodeDTO.dedicatedUserEmail);
        this.promocodeRepository.save(promocode);
    }

    @Transactional
    public void updatePromocode(PromocodeDTO promocodeDTO) {
        Optional<Promocode> optionalPromocode = this.promocodeRepository.findById(promocodeDTO.id);

        if (optionalPromocode.isPresent()) {
            Promocode newPromocode = optionalPromocode.get();

            newPromocode.setName(promocodeDTO.name);
            newPromocode.setDescription(promocodeDTO.description);
            newPromocode.setPercentageOff(promocodeDTO.percentageOff);
            newPromocode.setMaxUsages(promocodeDTO.maxUsages);
            newPromocode.setAvailable(promocodeDTO.available);
            newPromocode.setDedicatedPromocode(promocodeDTO.dedicatedPromocode);
            newPromocode.setDedicatedUserEmail(promocodeDTO.dedicatedUserEmail);

            this.promocodeRepository.save(newPromocode);
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Promocode with id: " + promocodeDTO.id + " not found"
        );
    }

    @Transactional
    public void updateAnalyticsPromocode(PlacedOrder placedOrder) {
        Optional<Promocode> optionalPromocode = this.promocodeRepository.findById(placedOrder.getPromocode().getId());

        if (optionalPromocode.isPresent()) {
            optionalPromocode.get().setUsages(optionalPromocode.get().getUsages() + 1);
            optionalPromocode.get().setTotalPriceOrders(
                    optionalPromocode.get().getTotalPriceOrders() + placedOrder.getTotalPriceAfterPromocode());

            if (optionalPromocode.get().getUsages() == optionalPromocode.get().getMaxUsages()) {
                optionalPromocode.get().setAvailable(false);
            }

            this.promocodeRepository.save(optionalPromocode.get());
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Promocode with id: " + placedOrder.getPromocode().getId() + " not found"
        );
    }

    public Promocode getPromocodeById(UUID id) {
        Optional<Promocode> promocode = promocodeRepository.findById(id);

        if (promocode.isPresent()) {
            return promocode.get();
        }

        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Promocode with id: " + id + " not found"
        );
    }

    public boolean archivePromocode(UUID id) {
        Optional<Promocode> optionalPromocode = this.promocodeRepository.findById(id);

        if (optionalPromocode.isPresent()) {
            optionalPromocode.get().setAvailable(!optionalPromocode.get().isAvailable());
            this.promocodeRepository.save(optionalPromocode.get());
            return optionalPromocode.get().isAvailable();
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Promocode with id: " + id + " not found"
        );
    }

    public void deletePromocode(UUID id) {
        Optional<Promocode> optionalPromocode = this.promocodeRepository.findById(id);

        if (optionalPromocode.isPresent()) {
            this.promocodeRepository.delete(optionalPromocode.get());
            return;
        }
        throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Promocode with id: " + id + " not found"
        );
    }
}
