package com.example.todoappdeel3;


import com.example.todoappdeel3.dao.*;
import com.example.todoappdeel3.dto.*;
import com.example.todoappdeel3.models.*;
import com.example.todoappdeel3.repositories.CategoryRepository;
import com.example.todoappdeel3.repositories.ProductRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import com.example.todoappdeel3.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;


public class ProductDAOTests {
    @Mock
    private ProductRepository productRepository;

    @Mock
    private CategoryRepository categoryRepository;

    @Mock
    private ProductSpecificationRepository specRepo;

    @Mock
    private ProductSpecificationTypesRepository typesRepo;

    @Mock
    private CategoryDAO categoryDAO;

    @Mock
    private ProductSpecificationDAO productSpecificationDAO;

    @InjectMocks
    private ProductDAO productDAO;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.openMocks(this);
        this.productDAO = new ProductDAO(this.productRepository, this.categoryDAO, this.productSpecificationDAO, this.typesRepo);
    }


    @Test
    public void when_product_created_should_create_full_product(){
        CategoryDTO categoryDTO = new CategoryDTO("Horloges");
        String categoryName = "Horloges";
        Category category= new Category("Horloges");
        List<TypeDTO> typesDTO = new ArrayList<>();
        typesDTO.add( new TypeDTO("18kt gold",55000.00 , 8, "https://i.ibb.co/Z170ptk/image-2024-05-21-081438902.png", null));
        List<ProductSpecificationType> types = new ArrayList<>();
        types.add( new ProductSpecificationType("18kt gold", 8, "https://i.ibb.co/Z170ptk/image-2024-05-21-081438902.png", 55000.00 ,null));

        ProductSpecificationsDTO specificationsDTO = new ProductSpecificationsDTO("materiaal", typesDTO);
        ProductSpecification productSpecification = new ProductSpecification("materiaal");
        productSpecification.setTypes(types);
        ProductDTO productDTO = new ProductDTO("Rolex Daytona", "Deze Rolex Daytona, bekend om zijn ongeëvenaarde precisie en iconische status, is het ultieme statussymbool voor de elite.",
                categoryDTO,0, specificationsDTO);

        Product expected_product = new Product("Rolex Daytona", "Deze Rolex Daytona, bekend om zijn ongeëvenaarde precisie en iconische status, is het ultieme statussymbool voor de elite.",
                category, productSpecification);

        when(this.categoryDAO.createCategory(categoryName)).thenReturn(category);
        when(this.productSpecificationDAO.createSpecification(any(), eq(specificationsDTO))).thenReturn(productSpecification);

        Product actual_product = this.productDAO.createProduct(productDTO);

        assertThat(expected_product.getName(), is(actual_product.getName()));
        assertThat(expected_product.getProductSpecification(), is(actual_product.getProductSpecification()));

    }

    @Test
    public void when_product_created_without_types_should_create_no_product(){

        String categoryName ="Horloges";
        CategoryDTO categoryDTO = new CategoryDTO("Horloges");
        Category category= new Category("Horloges");
        List<TypeDTO> typesDTO = new ArrayList<>();
        typesDTO.add( new TypeDTO("18kt gold",55000.00 , 8, "https://i.ibb.co/Z170ptk/image-2024-05-21-081438902.png", null));
        List<ProductSpecificationType> types = new ArrayList<>();
        types.add( new ProductSpecificationType("18kt gold", 8, "https://i.ibb.co/Z170ptk/image-2024-05-21-081438902.png", 55000.00 ,null));

        ProductSpecificationsDTO specificationsDTO = new ProductSpecificationsDTO("", typesDTO);
        ProductSpecification productSpecification = new ProductSpecification("");
        productSpecification.setTypes(types);
        ProductDTO productDTO = new ProductDTO("Rolex Daytona", "Deze Rolex Daytona, bekend om zijn ongeëvenaarde precisie en iconische status, is het ultieme statussymbool voor de elite.",
                categoryDTO, category.getId(), specificationsDTO);

        Product expected_product = new Product();

        when(this.categoryDAO.createCategory(categoryName)).thenReturn(category);
        when(this.productSpecificationDAO.createSpecification(any(), eq(specificationsDTO))).thenReturn(productSpecification);

        Product actual_product = this.productDAO.createProduct(productDTO);

        assertThat(actual_product.getName(), is(expected_product.getName()));
        ChangeSetPersister.NotFoundException exeption = assertThrows(ChangeSetPersister.NotFoundException.class, () -> this.productDAO.createProduct(productDTO));

    }

}
