package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.AdressDTO;
import com.example.todoappdeel3.models.Adress;
import com.example.todoappdeel3.services.CredentialValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AdressDAO {

    private final CredentialValidator credentialValidator;
    private final AdressRepository adressRepository;

    public AdressDAO(CredentialValidator credentialValidator, AdressRepository adressRepository) {
        this.credentialValidator = credentialValidator;
        this.adressRepository = adressRepository;
    }

    public Adress createAdress(AdressDTO adressDTO){
        if(credentialValidator.isValidZipCode(adressDTO.zipcode)) {
            Adress adress = new Adress(adressDTO.zipcode, adressDTO.houseNumber, adressDTO.houseNumberAddition);
            this.adressRepository.save(adress);
            return adress;
        } throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "no correct zipcode specified"
        );
    }

}
