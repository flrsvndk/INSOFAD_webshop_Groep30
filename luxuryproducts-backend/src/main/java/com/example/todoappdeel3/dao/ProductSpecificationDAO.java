package com.example.todoappdeel3.dao;

import com.example.todoappdeel3.dto.ProductSpecificationsDTO;
import com.example.todoappdeel3.dto.TypeDTO;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.models.ProductSpecification;
import com.example.todoappdeel3.models.ProductSpecificationType;
import com.example.todoappdeel3.repositories.ProductSpecificationRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
                productSpecificationsDTO.specificationName);

        this.repository.save(productSpecification);

        if(productSpecificationsDTO.types != null) {

            for (TypeDTO typeDTO : productSpecificationsDTO.types) {
                if (typeDTO.subSpecification == null) {

                    ProductSpecificationType type = new ProductSpecificationType(
                            typeDTO.typeName,
                            typeDTO.stock,
                            typeDTO.imgUrl,
                            typeDTO.price,
                            product.getId()
                    );

                    type.setProductSpecification(productSpecification);
                    this.typesRepository.save(type);
                    types.add(type);

                } else {
                    ProductSpecification subProductSpecification =
                            this.createSpecification(
                                    product,
                                    typeDTO.subSpecification
                            );

                    this.repository.save(subProductSpecification);

                    ProductSpecificationType type = new ProductSpecificationType(
                            typeDTO.typeName, typeDTO.imgUrl, productSpecification, subProductSpecification);

                    this.typesRepository.save(type);
                    types.add(type);
                }
            }
        } else{
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "No types to be added"
            );
        }

        productSpecification.setTypes(types);
        this.repository.save(productSpecification);

        return productSpecification;
    }

    public ProductSpecification updateSpecification(ProductSpecificationsDTO productSpecificationsDTO){
        Optional<ProductSpecification> optionalSpecification =  this.repository.findById(productSpecificationsDTO.id);
        if(optionalSpecification.isEmpty()){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "specification doesnt exist"
            );
        }
        ProductSpecification specification = optionalSpecification.get();

        specification.setName(productSpecificationsDTO.specificationName);
        List<ProductSpecificationType> types = new ArrayList<>();

        for(TypeDTO typeDTO : productSpecificationsDTO.types){
            Optional<ProductSpecificationType> optionalType = this.typesRepository.findById(typeDTO.id);

            if(optionalType.isEmpty()) {
                throw new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "type doesnt exist"
                );
            }
            ProductSpecificationType type = optionalType.get();

            type.setName(typeDTO.typeName);
            type.setImgUrl(typeDTO.imgUrl);
            type.setPrice(typeDTO.price);
            type.setStock(typeDTO.stock);

            if (typeDTO.subSpecification != null){
                ProductSpecification subSpecification = this.updateSpecification(typeDTO.subSpecification);
                type.setProductSpecification(subSpecification);
            }

            this.typesRepository.save(type);
            types.add(type);
        }

        specification.setTypes(types);
        this.repository.save(specification);
        return specification;
    }

}
