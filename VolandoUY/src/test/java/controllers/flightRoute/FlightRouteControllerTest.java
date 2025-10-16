package controllers.flightRoute;

import controllers.airport.IAirportController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.user.IUserController;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.services.flightRoute.FlightRouteService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FlightRouteControllerTest {

    private IFlightRouteController flightRouteController;
    private IUserController userController;
    private ICategoryController categoryController;
    private ICityController cityController;
    private IAirportController airportController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        flightRouteController = ControllerFactory.getFlightRouteController();
        userController = ControllerFactory.getUserController();
        categoryController = ControllerFactory.getCategoryController();
        cityController = ControllerFactory.getCityController();
        airportController = ControllerFactory.getAirportController();

        //Crear aereolinea LAT123
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("LAT123");
        airlineDTO.setName("LATAM");
        airlineDTO.setPassword("password");
        airlineDTO.setMail("a@gmail.com");
        airlineDTO.setDescription("LATAMfedafewafewafewa");
        airlineDTO.setWeb("https://www.google.com");
        userController.registerAirline(airlineDTO, null);
        //Crear aereolinea LAT123
        AirlineDTO airlineDTO2 = new AirlineDTO();
        airlineDTO2.setNickname("LAT999");
        airlineDTO2.setName("LATAM2");
        airlineDTO2.setMail("a2@gmail.com");
        airlineDTO2.setPassword("password");
        airlineDTO2.setDescription("LATAMfedafewafewafewa");
        airlineDTO2.setWeb("https://www.google.com");
        userController.registerAirline(airlineDTO2, null);

        //Crear categoria

        categoryController.createCategory(new CategoryDTO("Económica"));
        categoryController.createCategory(new CategoryDTO("Business"));


        //Crear aeropuertos
        cityController.createCity(new BaseCityDTO("Montevideo", "Uruguay", -34.9011, -56.1645));
        airportController.createAirport(
                new BaseAirportDTO("Primer Aeropuerto", "MVI"),
                "Montevideo"
        );
        airportController.createAirport(
                new BaseAirportDTO("Segundo Aeropuerto", "MVS"),
                "Montevideo"
        );
        airportController.createAirport(
                new BaseAirportDTO("Tercer Aeropuerto", "MVE"),
                "Montevideo"
        );



        // Preload data
        BaseFlightRouteDTO baseDTO = new BaseFlightRouteDTO();
        baseDTO.setName("Ruta 1");
        baseDTO.setDescription("Ruta de prueba");
        baseDTO.setCreatedAt(LocalDate.now());
        baseDTO.setPriceTouristClass(90.0);
        baseDTO.setPriceBusinessClass(150.0);
        baseDTO.setPriceExtraUnitBaggage(25.0);

        flightRouteController.createFlightRoute(
            baseDTO,
            "MVI",
            "MVS",
            "LAT123",
            List.of("Económica"),
            null
        );
    }

    @Test
    @DisplayName("Debe verificar si existe una ruta por nombre")
    void existFlightRoute() {
        assertTrue(flightRouteController.existFlightRoute("Ruta 1"));
        assertFalse(flightRouteController.existFlightRoute("Desconocida"));
    }

    @Test
    @DisplayName("Debe crear una ruta de vuelo correctamente")
    void createFlightRoute() {
        BaseFlightRouteDTO dto = new BaseFlightRouteDTO();
        dto.setName("Ruta 2");
        dto.setDescription("Nueva ruta");
        dto.setCreatedAt(LocalDate.now());
        dto.setPriceTouristClass(80.0);
        dto.setPriceBusinessClass(130.0);
        dto.setPriceExtraUnitBaggage(20.0);

        BaseFlightRouteDTO created = flightRouteController.createFlightRoute(
                dto,
                "MVI",
                "MVS",
                "LAT999",
                List.of("Business"),
                null
        );

        assertEquals("Ruta 2", created.getName());
        assertTrue(flightRouteController.existFlightRoute("Ruta 2"));
    }

    @Test
    @DisplayName("Debe obtener detalles completos de una ruta")
    void getFlightRouteDetailsByName() {
        FlightRouteDTO dto = flightRouteController.getFlightRouteDetailsByName("Ruta 1");

        assertNotNull(dto);
        assertEquals("Ruta 1", dto.getName());
        assertEquals("MVI", dto.getOriginAeroCode());
        assertEquals("MVS", dto.getDestinationAeroCode());
    }

    @Test
    @DisplayName("Debe obtener detalles simples de una ruta")
    void getFlightRouteSimpleDetailsByName() {
        BaseFlightRouteDTO dto = flightRouteController.getFlightRouteSimpleDetailsByName("Ruta 1");

        assertNotNull(dto);
        assertEquals("Ruta 1", dto.getName());
    }

    @Test
    @DisplayName("Debe obtener todas las rutas de una aerolínea")
    void getAllFlightRoutesByAirlineNickname() {
        List<FlightRouteDTO> rutas = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname("LAT123");

        assertEquals(1, rutas.size());
        assertEquals("Ruta 1", rutas.get(0).getName());
    }

    @Test
    @DisplayName("Debe obtener rutas simples por aerolínea")
    void getAllFlightRoutesSimpleByAirlineNickname() {
        List<BaseFlightRouteDTO> rutas = flightRouteController.getAllFlightRoutesSimpleDetailsByAirlineNickname("LAT123");

        assertEquals(1, rutas.size());
        assertEquals("Ruta 1", rutas.get(0).getName());
    }
}