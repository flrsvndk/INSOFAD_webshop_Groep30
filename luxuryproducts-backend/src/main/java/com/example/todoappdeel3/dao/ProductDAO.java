package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.CategoryDTO;
import com.example.todoappdeel3.dto.ProductDTO;
import com.example.todoappdeel3.dto.UpdatedProductDTO;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.models.ProductSpecification;
import com.example.todoappdeel3.models.ProductSpecificationType;
import com.example.todoappdeel3.repositories.ProductRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class ProductDAO {

    private final ProductRepository productRepository;
    private final CategoryDAO categoryDAO;
    private final ProductSpecificationDAO productSpecificationDAO;
    private final ProductSpecificationTypesRepository productSpecificationTypesRepository;
    public ProductDAO(ProductRepository productRepository, CategoryDAO categoryDAO, ProductSpecificationDAO productSpecificationDAO, ProductSpecificationTypesRepository productSpecificationTypesRepository) {
        this.productRepository = productRepository;
        this.categoryDAO = categoryDAO;
        this.productSpecificationDAO = productSpecificationDAO;
        this.productSpecificationTypesRepository = productSpecificationTypesRepository;
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
    public Product updateProduct(UpdatedProductDTO updatedProductDTO){
        Product product = this.getProductById(updatedProductDTO.id);

        product.setName(updatedProductDTO.name);
        product.setDescription(updatedProductDTO.description);

        if (updatedProductDTO.productSpecification == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Specification not found"
            );
        }
        ProductSpecification specification = this.productSpecificationDAO.updateSpecification(updatedProductDTO.productSpecification);

        product.setProductSpecification(specification);

        this.productRepository.save(product);

        return product;
    }

    @Transactional
    public Product createProduct(ProductDTO productDTO){
         Category category = this.setCategory(productDTO.category, productDTO.categoryId);


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

    public void incrementProductStock(UUID productId) {
        this.updateProductStock(productId, 1);
    }

    public void decrementProductStock(UUID productId) {
        this.updateProductStock(productId, -1);
    }

    private void updateProductStock(UUID productId, int amount) {
        Optional<ProductSpecificationType> productOptional = productSpecificationTypesRepository.findById(productId);
        if (productOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found");
        }
        ProductSpecificationType product = productOptional.get();
        product.setStock(product.getStock()+amount);
        productSpecificationTypesRepository.save(product);
    }

    private Category setCategory(CategoryDTO category, Long categoryId) {
        if(category == null && categoryId != 0){
            return this.categoryDAO.getById(categoryId);
        } else if (category != null) {
            return this.categoryDAO.createCategory(category.name);
        } else {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No correct category was given"
            );
        }
    }
}
