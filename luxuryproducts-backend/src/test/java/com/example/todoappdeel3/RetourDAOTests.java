package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.ProductDAO;
import com.example.todoappdeel3.dao.RetourDAO;
import com.example.todoappdeel3.dto.RetourRequestDTO;
import com.example.todoappdeel3.models.*;
import com.example.todoappdeel3.repositories.*;
import com.example.todoappdeel3.utils.StaticDetails;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetourDAOTests {

    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RetourReasonRepository retourReasonRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private RetourRequestRepository retourRequestRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private ProductDAO productDAO;

    @InjectMocks
    private RetourDAO retourDAO;

    private RetourRequestDTO dummyRetourRequestDTO;
    private RetourRequest dummyRetourRequest;
    private OrderItem dummyRetouredProduct;
    private Set<OrderItem> dummyRetouredProducts;
    private ProductSpecificationType dummyProduct;
    private PlacedOrder dummyPlacedOrder;
    private RetourReason dummyReason;
    private OrderItem dummyOrderItem;


    @BeforeEach
    public void setup() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("dummyUsername", null));

        dummyRetourRequestDTO = new RetourRequestDTO();
        dummyRetourRequestDTO.id = UUID.randomUUID();
        dummyRetourRequestDTO.orderId = UUID.randomUUID();
        dummyRetourRequestDTO.reasonId = UUID.randomUUID();
        dummyRetourRequestDTO.orderItemIds = Collections.singletonList(1L);

        dummyProduct = new ProductSpecificationType();
        dummyProduct.setId(UUID.randomUUID());

        dummyRetouredProduct = new OrderItem();
        dummyRetouredProduct.setProductType(dummyProduct);

        dummyRetouredProducts = new HashSet<>();
        dummyRetouredProducts.add(dummyRetouredProduct);

        dummyRetourRequest = new RetourRequest();
        dummyRetourRequest.setRetouredProducts(dummyRetouredProducts);

        dummyPlacedOrder = new PlacedOrder();
        dummyReason = new RetourReason();
        dummyOrderItem = new OrderItem();
    }



    //  STORY Admin retours overzicht

    //  TEST Retour verzoek beoordelen
    @Test
    public void should_set_status_accepted_when_called() {
        when(retourRequestRepository.findById(any())).thenReturn(Optional.of(dummyRetourRequest));
        doNothing().when(productDAO).incrementProductStock(any());
        String expectedStatus = StaticDetails.RETOUR_ACCEPTED;

        RetourRequest actualRetourRequest = retourDAO.acceptRetourRequest(dummyRetourRequestDTO);

        this.assert_retour_status(actualRetourRequest, expectedStatus);
        verify(productDAO, times(1)).incrementProductStock(dummyProduct.getId());
        verify(orderItemRepository, times(1)).saveAll(dummyRetouredProducts);
    }

    @Test void should_set_status_declined_when_called() {
        when(retourRequestRepository.findById(any())).thenReturn(Optional.of(dummyRetourRequest));
        String expectedStatus = StaticDetails.RETOUR_DECLINED;

        RetourRequest actualRetourRequest = retourDAO.declineRetourRequest(dummyRetourRequestDTO);

        this.assert_retour_status(actualRetourRequest, expectedStatus);
    }

    private void assert_retour_status(RetourRequest actualRetourRequest, String expectedStatus) {
        assertThat(actualRetourRequest, is(dummyRetourRequest));
        assertThat(actualRetourRequest.getState(), is(expectedStatus));
        verify(retourRequestRepository, times(1)).findById(any());
        verify(retourRequestRepository, times(1)).save(dummyRetourRequest);
    }



    //  Test retourverzoeken terugvinden
    @Test
    public void should_return_all_requests_when_called() {
        when(this.retourRequestRepository.findAll()).thenReturn(List.of(this.dummyRetourRequest));
        List<RetourRequest> expectedRetourRequests = List.of(this.dummyRetourRequest);

        List<RetourRequest> actualRetourRequests = this.retourDAO.getAllRequests();

        assertThat(actualRetourRequests, is(expectedRetourRequests));
    }



    //  STORY Gebruiker orders overzicht

    //  Test: Retourverzoek indienen
    @Test
    public void should_create_request_when_all_inputs_valid() {
        setupMocksForValidRequest();

        RetourRequest result = retourDAO.createRetourRequest(dummyRetourRequestDTO);

        assertThat(result.getOrder(), is(dummyPlacedOrder));
        assertThat(result.getReason().getReason(), is(dummyReason.getReason()));
        verify(retourRequestRepository, times(1)).save(result);
    }

    @Test
    public void should_throw_exception_when_order_id_not_found() {
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        String expectedExceptionResponse = "Order not found";

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
            retourDAO.createRetourRequest(dummyRetourRequestDTO);
        });

        assertThat(e.getReason(), is(expectedExceptionResponse));
        assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
    }

    private void setupMocksForValidRequest() {
        dummyPlacedOrder.setOrderDate(LocalDateTime.now());
        dummyReason.setReason("Some valid reason");

        when(orderRepository.findById(any())).thenReturn(Optional.of(dummyPlacedOrder));
        when(retourReasonRepository.findById(any())).thenReturn(Optional.of(dummyReason));
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(dummyOrderItem));
    }



    //  Test: 30 dagen retourbeleid
    @Test
    public void should_throw_exception_when_ordered_31_days_ago() {
        dummyPlacedOrder.setOrderDate(LocalDateTime.now().minusDays(31));
        when(orderRepository.findById(any())).thenReturn(Optional.of(dummyPlacedOrder));
        String expectedExceptionResponse = "Cannot create return request for orders placed more than 30 days ago";

        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
            retourDAO.createRetourRequest(dummyRetourRequestDTO);
        });
        assertThat(e.getReason(), is(expectedExceptionResponse));
        assertThat(e.getStatusCode(), is(HttpStatus.BAD_REQUEST));
    }

    @Test
    public void should_not_throw_exception_when_ordered_30_days_ago() {
        testOrderWithinAllowedReturnPeriod(30);
    }

    @Test
    public void should_not_throw_exception_when_ordered_29_days_ago() {
        testOrderWithinAllowedReturnPeriod(29);
    }

    private void testOrderWithinAllowedReturnPeriod(int daysAgo) {
        dummyPlacedOrder.setOrderDate(LocalDateTime.now().minusDays(daysAgo));
        dummyReason.setReason("Van gedachten veranderd");

        when(orderRepository.findById(any())).thenReturn(Optional.of(dummyPlacedOrder));
        when(retourReasonRepository.findById(any())).thenReturn(Optional.of(dummyReason));
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.of(dummyOrderItem));

        assertDoesNotThrow(() -> {
            retourDAO.createRetourRequest(dummyRetourRequestDTO);
        });
    }
}
