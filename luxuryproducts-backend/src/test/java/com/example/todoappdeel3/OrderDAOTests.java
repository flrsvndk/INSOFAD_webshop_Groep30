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

    @BeforeEach
    public void setup() {
        this.dummyOrderDTO = new OrderDTO();
        this.dummyOrderDTO.id = UUID.randomUUID();

        this.dummyPlacedOrder = new PlacedOrder();

        when(this.orderRepository.findById(any())).thenReturn(Optional.of(this.dummyPlacedOrder));
    }



    //  USER STORY #59
    //  Admin orders overzicht

    //  TEST Orders terugzien (Task #65)

    //  TEST Orderstatus aanpassen (Task #66)
    @Test
    public void should_set_status_processing_when_called() {
        String expectedStatus = StaticDetails.ORDER_PROCESSING;

        PlacedOrder actualOrder = this.orderDAO.setProcessing(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }

    @Test
    public void should_set_status_confirmed_when_called() {
        String expectedStatus = StaticDetails.ORDER_CONFIRMED;

        PlacedOrder actualOrder = this.orderDAO.setConfirmed(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }

    @Test
    public void should_set_status_out_for_delivery_when_called() {
        String expectedStatus = StaticDetails.ORDER_OUT_FOR_DELIVERY;

        PlacedOrder actualOrder = this.orderDAO.setOutForDelivery(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }

    @Test
    public void should_set_status_delivered_when_called() {
        String expectedStatus = StaticDetails.ORDER_DELIVERED;

        PlacedOrder actualOrder = this.orderDAO.setDelivered(this.dummyOrderDTO);

        assertThat(actualOrder.getStatus(), is(expectedStatus));
    }
}
