package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.CategoryDAO;
import com.example.todoappdeel3.dto.CategoryDTO;
import com.example.todoappdeel3.models.Category;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/category")
public class CategoryController {
    private final CategoryDAO categoryDAO;

    public CategoryController(CategoryDAO categoryDAO) {
        this.categoryDAO = categoryDAO;
    }

    @GetMapping
    public ResponseEntity<List<Category>> getAllCategory(){
        return ResponseEntity.ok(this.categoryDAO.getAll());
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategory(@RequestHeader Long categoryId){
        return ResponseEntity.ok(this.categoryDAO.getById(categoryId));
    }

    @PostMapping
    public ResponseEntity<Long> createCategory(@RequestBody CategoryDTO categoryDTO){
        Long categoryId = this.categoryDAO.createCategory(categoryDTO).getId();
        return ResponseEntity.ok(categoryId);
    }
}
