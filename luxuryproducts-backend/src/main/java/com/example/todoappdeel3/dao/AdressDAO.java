package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.AdressDTO;
import com.example.todoappdeel3.models.Adress;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.repositories.AdressRepository;
import com.example.todoappdeel3.repositories.UserRepository;
import com.example.todoappdeel3.services.CredentialValidator;
import com.example.todoappdeel3.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class AdressDAO {

    private final CredentialValidator credentialValidator;
    private final AdressRepository adressRepository;
    private final UserService userService;
    private final UserRepository userRepository;

    public AdressDAO(CredentialValidator credentialValidator, AdressRepository adressRepository, UserService userService, UserRepository userRepository) {
        this.credentialValidator = credentialValidator;
        this.adressRepository = adressRepository;
        this.userService = userService;
        this.userRepository = userRepository;
    }

    public Adress createAdress(AdressDTO adressDTO) {
        CustomUser user = userService.getUser();

        if (adressDTO == null) {
            if (user.getAdress() != null && !user.getAdress().getZipcode().isEmpty()) {
                return user.getAdress();
            }
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No zipcode specified"
            );
        }
        if (adressDTO.zipCode != null && credentialValidator.isValidZipCode(adressDTO.zipCode)) {
                Adress adress = new Adress(adressDTO.zipCode, adressDTO.houseNumber, adressDTO.houseNumberAddition);

                System.out.println(user.getId() + user.getEmail());

                if (adressDTO.save) {
                    adress.setCustomUser(user);
                    user.setAdress(adress);
                    this.userRepository.save(user);
                }

                this.adressRepository.save(adress);

                return adress;

        } else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "no correct zipcode specified"
            );
        }
    }
}
