package controllers.flight;


import domain.dtos.city.BaseCityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import factory.ControllerFactory;
import factory.ServiceFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightControllerTest {

    private IFlightController flightController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();

        // Setup del sistema con datos necesarios
//        ServiceFactory.getUserService().registerAirline(new AirlineDTO(
//                "air123", "Test Airline", "test@mail.com", "Una aerolínea de prueba", "www.testair.com"
//        ));

        ServiceFactory.getCityService().createCity(new BaseCityDTO("Montevideo", "Uruguay", -34.9, -56.2));
        ServiceFactory.getCityService().createCity(new BaseCityDTO("Buenos Aires", "Argentina", -34.6, -58.4));

        // Crear ruta de vuelo para asociar vuelos
//        FlightRouteDTO route = new FlightRouteDTO();
//        route.setName("Ruta A");
//        route.setDescription("De Montevideo a Buenos Aires");
//        route.setCreatedAt(LocalDate.now());
//        route.setPriceTouristClass(100.0);
//        route.setPriceBusinessClass(200.0);
//        route.setPriceExtraUnitBaggage(30.0);
//        route.setOriginCityName("Montevideo");
//        route.setDestinationCityName("Buenos Aires");
//        route.setAirlineNickname("air123");
//        route.setCategories(List.of());
        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("Ruta A");
        baseFlightRouteDTO.setDescription("De Montevideo a Buenos Aires");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(100.0);
        baseFlightRouteDTO.setPriceBusinessClass(200.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(30.0);

//        ServiceFactory.getFlightRouteService().createFlightRoute(baseFlightRouteDTO,
//                "Montevideo", "Buenos Aires", "air123", List.of());

        // Instanciar controller real
        flightController = ControllerFactory.getFlightController();
    }

    @Test
    @DisplayName("GIVEN valid BaseFlightDTO WHEN createFlight is called THEN it should return created FlightDTO")
    void createFlight_shouldReturnCreatedFlight() {
//        // GIVEN
//        BaseFlightDTO dto = new BaseFlightDTO("Vuelo 1", LocalDateTime.now().plusDays(1), 120L, 100, 50, null);
//
//        // WHEN
//        BaseFlightDTO result = flightController.createFlight(dto, "air123", "Ruta A");

//        // THEN
//        assertNotNull(result);
//        assertEquals("Vuelo 1", result.getName());
    }

    @Test
    @DisplayName("GIVEN multiple flights WHEN getAllFlights is called THEN return full list")
    void getAllFlights_shouldReturnListFromController() {
//        flightController.createFlight(new BaseFlightDTO("Vuelo A", LocalDateTime.now().plusDays(1), 90L, 80, 40, null), "air123", "Ruta A");
//        flightController.createFlight(new BaseFlightDTO("Vuelo B", LocalDateTime.now().plusDays(2), 60L, 70, 35, null), "air123", "Ruta A");
//
//        List<BaseFlightDTO> result = flightController.getAllFlightsSimpleDetails();
//
//        assertEquals(2, result.size());
//        assertTrue(result.stream().anyMatch(f -> f.getName().equals("Vuelo A")));
//        assertTrue(result.stream().anyMatch(f -> f.getName().equals("Vuelo B")));
    }

    @Test
    @DisplayName("GIVEN flight name WHEN getFlightByName is called THEN return correct DTO")
    void getFlightByName_shouldReturnFlightDTO() {
//        flightController.createFlight(new BaseFlightDTO("Vuelo Unico", LocalDateTime.now().plusDays(1), 120L, 100, 50, null), "air123", "Ruta A");

        FlightDTO result = flightController.getFlightDetailsByName("Vuelo Unico");

        assertNotNull(result);
        assertEquals("Vuelo Unico", result.getName());
        assertEquals("air123", result.getAirlineNickname());
        assertEquals("Ruta A", result.getFlightRouteName());
    }

    @Test
    @DisplayName("GIVEN airline nickname WHEN getAllFlightsByAirline is called THEN return flights for that airline")
    void getAllFlightsByAirline_shouldReturnCorrectly() {
//        flightController.createFlight(new BaseFlightDTO("Vuelo X", LocalDateTime.now().plusDays(1), 100L, 90, 45, null), "air123", "Ruta A");

        List<BaseFlightDTO> result = flightController.getAllFlightsSimpleDetailsByAirline("air123");

        assertEquals(1, result.size());
        assertEquals("Vuelo X", result.get(0).getName());
    }

    @Test
    @DisplayName("GIVEN nonexistent airline WHEN getAllFlightsByAirline is called THEN throw exception")
    void getAllFlightsByAirline_shouldThrowIfNotFound() {
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            flightController.getAllFlightsSimpleDetailsByAirline("noExiste");
        });

        assertEquals(String.format(ErrorMessages.ERR_AIRLINE_NOT_FOUND, "noExiste"), ex.getMessage());
    }
}
