package com.example.todoappdeel3.controller;

import com.example.todoappdeel3.dao.ProductDAO;
import com.example.todoappdeel3.dto.ProductDTO;
import com.example.todoappdeel3.dto.UpdatedProductDTO;
import com.example.todoappdeel3.models.Product;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/products")
public class ProductController {

    private final ProductDAO productDAO;

    public ProductController(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(){
        return ResponseEntity.ok(this.productDAO.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable UUID id){
        return ResponseEntity.ok(this.productDAO.getProductById(id));
    }

    @PutMapping
    public ResponseEntity<Product> updateProduct(@RequestBody UpdatedProductDTO updatedProductDTO){
        return ResponseEntity.ok(this.productDAO.updateProduct(updatedProductDTO));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createProduct(@RequestBody ProductDTO productDTO){
        this.productDAO.createProduct(productDTO);
        return ResponseEntity.ok("Created a product");
    }
}
