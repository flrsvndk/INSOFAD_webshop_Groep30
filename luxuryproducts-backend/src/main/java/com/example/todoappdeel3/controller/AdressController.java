package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.AdressDAO;
import com.example.todoappdeel3.dto.AdressDTO;
import com.example.todoappdeel3.models.Adress;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/adress")
public class AdressController {
    private final AdressDAO adressDAO;

    public AdressController(AdressDAO adressDAO) {
        this.adressDAO = adressDAO;
    }


    @PostMapping
    public ResponseEntity<String> createAdress(@RequestBody AdressDTO adressDTO){
        this.adressDAO.createAdress(adressDTO);
        return ResponseEntity.ok("Created an Adress");
    }
}
