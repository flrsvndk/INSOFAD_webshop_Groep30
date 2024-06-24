package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.AdressDAO;
import com.example.todoappdeel3.dao.OrderItemDAO;
import com.example.todoappdeel3.dao.PromocodeDAO;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.dto.OrderItemDTO;
import com.example.todoappdeel3.dto.PromocodeDTO;
import com.example.todoappdeel3.models.*;
import com.example.todoappdeel3.repositories.*;
import com.example.todoappdeel3.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTests {

    @Mock
    private AdressDAO adressDAO;
    @Mock
    private OrderItemDAO orderItemDAO;
    @Mock
    private PromocodeRepository promocodeRepository;
    @Mock
    private ProductSpecificationTypesRepository productSpecificationTypesRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private PromocodeDAO promocodeDAO;

    @InjectMocks
    private OrderService orderService;

    private OrderDTO dummyOrderDTO;
    private PlacedOrder dummyPlacedOrder;
    private Promocode dummyPromocode;
    private CustomUser dummyUser;
    private OrderItem dummyOrderItem;
    private ProductSpecificationType dummyProductSpecificationType;

    @BeforeEach
    public void setup() {
        dummyPromocode = new Promocode();
        dummyPromocode.setId(UUID.randomUUID());
        dummyPromocode.setPercentageOff(10);
        dummyPromocode.setAvailable(true);

        dummyOrderItem = new OrderItem();
        dummyOrderItem.setId(1);

        dummyProductSpecificationType = new ProductSpecificationType();
        dummyProductSpecificationType.setId(UUID.randomUUID());
        dummyProductSpecificationType.setPrice(100.0);

        dummyOrderItem.setProductType(dummyProductSpecificationType);
        dummyOrderItem.setQuantity(1);


        List<OrderItemDTO> dummyOrderItems = new ArrayList<>();
        dummyOrderItems.add(new OrderItemDTO(2, UUID.randomUUID()));

        dummyOrderDTO = new OrderDTO();
        dummyOrderDTO.id = UUID.randomUUID();
        dummyOrderDTO.orderItems = dummyOrderItems;

        dummyUser = new CustomUser();
        dummyUser.setId(UUID.randomUUID());

        dummyPlacedOrder = new PlacedOrder();
        dummyPlacedOrder.setId(UUID.randomUUID());
        dummyPlacedOrder.setPromocode(dummyPromocode);
    }


    @Test
    public void createOrder_shouldReturnAnOrderWithPromocodeOfNull_whenNoPromocodeProvided() {
        dummyOrderDTO.promocode = null;

        when(adressDAO.createAdress(any())).thenReturn(new Adress());
        when(orderItemDAO.createOrderItem(any(), any())).thenReturn(dummyOrderItem);
        when(productSpecificationTypesRepository.findById(any())).thenReturn(Optional.of(dummyProductSpecificationType));

        PlacedOrder createdOrder = orderService.createOrder(dummyOrderDTO, dummyUser);

        assertNull(createdOrder.getPromocode());
    }

    @Test
    public void createOrder_shouldReturnAndOrderWithMatchingPromocode_whenPromocodeProvided() {
        dummyOrderDTO.promocode = new PromocodeDTO();
        dummyOrderDTO.promocode.id = dummyPromocode.getId();
        dummyOrderDTO.promocode.percentageOff = dummyPromocode.getPercentageOff();

        when(adressDAO.createAdress(any())).thenReturn(new Adress());
        when(orderItemDAO.createOrderItem(any(), any())).thenReturn(dummyOrderItem);
        when(productSpecificationTypesRepository.findById(any())).thenReturn(Optional.of(dummyProductSpecificationType));
        when(promocodeRepository.findById(any())).thenReturn(Optional.of(dummyPromocode));

        PlacedOrder createdOrder = orderService.createOrder(dummyOrderDTO, dummyUser);

        assertEquals(dummyPromocode, createdOrder.getPromocode());
    }
}
