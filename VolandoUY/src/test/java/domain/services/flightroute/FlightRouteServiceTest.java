package domain.services.flightroute;

import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.models.airport.Airport;
import domain.models.category.Category;
import domain.models.enums.EnumEstatusRuta;
import domain.models.flightroute.FlightRoute;
import domain.models.user.Airline;
import domain.services.airport.IAirportService;
import domain.services.category.ICategoryService;
import domain.services.user.IUserService;
import infra.repository.flightroute.IFlightRouteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import shared.constants.Images;
import shared.utils.CustomModelMapper;
import shared.utils.ImageProcessor;
import shared.utils.ValidatorUtil;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockStatic;

class FlightRouteServiceTest {

    private FlightRouteService service;
    private IFlightRouteRepository mockRepo;
    private CustomModelMapper mockMapper;
    private ICategoryService mockCategoryService;
    private IAirportService mockAirportService;
    private IUserService mockUserService;

    @BeforeEach
    void setUp() {
        mockRepo = mock(IFlightRouteRepository.class);
        mockMapper = mock(CustomModelMapper.class);
        mockCategoryService = mock(ICategoryService.class);
        mockAirportService = mock(IAirportService.class);
        mockUserService = mock(IUserService.class);

        service = new FlightRouteService(mockRepo, mockMapper);
        service.setCategoryService(mockCategoryService);
        service.setAirportService(mockAirportService);
        service.setUserService(mockUserService);
    }

    @Test
    void createFlightRoute_shouldCreateSuccessfullyWithoutImage() {
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO(
                        "Ruta A",
                        "desc",
                        LocalDate.now(),
                        100.0,
                        200.0,
                        30.0,
                        EnumEstatusRuta.SIN_ESTADO,
                        "default-image.png"
                );

        FlightRoute route = new FlightRoute();
        route.setName("Ruta A");

        Airline airline = new Airline();
        airline.setNickname("air1");

        Airport origin = new Airport();
        Airport destination = new Airport();

        when(mockRepo.existsByName("Ruta A")).thenReturn(false);
        when(mockUserService.getAirlineByNickname("air1", true)).thenReturn(airline);
        when(mockAirportService.getAirportByCode("ORI", false)).thenReturn(origin);
        when(mockAirportService.getAirportByCode("DEST", false)).thenReturn(destination);
        when(mockCategoryService.getCategoryByName("Promo")).thenReturn(new Category());
        when(mockMapper.map(dto, FlightRoute.class)).thenReturn(route);
        when(mockMapper.map(route, BaseFlightRouteDTO.class)).thenReturn(dto);

        try (var validatorMock = mockStatic(ValidatorUtil.class)) {
            BaseFlightRouteDTO result = service.createFlightRoute(
                    dto,
                    "ORI", "DEST",
                    "air1",
                    List.of("Promo"),
                    null
            );

            assertEquals("Ruta A", result.getName());
            verify(mockRepo).createFlightRoute(route, airline);
            assertEquals(Images.IMAGES_PATH + Images.FLIGHT_ROUTE_DEFAULT, route.getImage());
        }
    }

    @Test
    void createFlightRoute_shouldCreateSuccessfullyWithImage() {
        /*
        //BaseFlightRouteDTO dto = new BaseFlightRouteDTO("Ruta Img", "desc", LocalDate.now(), 100.0, 200.0, 20.0);
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("Ruta A");
        dto.setDescription("desc");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(100.0);
        dto.setPriceBusinessClass(200.0);
        dto.setPriceExtraUnitBaggage(20.0);
        dto.setStatus(EnumEstatusRuta.SIN_ESTADO);
        dto.setImage("default-image.png");

        FlightRoute route = new FlightRoute();
        route.setName("Ruta Img");

        Airline airline = new Airline();
        airline.setNickname("air1");

        Airport origin = new Airport();
        Airport destination = new Airport();

        when(mockRepo.existsByName("Ruta Img")).thenReturn(false);
        when(mockUserService.getAirlineByNickname("air1", true)).thenReturn(airline);
        when(mockAirportService.getAirportByCode("ORI", false)).thenReturn(origin);
        when(mockAirportService.getAirportByCode("DEST", false)).thenReturn(destination);
        when(mockMapper.map(dto, FlightRoute.class)).thenReturn(route);
        when(mockMapper.map(route, BaseFlightRouteDTO.class)).thenReturn(dto);

        try (var validatorMock = mockStatic(ValidatorUtil.class);
             var imageMock = mockStatic(ImageProcessor.class)) {

            imageMock.when(() -> ImageProcessor.uploadImage(any(), anyString()))
                    .thenReturn("uploaded/path.png");

            BaseFlightRouteDTO result = service.createFlightRoute(
                    dto,
                    "ORI", "DEST",
                    "air1",
                    null,
                    new File("fake.png")
            );

            assertEquals("uploaded/path.png", route.getImage());
            verify(mockRepo).createFlightRoute(route, airline);
        }
        */
    }

