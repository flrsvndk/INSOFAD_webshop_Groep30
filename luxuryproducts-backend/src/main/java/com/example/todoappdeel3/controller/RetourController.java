package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.RetourDAO;
import com.example.todoappdeel3.dto.RetourRequestDTO;
import com.example.todoappdeel3.models.RetourReason;
import com.example.todoappdeel3.models.RetourRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("requests")
    public ResponseEntity<List<RetourRequest>> getAllRequests() {
        return ResponseEntity.ok(this.retourDAO.getAllRequests());
    }

    @PostMapping
    public ResponseEntity<RetourRequest> createRequest(@RequestBody RetourRequestDTO retourRequestDTO) {
        return ResponseEntity.ok(this.retourDAO.createRetourRequest(retourRequestDTO));
    }

}