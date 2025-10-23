package domain.services.flightroutepackage;

import org.mockito.MockedStatic;
import domain.dtos.flightroutepackage.FlightRoutePackageDTO;
import domain.dtos.flightroutepackage.BaseFlightRoutePackageDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightroute.FlightRoute;
import domain.models.flightroutepackage.FlightRoutePackage;
import domain.services.flightroute.IFlightRouteService;
import infra.repository.flightroutepackage.IFlightRoutePackageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRoutePackageServiceTest {

    private IFlightRoutePackageRepository mockRepo;
    private IFlightRouteService mockFlightRouteService;
    private CustomModelMapper mockMapper;
    private FlightRoutePackageService service;

    @BeforeEach
    void setUp() {
        mockRepo = mock(IFlightRoutePackageRepository.class);
        mockFlightRouteService = mock(IFlightRouteService.class);
        mockMapper = mock(CustomModelMapper.class);

        service = new FlightRoutePackageService(mockRepo, mockMapper);
        service.setFlightRouteService(mockFlightRouteService);
    }


    @Test
    void createFlightRoutePackage_shouldCreateAndReturnDTO() {
        BaseFlightRoutePackageDTO inputDTO = new BaseFlightRoutePackageDTO(
                "PackX", "descripcion123", 10, 5.0, LocalDate.now(), EnumTipoAsiento.TURISTA, 100.0
        );

        FlightRoutePackage domain = new FlightRoutePackage();
        domain.setName("PackX");
        domain.setDescription("descripcion123");
        domain.setSeatType(EnumTipoAsiento.TURISTA);
        domain.setCreationDate(LocalDate.now());
        domain.setTotalPrice(100.0);
        domain.setDiscount(5.0);
        domain.setValidityPeriodDays(10);

        when(mockMapper.map(inputDTO, FlightRoutePackage.class)).thenReturn(domain);
        when(mockRepo.existsByName("PackX")).thenReturn(false);
        when(mockMapper.map(domain, BaseFlightRoutePackageDTO.class)).thenReturn(inputDTO);

        try (MockedStatic<ValidatorUtil> validatorStatic = mockStatic(ValidatorUtil.class)) {
            // No hace nada (salta validación real)
            validatorStatic.when(() -> ValidatorUtil.validate(any())).thenAnswer(inv -> null);

            BaseFlightRoutePackageDTO result = service.createFlightRoutePackage(inputDTO);

            assertNotNull(result);
            assertEquals("PackX", result.getName());
            verify(mockRepo).save(domain);
            validatorStatic.verify(() -> ValidatorUtil.validate(domain));
        }
    }

    @Test
    void createFlightRoutePackage_shouldFailIfAlreadyExists() {
        BaseFlightRoutePackageDTO dto = new BaseFlightRoutePackageDTO();
        dto.setName("Duplicado");

        FlightRoutePackage domain = new FlightRoutePackage();
        domain.setName("Duplicado");

        when(mockMapper.map(dto, FlightRoutePackage.class)).thenReturn(domain);
        when(mockRepo.existsByName("Duplicado")).thenReturn(true);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.createFlightRoutePackage(dto);
        });

        assertEquals(String.format(ErrorMessages.ERR_PACKAGE_ALREADY_EXISTS, "Duplicado"), ex.getMessage());
    }

    @Test
    void getFlightRoutePackageDetailsByName_shouldReturnDTO() {
        FlightRoutePackage pack = new FlightRoutePackage();
        pack.setName("Pack1");
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO();
        dto.setName("Pack1");

        when(mockRepo.findAll()).thenReturn(List.of(pack));
        when(mockRepo.getFullFlightRoutePackageByName("Pack1")).thenReturn(pack);
        when(mockMapper.mapFullFlightRoutePackage(pack)).thenReturn(dto);

        FlightRoutePackageDTO result = service.getFlightRoutePackageDetailsByName("Pack1", true);

        assertNotNull(result);
        assertEquals("Pack1", result.getName());
    }

    @Test
    void getFlightRoutePackageDetailsByName_shouldThrowIfNotFound() {
        when(mockRepo.getFlightRoutePackageByName("Unknown")).thenReturn(null);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.getFlightRoutePackageDetailsByName("Unknown", false);
        });

        assertEquals(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, "Unknown"), ex.getMessage());
    }

    @Test
    void flightRoutePackageExists_shouldReturnCorrectValue() {
        when(mockRepo.existsByName("PackX")).thenReturn(true);
        assertTrue(service.flightRoutePackageExists("PackX"));
    }

    @Test
    void getAllNotBoughtFlightRoutePackagesNames_shouldReturnNames() {
        FlightRoutePackage p1 = new FlightRoutePackage();
        p1.setName("Uno");
        FlightRoutePackage p2 = new FlightRoutePackage();
        p2.setName("Dos");

        when(mockRepo.findAll()).thenReturn(List.of(p1, p2));

        List<String> result = service.getAllNotBoughtFlightRoutePackagesNames();

        assertEquals(List.of("Uno", "Dos"), result);
    }

    @Test
    void addFlightRouteToPackage_shouldUpdatePriceAndSave() {
        FlightRoutePackage pack = new FlightRoutePackage();
        pack.setName("PackZ");
        pack.setSeatType(EnumTipoAsiento.TURISTA);
        pack.setDiscount(10.0);
        pack.setTotalPrice(100.0);

        FlightRoute route = new FlightRoute();
        route.setName("RutaZ");
        route.setPriceTouristClass(200.0);
        route.setPriceBusinessClass(300.0);

        when(mockRepo.getFullFlightRoutePackageByName("PackZ")).thenReturn(pack);
        when(mockFlightRouteService.getFlightRouteByName("RutaZ", false)).thenReturn(route);

        service.addFlightRouteToPackage("PackZ", "RutaZ", 2);

        // Espera que se actualice el precio dos veces
        double expectedPrice = 100.0 + (200.0 * 0.9) * 2;
        assertEquals(expectedPrice, pack.getTotalPrice());

        verify(mockRepo, times(1)).addFlightRouteToPackage(route, pack);
    }

    @Test
    void addFlightRouteToPackage_shouldFailIfInvalidQuantity() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addFlightRouteToPackage("Pack", "Ruta", 0);
        });

        assertEquals(ErrorMessages.ERR_QUANTITY_MUST_BE_GREATER_THAN_ZERO, ex.getMessage());
    }

    @Test
    void addFlightRouteToPackage_shouldFailIfPackageNotFound() {
        when(mockRepo.getFullFlightRoutePackageByName("PackX")).thenReturn(null);

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            service.addFlightRouteToPackage("PackX", "RutaA", 1);
        });

        assertEquals(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_PACKAGE_NOT_FOUND, "PackX"), ex.getMessage());
    }

    @Test
    void getAllFlightRoutePackages_shouldReturnAll() {
        FlightRoutePackage p = new FlightRoutePackage();
        when(mockRepo.findAll()).thenReturn(List.of(p));
        List<FlightRoutePackage> result = service.getAllFlightRoutePackages();
        assertEquals(1, result.size());
    }

    @Test
    void getAllFlightRoutePackagesDetails_shouldMapAll() {
        FlightRoutePackage pack = new FlightRoutePackage();
        pack.setName("PackMapped");
        FlightRoutePackageDTO dto = new FlightRoutePackageDTO();
        dto.setName("PackMapped");

        when(mockRepo.findAll()).thenReturn(List.of(pack));
        when(mockMapper.mapFullFlightRoutePackage(pack)).thenReturn(dto);

        List<FlightRoutePackageDTO> result = service.getAllFlightRoutePackagesDetails(true);

        assertEquals(1, result.size());
        assertEquals("PackMapped", result.get(0).getName());
    }

    @Test
    void getFlightRoutePackageByName_shouldReturnCorrectly() {
        FlightRoutePackage pack = new FlightRoutePackage();
        pack.setName("PackX");

        when(mockRepo.getFlightRoutePackageByName("PackX")).thenReturn(pack);

        FlightRoutePackage result = service.getFlightRoutePackageByName("PackX", false);

        assertNotNull(result);
        assertEquals("PackX", result.getName());
    }

    @Test
    void getFlightRoutePackageByName_fullTrue_shouldReturnCorrectly() {
        FlightRoutePackage pack = new FlightRoutePackage();
        pack.setName("PackFull");

        when(mockRepo.getFlightRoutePackageFullByName("PackFull")).thenReturn(pack);

        FlightRoutePackage result = service.getFlightRoutePackageByName("PackFull", true);

        assertNotNull(result);
        assertEquals("PackFull", result.getName());
    }

    @Test
    void getAllFlightRoutePackagesDetailsWithFlightRoutes_shouldReturnMappedList() {
        FlightRoutePackage pack = new FlightRoutePackage();
        pack.setName("Paquete");

        FlightRoutePackageDTO dto = new FlightRoutePackageDTO();
        dto.setName("Paquete");

        when(mockRepo.findAllFullWithFlightRoutes()).thenReturn(List.of(pack));
        when(mockMapper.mapFullFlightRoutePackage(pack)).thenReturn(dto);

        List<FlightRoutePackageDTO> result = service.getAllFlightRoutePackagesDetailsWithFlightRoutes(true);

        assertEquals(1, result.size());
        assertEquals("Paquete", result.get(0).getName());
    }

    @Test
    void updateFlightRoutePackage_shouldDelegateToRepository() {
        FlightRoutePackage pack = new FlightRoutePackage();
        service._updateFlightRoutePackage(pack);
        verify(mockRepo).update(pack);
    }
}
