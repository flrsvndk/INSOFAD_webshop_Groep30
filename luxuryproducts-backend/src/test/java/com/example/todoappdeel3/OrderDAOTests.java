package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.RetourDAO;
import com.example.todoappdeel3.models.OrderItem;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.repositories.OrderRepository;
import com.example.todoappdeel3.utils.StaticDetails;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.times;

@ExtendWith(MockitoExtension.class)
public class OrderDAOTests {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private RetourDAO retourDAO;

    private PlacedOrder dummyOrder;
    private OrderItem dummyOrderItemTrue;
    private OrderItem dummyOrderItemFalse;
    private Set<OrderItem> dummyOrderItems;

    @BeforeEach
    public void setup() {
        this.dummyOrder = new PlacedOrder();
        this.dummyOrderItemTrue = new OrderItem();
        this.dummyOrderItemFalse = new OrderItem();
        this.dummyOrderItemTrue.setReturned(true);
        this.dummyOrderItemFalse.setReturned(false);
        this.dummyOrderItems = new HashSet<>();
    }



    //  USER STORY #59
    //  Admin orders overzicht

    //  TEST Orders terugzien (Task #65)

    //  TEST Orderstatus aanpassen (Task #66)
    @Test
    public void should_set_partially_returned_when_partially_returned() {
        this.dummyOrderItems.add(dummyOrderItemTrue);
        this.dummyOrderItems.add(dummyOrderItemFalse);
        this.dummyOrder.setOrderItems(List.copyOf(this.dummyOrderItems));
        String expectedStatus = StaticDetails.ORDER_PARTIALLY_RETURNED;

        PlacedOrder result = this.retourDAO.updateOrderStatusAfterRefund(dummyOrder);

        assertThat(result.getStatus(), is(expectedStatus));
        Mockito.verify(orderRepository, times(1)).save(dummyOrder);
    }

    @Test
    public void should_set_returned_when_fully_returned() {
        this.dummyOrderItems.add(dummyOrderItemTrue);
        this.dummyOrder.setOrderItems(List.copyOf(this.dummyOrderItems));
        String expectedStatus = StaticDetails.ORDER_RETURNED;

        PlacedOrder result = this.retourDAO.updateOrderStatusAfterRefund(dummyOrder);

        assertThat(result.getStatus(), is(expectedStatus));
        Mockito.verify(orderRepository, times(1)).save(dummyOrder);
    }
}
