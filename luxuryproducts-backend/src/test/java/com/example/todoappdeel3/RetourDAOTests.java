package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.ProductDAO;
import com.example.todoappdeel3.dao.RetourDAO;
import com.example.todoappdeel3.dto.RetourRequestDTO;
import com.example.todoappdeel3.models.*;
import com.example.todoappdeel3.repositories.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.todoappdeel3.utils.StaticDetails;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetourDAOTests {

    @Mock
    private RetourRequestRepository retourRequestRepository;
    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private RetourReasonRepository retourReasonRepository;

    @InjectMocks
    private RetourDAO retourDAO;
    @Mock
    private ProductDAO productDAO;

    private RetourRequestDTO dummyRetourRequestDTO;
    private RetourRequest dummyRetourRequest;
    private Set<OrderItem> dummyRetouredProducts;
    private OrderItem dummyRetouredProduct;
    private ProductSpecificationType dummyProduct;
    private PlacedOrder dummyPlacedOrder;
    private RetourReason dummyReason;

    @BeforeEach
    public void setup() {
        this.dummyRetourRequestDTO = new RetourRequestDTO();
        this.dummyRetourRequestDTO.id = UUID.randomUUID();

        this.dummyProduct = new ProductSpecificationType();
        this.dummyProduct.setId(UUID.randomUUID());
        this.dummyRetouredProduct = new OrderItem();
        this.dummyRetouredProduct.setProductType(dummyProduct);
        this.dummyRetouredProducts = new HashSet<>();
        this.dummyRetouredProducts.add(dummyRetouredProduct);
        this.dummyRetourRequest = new RetourRequest();
        this.dummyRetourRequest.setRetouredProducts(dummyRetouredProducts);

        when(retourRequestRepository.findById(any())).thenReturn(Optional.of(dummyRetourRequest));
        doNothing().when(productDAO).incrementProductStock(any());
    }

    //  USER STORY #60
    //  Admin retours overzicht

    // TEST Retour verzoek beoordelen (Task #70)
    @Test
    public void should_set_status_accepted_when_called() {
        String expectedStatus = StaticDetails.RETOUR_ACCEPTED;

        RetourRequest actualRetourRequest = retourDAO.acceptRetourRequest(dummyRetourRequestDTO);

        assertThat(actualRetourRequest, is(dummyRetourRequest));
        assertThat(actualRetourRequest.getState(), is(expectedStatus));
        verify(retourRequestRepository, times(1)).findById(any());
        verify(retourRequestRepository, times(1)).save(dummyRetourRequest);
        verify(productDAO, times(1)).incrementProductStock(dummyProduct.getId());
        verify(orderItemRepository, times(1)).saveAll(dummyRetouredProducts);
    }

    @Test void should_set_status_declined_when_called() {

    }

    // TEST retourverzoek zoeken op id (Task #68)
    @Test
    public void should_return_request_when_given_valid_id() {

    }

    @Test
    public void should_return_nothing_when_given_invalid_id() {

    }

    @Test
    public void should_return_nothing_when_given_no_id() {

    }

    // Test retourverzoeken terugvinden (Task #69)
    @Test
    public void should_return_all_requests_when_called() {

    }

    //  USER STORY #61
    //  Gebruiker orders overzicht

    // Test: Retourverzoek indienen (Task #72)
    @Test
    public void should_create_request_when_all_inputs_valid() {

    }

    @Test
    public void should_throw_exception_when_not_all_inputs_valid() {

    }

    @Test
    public void should_throw_exception_when_order_id_not_found() {

    }

    // Test: Keuze uit 1 van de retourredenen (Task #71)
    @Test
    public void should_return_all_reasons() {

    }

    // Test: 30 dagen retourbeleid (Task #73)
    @Test
    public void should_throw_exception_when_ordered_31_days_ago() {

    }

    @Test
    public void should_not_throw_exception_when_ordered_30_days_ago() {

    }

    @Test
    public void should_not_throw_exception_when_ordered_29_days_ago() {

    }
}
