package controllers.buypackage;

import domain.dtos.buypackage.BaseBuyPackageDTO;
import domain.dtos.buypackage.BuyPackageDTO;
import domain.services.buypackage.IBuyPackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyPackageControllerTest {

    @Mock
    private IBuyPackageService buyPackageService;

    @InjectMocks
    private BuyPackageController controller;

    @Test
    void createBuyPackage_shouldCallServiceAndReturnDTO() {
        // Given
        String customer = "pepe";
        String packageName = "promoRuta1";
        BaseBuyPackageDTO expected = new BaseBuyPackageDTO();
        expected.setId(1L);
        expected.setTotalPrice(150.0);
        expected.setCreatedAt(LocalDateTime.now());

        when(buyPackageService.createBuyPackage(customer, packageName)).thenReturn(expected);

        // When
        BaseBuyPackageDTO result = controller.createBuyPackage(customer, packageName);

        // Then
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(150.0, result.getTotalPrice());
        verify(buyPackageService).createBuyPackage(customer, packageName);
    }

    @Test
    void getBuyPackageDetailsById_shouldReturnFullDTO() {
        // Given
        Long id = 1L;
        BuyPackageDTO dto = new BuyPackageDTO();
        dto.setId(id);

        when(buyPackageService.getBuyPackageDetailsById(id, true)).thenReturn(dto);

        // When
        BuyPackageDTO result = controller.getBuyPackageDetailsById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(buyPackageService).getBuyPackageDetailsById(id, true);
    }

    @Test
    void getBuyPackageSimpleDetailsById_shouldReturnSimpleDTO() {
        // Given
        Long id = 2L;
        BaseBuyPackageDTO dto = new BaseBuyPackageDTO();
        dto.setId(id);

        BuyPackageDTO detailedDto = new BuyPackageDTO();
        detailedDto.setId(id);

        when(buyPackageService.getBuyPackageDetailsById(id, false)).thenReturn(detailedDto);

        // When
        BaseBuyPackageDTO result = controller.getBuyPackageSimpleDetailsById(id);

        // Then
        assertNotNull(result);
        assertEquals(id, result.getId());
        verify(buyPackageService).getBuyPackageDetailsById(id, false);
    }
}
