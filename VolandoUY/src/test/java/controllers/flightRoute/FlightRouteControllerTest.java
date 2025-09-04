package controllers.flightRoute;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.user.IUserController;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.user.Airline;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FlightRouteControllerTest {

    private IFlightRouteController controller;
    private IUserController userController;
    private ICategoryController categoryController;
    private ICityController cityController;
    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        controller = new FlightRouteController(new FlightRouteService());
        userController = ControllerFactory.getUserController();
        categoryController = ControllerFactory.getCategoryController();
        cityController = ControllerFactory.getCityController();
        //Crear aereolinea LAT123
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("LAT123");
        airlineDTO.setName("LATAM");
        airlineDTO.setMail("a@gmail.com");
        airlineDTO.setDescription("LATAMfedafewafewafewa");
        airlineDTO.setWeb("https://www.google.com");
        userController.registerAirline(airlineDTO);
        //Crear aereolinea LAT123
        AirlineDTO airlineDTO2 = new AirlineDTO();
        airlineDTO2.setNickname("LAT999");
        airlineDTO2.setName("LATAM2");
        airlineDTO2.setMail("a2@gmail.com");
        airlineDTO2.setDescription("LATAMfedafewafewafewa");
        airlineDTO2.setWeb("https://www.google.com");
        userController.registerAirline(airlineDTO2);

        //Crear categoria

        categoryController.createCategory(new CategoryDTO("Económica"));
        categoryController.createCategory(new CategoryDTO("Business"));


        //Crear ciudades
        cityController.createCity(new BaseCityDTO("Montevideo", "Uruguay", -34.9011, -56.1645));
        cityController.createCity(new BaseCityDTO("Santiago", "Chile", -33.4489, -70.6693));
        cityController.createCity(new BaseCityDTO("Buenos Aires", "Argentina", -34.6037, -58.3816));
        cityController.createCity(new BaseCityDTO("Lima", "Peru", -12.0464, -77.0428));

        // Preload data
        BaseFlightRouteDTO baseDTO = new BaseFlightRouteDTO();
        baseDTO.setName("Ruta 1");
        baseDTO.setDescription("Ruta de prueba");
        baseDTO.setCreatedAt(LocalDate.now());
        baseDTO.setPriceTouristClass(90.0);
        baseDTO.setPriceBusinessClass(150.0);
        baseDTO.setPriceExtraUnitBaggage(25.0);

        controller.createFlightRoute(baseDTO, "Montevideo", "Santiago", "LAT123", List.of("Económica"));
    }

    @Test
    @DisplayName("Debe verificar si existe una ruta por nombre")
    void existFlightRoute() {
        assertTrue(controller.existFlightRoute("Ruta 1"));
        assertFalse(controller.existFlightRoute("Desconocida"));
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

        BaseFlightRouteDTO created = controller.createFlightRoute(dto, "Buenos Aires", "Lima", "LAT999", List.of("Business"));

        assertEquals("Ruta 2", created.getName());
        assertTrue(controller.existFlightRoute("Ruta 2"));
    }

    @Test
    @DisplayName("Debe obtener detalles completos de una ruta")
    void getFlightRouteDetailsByName() {
        FlightRouteDTO dto = controller.getFlightRouteDetailsByName("Ruta 1");

        assertNotNull(dto);
        assertEquals("Ruta 1", dto.getName());
        assertEquals("Montevideo", dto.getOriginCityName());
        assertEquals("Santiago", dto.getDestinationCityName());
    }

    @Test
    @DisplayName("Debe obtener detalles simples de una ruta")
    void getFlightRouteSimpleDetailsByName() {
        BaseFlightRouteDTO dto = controller.getFlightRouteSimpleDetailsByName("Ruta 1");

        assertNotNull(dto);
        assertEquals("Ruta 1", dto.getName());
    }

    @Test
    @DisplayName("Debe obtener todas las rutas de una aerolínea")
    void getAllFlightRoutesByAirlineNickname() {
        List<FlightRouteDTO> rutas = controller.getAllFlightRoutesDetailsByAirlineNickname("LAT123");

        assertEquals(1, rutas.size());
        assertEquals("Ruta 1", rutas.get(0).getName());
    }

    @Test
    @DisplayName("Debe obtener rutas simples por aerolínea")
    void getAllFlightRoutesSimpleByAirlineNickname() {
        List<BaseFlightRouteDTO> rutas = controller.getAllFlightRoutesSimpleDetailsByAirlineNickname("LAT123");

        assertEquals(1, rutas.size());
        assertEquals("Ruta 1", rutas.get(0).getName());
    }
}