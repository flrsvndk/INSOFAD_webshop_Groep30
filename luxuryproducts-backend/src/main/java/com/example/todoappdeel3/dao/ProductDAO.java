package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.ProductDTO;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.models.ProductSpecification;
import com.example.todoappdeel3.repositories.ProductRepository;
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
    private final CategoryDAO categoryDAO;
    private final ProductSpecificationDAO productSpecificationDAO;

    public ProductDAO(ProductRepository productRepository, CategoryDAO categoryDAO, ProductSpecificationDAO productSpecificationDAO) {
        this.productRepository = productRepository;
        this.categoryDAO = categoryDAO;
        this.productSpecificationDAO = productSpecificationDAO;
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
    public Product createProduct(ProductDTO productDTO){
         Category category;

         if(productDTO.category == null && productDTO.categoryId != 0){
             category = this.categoryDAO.getById(productDTO.categoryId);
         } else if (productDTO.category != null) {
             category = this.categoryDAO.createCategory(productDTO.category.name);
         } else {
             throw new ResponseStatusException(
                     HttpStatus.NOT_FOUND, "No correct category was given"
             );
         }

        Product product = new Product(
            productDTO.name,
            productDTO.description,
            category
        );
        this.productRepository.save(product);

        ProductSpecification productSpecification = this.productSpecificationDAO.createSpecification(product, productDTO.productSpecificationsDTO);
        product.setProductSpecification(productSpecification);
        this.productRepository.save(product);
        return product;
    }

}
