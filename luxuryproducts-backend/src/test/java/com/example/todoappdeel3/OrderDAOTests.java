package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.OrderDAO;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.repositories.OrderRepository;
import com.example.todoappdeel3.utils.StaticDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderDAOTests {

    @Mock
    private OrderRepository orderRepository;
    @InjectMocks
    private OrderDAO orderDAO;

    private OrderDTO dummyOrderDTO;
    private PlacedOrder dummyPlacedOrder;
    private List<PlacedOrder> dummyPlacedOrders;

    @BeforeEach
    public void setup() {
        this.dummyOrderDTO = new OrderDTO();
        this.dummyOrderDTO.id = UUID.randomUUID();

        this.dummyPlacedOrder = new PlacedOrder();

        this.dummyPlacedOrders = new ArrayList<PlacedOrder>();
    }



    //  STORY Admin orders overzicht

    //  TEST Orders terugzien
    @Test
    public void should_return_all_orders_when_called() {
        when(this.orderRepository.findAll()).thenReturn(this.dummyPlacedOrders);

        List<PlacedOrder> actualPlacedOrders = this.orderDAO.getAllOrders();

        assertThat(actualPlacedOrders, is(this.dummyPlacedOrders));
    }



    //  TEST Orderstatus aanpassen
    @Test
    public void should_set_status_processing_when_called() {
        when(this.orderRepository.findById(any())).thenReturn(Optional.of(this.dummyPlacedOrder));
        String expectedStatus = StaticDetails.ORDER_PROCESSING;

        PlacedOrder actualOrder = this.orderDAO.setProcessing(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }

    @Test
    public void should_set_status_confirmed_when_called() {
        when(this.orderRepository.findById(any())).thenReturn(Optional.of(this.dummyPlacedOrder));
        String expectedStatus = StaticDetails.ORDER_CONFIRMED;

        PlacedOrder actualOrder = this.orderDAO.setConfirmed(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }

    @Test
    public void should_set_status_out_for_delivery_when_called() {
        when(this.orderRepository.findById(any())).thenReturn(Optional.of(this.dummyPlacedOrder));
        String expectedStatus = StaticDetails.ORDER_OUT_FOR_DELIVERY;

        PlacedOrder actualOrder = this.orderDAO.setOutForDelivery(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }

    @Test
    public void should_set_status_delivered_when_called() {
        when(this.orderRepository.findById(any())).thenReturn(Optional.of(this.dummyPlacedOrder));
        String expectedStatus = StaticDetails.ORDER_DELIVERED;

        PlacedOrder actualOrder = this.orderDAO.setDelivered(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }
}
