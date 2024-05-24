package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.CategoryDTO;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.Product;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class CategoryDAO {
    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;

    public CategoryDAO(CategoryRepository categoryRepository, ProductRepository productRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
    }

    public List<Category> getAll(){
        return this.categoryRepository.findAll();
    }

    public Category getById(Long categoryId){
        Optional<Category> categoryOptional = this.categoryRepository.findById(categoryId);
        if(categoryOptional.isPresent()){
            return categoryOptional.get();
        } throw new ResponseStatusException(
                HttpStatus.NOT_FOUND, "Category was not found"
        );
    }

    public Category createCategory(CategoryDTO categoryDTO){
        List<Product> products = new ArrayList<>();
        Category category = new Category(categoryDTO.name);

        if (categoryDTO.productIds != null) {
            for (UUID productId : categoryDTO.productIds) {
                Optional<Product> product = this.productRepository.findById(productId);
                if (product.isPresent()) {
                    products.add(product.get());
                    product.get().setCategory(category);
                }
            }
        }

        this.categoryRepository.save(category);
        return category;
    }
}
