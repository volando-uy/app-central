package casosdeuso;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import factory.ControllerFactory;
import jakarta.persistence.EntityManager;
import app.DBConnection;
import org.junit.jupiter.api.*;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RegisterFlightRouteTest {

    IUserController userController = ControllerFactory.getUserController();
    IFlightRouteController flightRouteController = ControllerFactory.getFlightRouteController();
    ICityController cityController = ControllerFactory.getCityController();
    ICategoryController categoryController = ControllerFactory.getCategoryController();

    @BeforeAll
    void cleanDB() {
        TestUtils.cleanDB();
    }

    @Test
    @DisplayName("GIVEN valid airline, cities and category WHEN creating a flight route THEN it is saved and retrievable")
    public void testCrearRutaVuelo() {
        // GIVEN
        AirlineDTO airline = new AirlineDTO("AA", "Aerolineas Argentinas", "arg@gmail.com", "Arg airline", "www.aa.com");
        userController.registerAirline(airline);

        CityDTO originCity = new CityDTO("Buenos Aires", "Argentina", -34.6, -58.4, List.of("ARG"));
        CityDTO destinationCity = new CityDTO("Madrid", "España", 40.4, -3.7, List.of("MAD"));
        cityController.createCity(originCity);
        cityController.createCity(destinationCity);

        CategoryDTO category = new CategoryDTO("Nacional");
        categoryController.createCategory(category);

        FlightRouteDTO flightRouteDTO = new FlightRouteDTO();
        flightRouteDTO.setName("TEST");
        flightRouteDTO.setDescription("Ruta Buenos Aires - Madrid");
        flightRouteDTO.setCreatedAt(LocalDate.now());
        flightRouteDTO.setPriceTouristClass(8000.0);
        flightRouteDTO.setPriceBusinessClass(15000.0);
        flightRouteDTO.setPriceExtraUnitBaggage(5000.0);
        flightRouteDTO.setOriginCityName("Buenos Aires");
        flightRouteDTO.setDestinationCityName("Madrid");
        flightRouteDTO.setAirlineNickname("AA");
        flightRouteDTO.setCategories(List.of("Nacional"));

        assertFalse(flightRouteController.existFlightRoute("TEST"));

        //  WHEN
        flightRouteController.createFlightRoute(flightRouteDTO);

        //  THEN
        assertTrue(flightRouteController.existFlightRoute("TEST"));

        FlightRouteDTO retrieved = flightRouteController.getFlightRouteByName("TEST");
        assertEquals("TEST", retrieved.getName());
        assertEquals("Buenos Aires", retrieved.getOriginCityName());
        assertEquals("Madrid", retrieved.getDestinationCityName());
        assertEquals("AA", retrieved.getAirlineNickname());
        assertEquals(List.of("Nacional"), retrieved.getCategories());
    }
}
