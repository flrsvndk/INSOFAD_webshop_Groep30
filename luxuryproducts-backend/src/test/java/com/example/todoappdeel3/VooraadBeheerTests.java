package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.AdressDAO;
import com.example.todoappdeel3.dao.OrderItemDAO;
import com.example.todoappdeel3.repositories.OrderItemRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import com.example.todoappdeel3.dto.AdressDTO;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.dto.OrderItemDTO;
import com.example.todoappdeel3.models.Adress;
import com.example.todoappdeel3.models.CustomUser;
import com.example.todoappdeel3.models.ProductSpecificationType;
import com.example.todoappdeel3.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class VooraadBeheerTests {

    @Mock
    private ProductSpecificationTypesRepository productSpecificationTypesRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private AdressDAO adressDAO;

    @InjectMocks
    private OrderItemDAO orderItemDAO;

    @InjectMocks
    private OrderService orderService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        this.orderItemDAO = new OrderItemDAO(this.orderItemRepository, this.productSpecificationTypesRepository);
        this.orderService = new OrderService(this.orderItemDAO, this.adressDAO, this.productSpecificationTypesRepository);
    }

    @Test
    public void when_product_ordered_should_one_subtracted_from_stock() {
        // Arrange
        int quanityOrdered = 3;
        UUID typeId = UUID.randomUUID();
        CustomUser dummyUser = new CustomUser("test", "test", "test", "test@test.nl", "passwordTest123!");
        Adress dummyAdress = new Adress("2406KE", 132, "a");
        AdressDTO dummyAdressDTO = new AdressDTO();
        ProductSpecificationType dummyProductType = new ProductSpecificationType("name", 5, "img", 50.00, null);
        dummyProductType.setId(typeId);
        List<OrderItemDTO> dummyOrderItems = new ArrayList<>();
        dummyOrderItems.add(new OrderItemDTO(quanityOrdered, typeId));
        OrderDTO dummyOrderDTO = new OrderDTO("note", dummyOrderItems, dummyAdressDTO);

        when(this.adressDAO.createAdress(dummyAdressDTO)).thenReturn(dummyAdress);
        when(this.orderItemRepository.save(any())).thenReturn(true);
        when(this.productSpecificationTypesRepository.findById(typeId)).thenReturn(Optional.of(dummyProductType));

        int expectedStock = 2; // Corrected expected stock

        // Act
        this.orderService.createOrder(dummyOrderDTO, dummyUser);
        int actualStock = dummyProductType.getStock();

        // Assert
        assertThat(actualStock, is(expectedStock));
    }

    @Test
    public void when_quanity_ordered_more_than_stock_should_return_quanity_is_stock(){
        // Arrange
        ProductSpecificationType dummyProductType = new ProductSpecificationType("name", 5, "img" , 50.00, null);
        UUID typeId = UUID.randomUUID();

        when(this.productSpecificationTypesRepository.findById(typeId)).thenReturn(Optional.of(dummyProductType));

        int expectedQuanity = 5;


        // Act
        int OrderQuanity = this.orderService.checkProductQuanity(typeId, 6);

        // Assert
        assertThat(expectedQuanity, is(OrderQuanity));

    }

    @Test
    public void when_stock_is_0_Should_give_0(){
        // Arrange
        ProductSpecificationType dummyProductType = new ProductSpecificationType("name", 0, "img" , 50.00, null);
        UUID typeId = UUID.randomUUID();

        when(this.productSpecificationTypesRepository.findById(typeId)).thenReturn(Optional.of(dummyProductType));

        int expectedQuanity = 0;


        // Act
        int OrderQuanity = this.orderService.checkProductQuanity(typeId, 0);

        // Assert
        assertThat(expectedQuanity, is(OrderQuanity));
    }
}
