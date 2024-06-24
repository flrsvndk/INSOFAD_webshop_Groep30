package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.ProductSpecificationDAO;
import com.example.todoappdeel3.dto.ProductSpecificationsDTO;
import com.example.todoappdeel3.dto.TypeDTO;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.models.ProductSpecification;
import com.example.todoappdeel3.models.ProductSpecificationType;
import com.example.todoappdeel3.repositories.ProductSpecificationRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TestProductSpecifications {
        @Mock
        private ProductSpecificationRepository repository;

        @Mock
        private ProductSpecificationTypesRepository typesRepository;

        @InjectMocks
        private ProductSpecificationDAO productSpecificationDAO; // Assuming your service class is named ProductSpecificationService

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        public void testCreateSpecification() {
            UUID typeId = UUID.randomUUID();
            ProductSpecificationsDTO productSpecificationsDTO = new ProductSpecificationsDTO();
            productSpecificationsDTO.specificationName = "Test Specification";
            TypeDTO typeDTO = new TypeDTO();
            typeDTO.typeName = "Test Type";
            typeDTO.stock = 10;
            typeDTO.imgUrl = "http://example.com/image.jpg";
            typeDTO.price = 100.0;
            productSpecificationsDTO.types = List.of(typeDTO);

            Product product = new Product();
            product.setId(typeId);

            ProductSpecification productSpecification = new ProductSpecification(productSpecificationsDTO.specificationName);
            when(this.repository.save(ArgumentMatchers.any())).thenReturn(productSpecification);

            ProductSpecification createdSpecification = productSpecificationDAO.createSpecification(product, productSpecificationsDTO);

            assertNotNull(createdSpecification);
            assertEquals("Test Specification", createdSpecification.getName());
            assertEquals(1, createdSpecification.getTypes().size());

            ProductSpecificationType createdType = createdSpecification.getTypes().getFirst();
            assertEquals("Test Type", createdType.getName());
            assertEquals(10, createdType.getStock());
            assertEquals("http://example.com/image.jpg", createdType.getImgUrl());
            assertEquals(100.0, createdType.getPrice());
            assertEquals(typeId, createdType.getProductId());

            verify(repository, times(2)).save(ArgumentMatchers.any());
            verify(typesRepository, times(1)).save(ArgumentMatchers.any());
        }

        @Test
        public void testCreateSpecification_NoTypes() {
            UUID typeId = UUID.randomUUID();
            ProductSpecificationsDTO productSpecificationsDTO = new ProductSpecificationsDTO();
            productSpecificationsDTO.specificationName = "Test Specification";
            productSpecificationsDTO.types = null;

            Product product = new Product();
            product.setId(typeId);

            ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
                productSpecificationDAO.createSpecification(product, productSpecificationsDTO);
            });

            assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
            assertEquals("No types to be added", exception.getReason());
        }

    @Test
    public void testUpdateSpecification() {
        UUID specificationId = UUID.randomUUID();
        UUID typeId = UUID.randomUUID();

        ProductSpecificationsDTO productSpecificationsDTO = new ProductSpecificationsDTO();
        productSpecificationsDTO.id = specificationId;
        productSpecificationsDTO.specificationName = "Updated Specification";

        TypeDTO typeDTO = new TypeDTO();
        typeDTO.id = typeId;
        typeDTO.typeName = "Updated Type";
        typeDTO.imgUrl = "http://example.com/updated_image.jpg";
        typeDTO.price = 150.0;
        typeDTO.stock = 20;
        productSpecificationsDTO.types = List.of(typeDTO);

        ProductSpecification existingSpecification = new ProductSpecification("Original Specification");
        existingSpecification.setId(specificationId);

        ProductSpecificationType existingType = new ProductSpecificationType("Original Type", 10, "http://example.com/image.jpg", 100.0, specificationId);
        existingType.setId(typeId);

        when(repository.findById(specificationId)).thenReturn(Optional.of(existingSpecification));
        when(typesRepository.findById(typeId)).thenReturn(Optional.of(existingType));


        ProductSpecification updatedSpecification = productSpecificationDAO.updateSpecification(productSpecificationsDTO);

        assertNotNull(updatedSpecification);
        assertEquals("Updated Specification", updatedSpecification.getName());
        assertEquals(1, updatedSpecification.getTypes().size());

        ProductSpecificationType updatedType = updatedSpecification.getTypes().get(0);
        assertEquals("Updated Type", updatedType.getName());
        assertEquals(150.0, updatedType.getPrice());
        assertEquals(20, updatedType.getStock());
        assertEquals("http://example.com/updated_image.jpg", updatedType.getImgUrl());

        verify(repository, times(1)).findById(specificationId);
        verify(repository, times(1)).save(any(ProductSpecification.class));
        verify(typesRepository, times(1)).findById(typeId);
        verify(typesRepository, times(1)).save(any(ProductSpecificationType.class));
    }

    @Test
    public void testUpdateSpecification_SpecificationNotFound() {
        UUID typeId = UUID.randomUUID();
        ProductSpecificationsDTO productSpecificationsDTO = new ProductSpecificationsDTO();
        productSpecificationsDTO.id = typeId;

        when(repository.findById(typeId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            productSpecificationDAO.updateSpecification(productSpecificationsDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("specification doesnt exist", exception.getReason());

        verify(repository, times(1)).findById(typeId);
    }

    @Test
    public void testUpdateSpecification_TypeNotFound() {
        UUID typeId = UUID.randomUUID();
        ProductSpecificationsDTO productSpecificationsDTO = new ProductSpecificationsDTO();
        productSpecificationsDTO.id = typeId;
        productSpecificationsDTO.specificationName = "Updated Specification";

        TypeDTO typeDTO = new TypeDTO();
        typeDTO.id = typeId;
        typeDTO.typeName = "Updated Type";
        typeDTO.imgUrl = "http://example.com/updated_image.jpg";
        typeDTO.price = 150.0;
        typeDTO.stock = 20;
        productSpecificationsDTO.types = List.of(typeDTO);

        ProductSpecification existingSpecification = new ProductSpecification("Original Specification");
        existingSpecification.setId(typeId);

        when(repository.findById(typeId)).thenReturn(Optional.of(existingSpecification));
        when(typesRepository.findById(typeId)).thenReturn(Optional.empty());

        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            productSpecificationDAO.updateSpecification(productSpecificationsDTO);
        });

        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertEquals("type doesnt exist", exception.getReason());

        verify(repository, times(1)).findById(typeId);
        verify(typesRepository, times(1)).findById(typeId);
    }
    }

