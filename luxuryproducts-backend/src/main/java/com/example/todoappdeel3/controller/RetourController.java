package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.RetourDAO;
import com.example.todoappdeel3.models.RetourReason;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/retour")
public class RetourController {

    private final RetourDAO retourDAO;

    public RetourController(RetourDAO retourDAO) {
        this.retourDAO = retourDAO;
    }

    @GetMapping("reasons")
    public ResponseEntity<List<RetourReason>> getAllReasons(){
        return ResponseEntity.ok(this.retourDAO.getAllReasons());
    }

}