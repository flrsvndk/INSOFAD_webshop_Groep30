package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.ProductSpecificationsDTO;
import com.example.todoappdeel3.dto.TypeDTO;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.models.ProductSpecification;
import com.example.todoappdeel3.models.ProductSpecificationType;
import com.example.todoappdeel3.repositories.ProductSpecificationRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ProductSpecificationDAO {
    private final ProductSpecificationRepository repository;
    private final ProductSpecificationTypesRepository typesRepository;

    public ProductSpecificationDAO( ProductSpecificationRepository repository, ProductSpecificationTypesRepository typesRepository) {
        this.repository = repository;
        this.typesRepository = typesRepository;
    }

    public ProductSpecification createSpecification(Product product, ProductSpecificationsDTO productSpecificationsDTO) {
        List<ProductSpecificationType> types = new ArrayList<>();

        ProductSpecification productSpecification = new ProductSpecification(
                productSpecificationsDTO.name);

        this.repository.save(productSpecification);

        for(TypeDTO typeDTO : productSpecificationsDTO.typesDTO){
            if(typeDTO.productSpecificationsDTO == null){

                // er zijn geen andere specificaties en de eindprijs, de voorraad en het plaatje kan meegegeven worden
                ProductSpecificationType type = new ProductSpecificationType(
                        typeDTO.name,
                        typeDTO.stock,
                        typeDTO.imgUrl,
                        typeDTO.price,
                        product.getId()
                );

                type.setProductSpecification(productSpecification);
                this.typesRepository.save(type);
                types.add(type);

            } else{
                // er zijn nog sub categorien:

                ProductSpecification subProductSpecification = this.createSpecification(product, typeDTO.productSpecificationsDTO);

                ProductSpecificationType type = new ProductSpecificationType(
                        typeDTO.name, productSpecification, subProductSpecification, product.getId());

                this.typesRepository.save(type);
                types.add(type);
            }
        }

        productSpecification.setTypes(types);
        this.repository.save(productSpecification);

        return productSpecification;
    }

//    public ProductSpecification makeSubSpecification(ProductSpecificationsDTO productSpecificationsDTO){
//        List<ProductSpecificationType> types = new ArrayList<>();
//
//        ProductSpecification productSpecification = new ProductSpecification(
//                productSpecificationsDTO.name,
//                productSpecificationsDTO.imgUrl);
//
//        for(TypeDTO typeDTO : productSpecificationsDTO.typesDTO){
//            if(typeDTO.productSpecificationsDTO == null){
//                ProductSpecificationType type = new ProductSpecificationType(typeDTO.name, typeDTO.stock, typeDTO.priceAdjustment, typeDTO.imgUrl);
//
//                type.setProductSpecification(productSpecification);
//                this.typesRepository.save(type);
//                types.add(type);
//
//
//            } else{
//                ProductSpecification subProductSpecification = this.makeSubSpecification(typeDTO.productSpecificationsDTO);
//                ProductSpecificationType type = new ProductSpecificationType(typeDTO.name, typeDTO.priceAdjustment, subProductSpecification);
//
//                type.setProductSpecification(productSpecification);
//                this.typesRepository.save(type);
//                types.add(type);
//            }
//        }
//        productSpecification.setTypes(types);
//
//        this.repository.save(productSpecification);
//
//        return productSpecification;
//    }
}
