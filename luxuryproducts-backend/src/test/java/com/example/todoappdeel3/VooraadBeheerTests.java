package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.AdressDAO;
import com.example.todoappdeel3.dao.OrderItemDAO;
import com.example.todoappdeel3.dao.PromocodeDAO;
import com.example.todoappdeel3.models.*;
import com.example.todoappdeel3.repositories.OrderItemRepository;
import com.example.todoappdeel3.repositories.ProductSpecificationTypesRepository;
import com.example.todoappdeel3.repositories.PromocodeRepository;
import com.example.todoappdeel3.services.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

public class VooraadBeheerTests {

    @Mock
    private ProductSpecificationTypesRepository productSpecificationTypesRepository;

    @Mock
    private OrderItemRepository orderItemRepository;

    @Mock
    private PromocodeRepository promocodeRepo;

    @Mock
    private PromocodeDAO promocodeDAO;

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
        this.orderService = new OrderService(this.orderItemDAO, this.adressDAO, this.productSpecificationTypesRepository, this.orderItemRepository, this.promocodeRepo, this.promocodeDAO);
    }

    @Test
    public void when_quanity_ordered_more_than_stock_should_return_quanity_is_stock(){
        ProductSpecificationType dummyProductType = new ProductSpecificationType("name", 5, "img" , 50.00, null);
        UUID typeId = UUID.randomUUID();

        when(this.productSpecificationTypesRepository.findById(typeId)).thenReturn(Optional.of(dummyProductType));

        int expectedQuanity = 5;

        int OrderQuanity = this.orderService.checkProductQuanity(typeId, 6);

        assertThat(expectedQuanity, is(OrderQuanity));

    }

    @Test
    public void when_stock_is_0_Should_give_0(){
        ProductSpecificationType dummyProductType = new ProductSpecificationType("name", 0, "img" , 50.00, null);
        UUID typeId = UUID.randomUUID();

        when(this.productSpecificationTypesRepository.findById(typeId)).thenReturn(Optional.of(dummyProductType));

        int expectedQuanity = 0;

        int OrderQuanity = this.orderService.checkProductQuanity(typeId, 0);

        assertThat(expectedQuanity, is(OrderQuanity));
    }
}
