package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.models.RetourReason;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RetourDAO {

    private final RetourReasonRepository retourReasonRepository;

    public RetourDAO(RetourReasonRepository retourReasonRepository) {
        this.retourReasonRepository = retourReasonRepository;
    }

    public List<RetourReason> getAllReasons() {
        return this.retourReasonRepository.findAll();
    }
}