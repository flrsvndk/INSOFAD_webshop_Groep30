package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.ProductDTO;
import com.example.todoappdeel3.models.Product;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductDAO {

    private final ProductRepository productRepository;

    public ProductDAO(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }

    public Product getProductById(UUID id) {
        Optional<Product> product = this.productRepository.findById(id);

        return product.orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND, "No product found with that id"
        ));
    }

    @Transactional
    public void createProduct(Product product){
        this.productRepository.save(product);
    }

    @Transactional
    public void createProduct(ProductDTO productDTO){
            Product product = new Product(productDTO.name, productDTO.price,  productDTO.description,productDTO.imgURL, productDTO.stock, productDTO.groupset, productDTO.material, productDTO.wheels);
            this.productRepository.save(product);
            return;
    }

}
