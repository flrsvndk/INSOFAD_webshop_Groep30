package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.ProductDAO;
import com.example.todoappdeel3.dao.RetourDAO;
import com.example.todoappdeel3.dto.RetourRequestDTO;
import com.example.todoappdeel3.models.OrderItem;
import com.example.todoappdeel3.models.Product;
import com.example.todoappdeel3.models.ProductSpecificationType;
import com.example.todoappdeel3.models.RetourRequest;
import com.example.todoappdeel3.repositories.OrderItemRepository;
import com.example.todoappdeel3.repositories.RetourRequestRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.todoappdeel3.utils.StaticDetails;
import org.junit.jupiter.api.Test;

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
    private OrderItemRepository orderItemRepository
            ;
    @Mock
    private ProductDAO productDAO;
    @InjectMocks
    private RetourDAO retourDAO;

    private RetourRequestDTO dummyRetourRequestDTO;
    private RetourRequest dummyRetourRequest;
    private Set<OrderItem> dummyRetouredProducts;
    private ProductSpecificationType dummyProduct;

    @BeforeEach
    public void setup() {
        this.dummyRetourRequestDTO = new RetourRequestDTO();
        this.dummyRetourRequestDTO.id = UUID.randomUUID();

        this.dummyProduct = new ProductSpecificationType();
        this.dummyProduct.setId(UUID.randomUUID());
        OrderItem dummyRetouredProduct = new OrderItem();
        dummyRetouredProduct.setProductType(dummyProduct);
        this.dummyRetouredProducts = new HashSet<>();
        this.dummyRetouredProducts.add(dummyRetouredProduct);
        this.dummyRetourRequest = new RetourRequest();
        this.dummyRetourRequest.setRetouredProducts(dummyRetouredProducts);
    }

    @Test
    public void should_set_status_accepted_when_called() {
        setupMocksForValidRetourRequest();

        RetourRequest actualRetourRequest = retourDAO.acceptRetourRequest(dummyRetourRequestDTO);

        assertRetourRequestAccepted(actualRetourRequest);
    }
    //
//    @Test
//    public void should_throw_404_when_request_is_not_found() {
//        String expectedExceptionMessage = "Request not found";
//
//        when(retourRequestRepository.findById(anyLong())).thenReturn(Optional.empty());
//
//        ResponseStatusException e = assertThrows(ResponseStatusException.class, () -> {
//            retourDAO.acceptRetourRequest(dummyRetourRequestDTO);
//        });
//        assertThat(e.getReason(), is(expectedExceptionMessage));
//        assertThat(e.getStatusCode(), is(HttpStatus.NOT_FOUND));
//    }
//
//    @Test
//    public void should_increment_stock_when_product_set_to_returned() {
//        setupMocksForValidRetourRequest();
//
//        retourDAO.acceptRetourRequest(dummyRetourRequestDTO);
//
//        verify(productDAO, times(1)).incrementProductStock(dummyProduct.getId());
//    }
//
//    @Test
//    public void should_correctly_detect_returned_products_when_called() {
//        setupMocksForValidRetourRequest();
//
//        RetourRequest actualRetourRequest = retourDAO.acceptRetourRequest(dummyRetourRequestDTO);
//
//        for (OrderItem orderProduct : actualRetourRequest.getRetouredProducts()) {
//            assertThat(orderProduct.isReturned(), is(true));
//            verify(productDAO, times(1)).incrementProductStock(orderProduct.getProduct().getId());
//        }
//        verify(orderItemRepository, times(1)).saveAll(dummyRetourRequest.getRetouredProducts());
//    }
//
    private void setupMocksForValidRetourRequest() {
        when(retourRequestRepository.findById(any())).thenReturn(Optional.of(dummyRetourRequest));
        doNothing().when(productDAO).incrementProductStock(any());
    }

    private void assertRetourRequestAccepted(RetourRequest actualRetourRequest) {
        String expectedStatus = StaticDetails.RETOUR_ACCEPTED;

        assertThat(actualRetourRequest, is(dummyRetourRequest));
        assertThat(actualRetourRequest.getState(), is(expectedStatus));
        verify(retourRequestRepository, times(1)).findById(any());
        verify(retourRequestRepository, times(1)).save(dummyRetourRequest);
        verify(productDAO, times(1)).incrementProductStock(dummyProduct.getId());
        verify(orderItemRepository, times(1)).saveAll(dummyRetouredProducts);
    }
}
