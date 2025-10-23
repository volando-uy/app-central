package controllers.flightroutepackage;

import domain.dtos.flightroutepackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.services.flightroutepackage.IFlightRoutePackageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRoutePackageControllerTest {

    private IFlightRoutePackageService mockService;
    private FlightRoutePackageController controller;

    @BeforeEach
    void setUp() {
        mockService = mock(IFlightRoutePackageService.class);
        controller = new FlightRoutePackageController(mockService);
    }

    @Test
    void createFlightRoutePackage_shouldDelegateToService() {
        BaseFlightRoutePackageDTO dto = new BaseFlightRoutePackageDTO(
                "Pack1", "desc", 5, 10.0, LocalDate.now(), EnumTipoAsiento.TURISTA, 120.0
        );

        when(mockService.createFlightRoutePackage(dto)).thenReturn(dto);

        BaseFlightRoutePackageDTO result = controller.createFlightRoutePackage(dto);

        assertNotNull(result);
        assertEquals("Pack1", result.getName());
        verify(mockService).createFlightRoutePackage(dto);
    }

    @Test
    void getFlightRoutePackageDetailsByName_shouldReturnFullDetails() {
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO();
        dto.setName("Promo1");

        when(mockService.getFlightRoutePackageDetailsByName("Promo1", true)).thenReturn(dto);

        FlightRoutePackageDTO result = controller.getFlightRoutePackageDetailsByName("Promo1");

        assertNotNull(result);
        assertEquals("Promo1", result.getName());
        verify(mockService).getFlightRoutePackageDetailsByName("Promo1", true);
    }

    @Test
    void getFlightRoutePackageSimpleDetailsByName_shouldReturnSimpleDetails() {
        BaseFlightRoutePackageDTO dto = new BaseFlightRoutePackageDTO();
        dto.setName("SimplePack");

        FlightRoutePackageDTO dtoDetails = new FlightRoutePackageDTO();
        dtoDetails.setName("SimplePack");
        when(mockService.getFlightRoutePackageDetailsByName("SimplePack", false)).thenReturn(dtoDetails);

        BaseFlightRoutePackageDTO result = controller.getFlightRoutePackageSimpleDetailsByName("SimplePack");

        assertNotNull(result);
        assertEquals("SimplePack", result.getName());
        verify(mockService).getFlightRoutePackageDetailsByName("SimplePack", false);
    }

    @Test
    void getAllNotBoughtFlightRoutesPackagesNames_shouldReturnNamesList() {
        List<String> expected = List.of("Pack1", "Pack2");
        when(mockService.getAllNotBoughtFlightRoutePackagesNames()).thenReturn(expected);

        List<String> result = controller.getAllNotBoughtFlightRoutesPackagesNames();

        assertEquals(2, result.size());
        assertTrue(result.contains("Pack1"));
        verify(mockService).getAllNotBoughtFlightRoutePackagesNames();
    }

    @Test
    void addFlightRouteToPackage_shouldCallServiceCorrectly() {
        controller.addFlightRouteToPackage("Pack A", "Ruta 1", 3);

        verify(mockService).addFlightRouteToPackage("Pack A", "Ruta 1", 3);
    }

    @Test
    void flightRoutePackageExists_shouldReturnCorrectly() {
        when(mockService.flightRoutePackageExists("PromoCheck")).thenReturn(true);

        boolean exists = controller.flightRoutePackageExists("PromoCheck");

        assertTrue(exists);
        verify(mockService).flightRoutePackageExists("PromoCheck");
    }

    @Test
    void getAllFlightRoutesPackagesSimpleDetailsWithFlightRoutes_shouldReturnMappedList() {
        BaseFlightRoutePackageDTO dto1 = new BaseFlightRoutePackageDTO();
        dto1.setName("WithRoutes1");
        BaseFlightRoutePackageDTO dto2 = new BaseFlightRoutePackageDTO();
        dto2.setName("WithRoutes2");

        List<FlightRoutePackageDTO> dtoList = List.of(
                new FlightRoutePackageDTO(dto1.getName(), "desc1", 3, 15.0, LocalDate.now(), EnumTipoAsiento.TURISTA, 200.0),
                new FlightRoutePackageDTO(dto2.getName(), "desc2", 2, 20.0, LocalDate.now(), EnumTipoAsiento.EJECUTIVO, 300.0)
        );

        when(mockService.getAllFlightRoutePackagesDetailsWithFlightRoutes(false))
                .thenReturn(dtoList);

        List<BaseFlightRoutePackageDTO> result = controller.getAllFlightRoutesPackagesSimpleDetailsWithFlightRoutes();

        assertEquals(2, result.size());
        assertEquals("WithRoutes1", result.get(0).getName());
        verify(mockService).getAllFlightRoutePackagesDetailsWithFlightRoutes(false);
    }

    @Test
    void getAllFlightRoutesPackages_shouldReturnAllPackages() {
        FlightRoutePackage pack = new FlightRoutePackage();
        pack.setName("CompletePack");

        when(mockService.getAllFlightRoutePackages()).thenReturn(List.of(pack));

        List<FlightRoutePackage> result = controller.getAllFlightRoutesPackages();

        assertEquals(1, result.size());
        assertEquals("CompletePack", result.get(0).getName());
        verify(mockService).getAllFlightRoutePackages();
    }

    @Test
    void getAllFlightRoutesPackagesDetails_shouldReturnFullDTOs() {
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO();
        dto.setName("FullDetailsPack");

        when(mockService.getAllFlightRoutePackagesDetails(true)).thenReturn(List.of(dto));

        List<FlightRoutePackageDTO> result = controller.getAllFlightRoutesPackagesDetails();

        assertEquals(1, result.size());
        assertEquals("FullDetailsPack", result.get(0).getName());
        verify(mockService).getAllFlightRoutePackagesDetails(true);
    }

    @Test
    void getAllFlightRoutesPackagesSimpleDetails_shouldReturnBaseDTOs() {
        BaseFlightRoutePackageDTO dto = new BaseFlightRoutePackageDTO();
        dto.setName("SimpleDetailsPack");

        List<FlightRoutePackageDTO> dtoList = List.of(
                new FlightRoutePackageDTO(dto.getName(), "desc", 4, 12.0, LocalDate.now(), EnumTipoAsiento.TURISTA, 150.0)
        );

        when(mockService.getAllFlightRoutePackagesDetails(false)).thenReturn(dtoList);

        List<BaseFlightRoutePackageDTO> result = controller.getAllFlightRoutesPackagesSimpleDetails();

        assertEquals(1, result.size());
        assertEquals("SimpleDetailsPack", result.get(0).getName());
        verify(mockService).getAllFlightRoutePackagesDetails(false);
    }
}
