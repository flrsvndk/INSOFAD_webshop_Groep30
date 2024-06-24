package com.example.todoappdeel3;

import com.example.todoappdeel3.dao.PromocodeDAO;
import com.example.todoappdeel3.dto.PromocodeDTO;
import com.example.todoappdeel3.models.PlacedOrder;
import com.example.todoappdeel3.models.Promocode;
import com.example.todoappdeel3.repositories.PromocodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PromocodeDAOTests {

    @Mock
    private PromocodeRepository promocodeRepository;

    @InjectMocks
    private PromocodeDAO promocodeDAO;

    private PromocodeDTO dummyPromocodeDTO;
    private Promocode dummyPromocode;
    private UUID dummyId;

    @BeforeEach
    public void setUp() {
        dummyId = UUID.randomUUID();

        dummyPromocodeDTO = new PromocodeDTO();
        dummyPromocodeDTO.id = dummyId;

        dummyPromocode = new Promocode();
        dummyPromocode.setId(dummyId);
    }

    @Test
    public void getAllPromocodes_ShouldReturnListOfPromocodes_WhenPromocodesExist() {
        List<Promocode> expectedPromocodes = Arrays.asList(dummyPromocode, dummyPromocode);

        when(promocodeRepository.findAll()).thenReturn(expectedPromocodes);

        List<Promocode> actualPromocodes = promocodeDAO.getAllPromocodes();

        assertNotNull(actualPromocodes);
        assertEquals(2, actualPromocodes.size());
        assertEquals(expectedPromocodes, actualPromocodes);
    }

    @Test
    public void getAllPromocodes_ShouldReturnEmptyList_WhenNoPromocodesExist() {
        when(promocodeRepository.findAll()).thenReturn(List.of());

        List<Promocode> actualPromocodes = promocodeDAO.getAllPromocodes();

        assertNotNull(actualPromocodes);
        assertTrue(actualPromocodes.isEmpty());
    }

    @Test
    public void createPromocode_ShouldSavePromocode_WhenValidPromocodeDTOProvided() {
        promocodeDAO.createPromocode(dummyPromocodeDTO);

        verify(promocodeRepository, times(1)).save(any(Promocode.class));
    }

    @Test
    public void createPromocode_ShouldThrowException_WhenPromocodeDTONull() {
        assertThrows(NullPointerException.class, () -> promocodeDAO.createPromocode(null));
    }

    @Test
    public void updatePromocode_ShouldUpdateExistingPromocode_WhenValidPromocodeDTOProvided() {
        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.of(dummyPromocode));

        promocodeDAO.updatePromocode(dummyPromocodeDTO);

        verify(promocodeRepository, times(1)).save(dummyPromocode);
        assertEquals(dummyPromocodeDTO.name, dummyPromocode.getName());
        assertEquals(dummyPromocodeDTO.description, dummyPromocode.getDescription());
    }

    @Test
    public void updatePromocode_ShouldThrowException_WhenPromocodeIdNotFound() {
        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> promocodeDAO.updatePromocode(dummyPromocodeDTO));
    }

    @Test
    public void updateAnalyticsPromocode_ShouldUpdateAnalytics_WhenPromocodeExists() {
        PlacedOrder placedOrder = new PlacedOrder();
        placedOrder.setPromocode(dummyPromocode);
        placedOrder.setTotalPriceAfterPromocode(50.0);
        dummyPromocode.setUsages(5);
        dummyPromocode.setTotalPriceOrders(100.0);

        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.of(dummyPromocode));

        promocodeDAO.updateAnalyticsPromocode(placedOrder);

        verify(promocodeRepository, times(1)).save(dummyPromocode);
        assertEquals(6, dummyPromocode.getUsages());
        assertEquals(150.0, dummyPromocode.getTotalPriceOrders());
    }

    @Test
    public void updateAnalyticsPromocode_ShouldThrowException_WhenPromocodeIdNotFound() {
        PlacedOrder placedOrder = new PlacedOrder();
        placedOrder.setPromocode(dummyPromocode);
        placedOrder.setTotalPriceAfterPromocode(50.0);

        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> promocodeDAO.updateAnalyticsPromocode(placedOrder));
    }

    @Test
    public void getPromocodeById_ShouldReturnPromocode_WhenIdExists() {
        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.of(dummyPromocode));

        Promocode promocode = promocodeDAO.getPromocodeById(dummyId);

        assertNotNull(promocode);
        assertEquals(dummyPromocode.getId(), promocode.getId());
    }

    @Test
    public void getPromocodeById_ShouldThrowException_WhenIdNotFound() {
        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> promocodeDAO.getPromocodeById(dummyId));
    }

    @Test
    public void archivePromocode_ShouldToggleAvailability_WhenIdExists() {
        dummyPromocode.setAvailable(false);

        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.of(dummyPromocode));

        boolean result = promocodeDAO.archivePromocode(dummyId);

        verify(promocodeRepository, times(1)).save(dummyPromocode);
        assertNotEquals(!dummyPromocode.isAvailable(), result);
    }

    @Test
    public void archivePromocode_ShouldThrowException_WhenIdNotFound() {
        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> promocodeDAO.archivePromocode(dummyId));
    }

    @Test
    public void deletePromocode_ShouldRemovePromocode_WhenIdExists() {
        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.of(dummyPromocode));

        promocodeDAO.deletePromocode(dummyId);

        verify(promocodeRepository, times(1)).delete(dummyPromocode);
    }

    @Test
    public void deletePromocode_ShouldThrowException_WhenIdNotFound() {
        when(promocodeRepository.findById(dummyId)).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> promocodeDAO.deletePromocode(dummyId));
    }
}
