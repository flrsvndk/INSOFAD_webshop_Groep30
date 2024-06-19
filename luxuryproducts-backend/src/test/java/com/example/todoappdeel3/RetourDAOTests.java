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

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RetourDAOTests {

    @Mock
    private RetourRequestRepository retourRequestRepository;
    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private RetourDAO retourDAO;
    @Mock
    private ProductDAO productDAO;

    private RetourRequestDTO dummyRetourRequestDTO;
    private RetourRequest dummyRetourRequest;
    private OrderItem dummyRetouredProduct;
    private Set<OrderItem> dummyRetouredProducts;
    private ProductSpecificationType dummyProduct;


    @BeforeEach
    public void setup() {
        dummyRetourRequestDTO = new RetourRequestDTO();
        dummyRetourRequestDTO.id = UUID.randomUUID();

        dummyProduct = new ProductSpecificationType();
        dummyProduct.setId(UUID.randomUUID());

        dummyRetouredProduct = new OrderItem();
        dummyRetouredProduct.setProductType(dummyProduct);

        dummyRetouredProducts = new HashSet<>();
        dummyRetouredProducts.add(dummyRetouredProduct);

        dummyRetourRequest = new RetourRequest();
        dummyRetourRequest.setRetouredProducts(dummyRetouredProducts);
    }

    //  USER STORY #60
    //  Admin retours overzicht

    //  TEST Retour verzoek beoordelen (Task #70)
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

    //  TEST retourverzoek zoeken op id (Task #68)
    @Test
    public void should_return_request_when_given_valid_id() {

    }

    @Test
    public void should_return_nothing_when_given_invalid_id() {

    }

    @Test
    public void should_return_nothing_when_given_no_id() {

    }

    //  Test retourverzoeken terugvinden (Task #69)
    @Test
    public void should_return_all_requests_when_called() {
        when(this.retourRequestRepository.findAll()).thenReturn(List.of(this.dummyRetourRequest));
        List<RetourRequest> expectedRetourRequests = List.of(this.dummyRetourRequest);

        List<RetourRequest> actualRetourRequests = this.retourDAO.getAllRequests();

        assertThat(actualRetourRequests, is(expectedRetourRequests));
    }

    //  USER STORY #61
    //  Gebruiker orders overzicht

    //  Test: Retourverzoek indienen (Task #72)
    @Test
    public void should_create_request_when_all_inputs_valid() {

    }

    @Test
    public void should_throw_exception_when_not_all_inputs_valid() {

    }

    @Test
    public void should_throw_exception_when_order_id_not_found() {

    }

    //  Test: Keuze uit 1 van de retourredenen (Task #71)
    @Test
    public void should_return_all_reasons() {

    }

    //  Test: 30 dagen retourbeleid (Task #73)
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