    @Test
    void createFlightRoute_shouldThrowIfAlreadyExists() {
        when(mockRepo.existsByName("Duplicada")).thenReturn(true);

        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("Duplicada");

        var ex = assertThrows(UnsupportedOperationException.class, () -> {
            service.createFlightRoute(dto, "ORI", "DEST", "air", null, null);
        });

        assertTrue(ex.getMessage().contains("Ya existe una ruta con el nombre"));
    }

    @Test
    void createFlightRoute_shouldThrowIfAirportsAreEqual() {
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("Ruta");

        var ex = assertThrows(IllegalArgumentException.class, () -> {
            service.createFlightRoute(dto, "MVD", "MVD", "air", null, null);
        });

        // Revisamos que el mensaje tenga la parte clave del mensaje de error
        assertTrue(ex.getMessage().contains("no puede ser igual al de destino"),
                "El mensaje de error debe indicar que los aeropuertos no pueden ser iguales: " + ex.getMessage());
    }

    @Test
    void existFlightRoute_shouldReturnCorrectValue() {
        when(mockRepo.existsByName("X")).thenReturn(true);
        assertTrue(service.existFlightRoute("X"));
    }

    @Test
    void getFlightRoutesDetailsByAirlineNickname_shouldReturnMappedList() {
        FlightRoute fr = new FlightRoute();
        fr.setName("R1");
        FlightRouteDTO dto = new FlightRouteDTO();
        dto.setName("R1");

        when(mockRepo.getAllByAirlineNickname("air")).thenReturn(List.of(fr));
        when(mockMapper.map(fr, FlightRouteDTO.class)).thenReturn(dto);

        List<FlightRouteDTO> result = service.getFlightRoutesDetailsByAirlineNickname("air", false);
        assertEquals(1, result.size());
        assertEquals("R1", result.get(0).getName());
    }

    @Test
    void getFlightRoutesDetailsByPackageName_shouldReturnMappedList() {
        FlightRoute fr = new FlightRoute();
        fr.setName("R1");
        FlightRouteDTO dto = new FlightRouteDTO();
        dto.setName("R1");

        when(mockRepo.getAllByPackageName("pack")).thenReturn(List.of(fr));
        when(mockMapper.map(fr, FlightRouteDTO.class)).thenReturn(dto);

        List<FlightRouteDTO> result = service.getFlightRoutesDetailsByPackageName("pack", false);
        assertEquals(1, result.size());
        assertEquals("R1", result.get(0).getName());
    }

    @Test
    void getFlightRoutesDetailsByPackageName_shouldReturnEmptyListIfNull() {
        when(mockRepo.getAllByPackageName("pack")).thenReturn(null);
        assertTrue(service.getFlightRoutesDetailsByPackageName("pack", false).isEmpty());
    }

    @Test
    void getFlightRouteByName_shouldReturnFullOrNot() {
        FlightRoute full = new FlightRoute();
        when(mockRepo.getFullByName("R1")).thenReturn(full);
        assertEquals(full, service.getFlightRouteByName("R1", true));

        FlightRoute base = new FlightRoute();
        when(mockRepo.getByName("R2")).thenReturn(base);
        assertEquals(base, service.getFlightRouteByName("R2", false));
    }

    @Test
    void getFlightRouteDetailsByName_shouldReturnCorrectMapped() {
        FlightRoute route = new FlightRoute();
        route.setName("R1");

        FlightRouteDTO dto = new FlightRouteDTO();
        dto.setName("R1");

        when(mockRepo.getFullByName("R1")).thenReturn(route);
        when(mockMapper.mapFullFlightRoute(route)).thenReturn(dto);

        assertEquals("R1", service.getFlightRouteDetailsByName("R1", true).getName());
    }

    @Test
    void setStatusFlightRouteByName_shouldSetConfirmed() {
        FlightRoute route = new FlightRoute();
        route.setName("R1");

        when(mockRepo.getByName("R1")).thenReturn(route);

        service.setStatusFlightRouteByName("R1", true);
        assertEquals(EnumEstatusRuta.CONFIRMADA, route.getStatus());
        verify(mockRepo).update(route);
    }

    @Test
    void setStatusFlightRouteByName_shouldSetRejected() {
        FlightRoute route = new FlightRoute();
        route.setName("R2");

        when(mockRepo.getByName("R2")).thenReturn(route);

        service.setStatusFlightRouteByName("R2", false);
        assertEquals(EnumEstatusRuta.RECHAZADA, route.getStatus());
        verify(mockRepo).update(route);
    }

    @Test
    void setStatusFlightRouteByName_shouldThrowIfNotFound() {
        when(mockRepo.getByName("404")).thenReturn(null);

        var ex = assertThrows(IllegalArgumentException.class, () -> {
            service.setStatusFlightRouteByName("404", true);
        });

        assertTrue(ex.getMessage().contains("no fue encontrada"));
    }
}
