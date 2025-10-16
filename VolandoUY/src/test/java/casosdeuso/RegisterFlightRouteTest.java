package casosdeuso;

import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
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
//        AirlineDTO airline = new AirlineDTO("AA", "Aerolineas Argentinas", "arg@gmail.com", "Arg airline", "www.aa.com");
//        userController.registerAirline(airline);

//        CityDTO originCity = new CityDTO("Buenos Aires", "Argentina", -34.6, -58.4, List.of("ARG"));
        BaseCityDTO  baseCityDTO = new BaseCityDTO();
        baseCityDTO.setName("Buenos Aires");
        baseCityDTO.setCountry("Argentina");
        baseCityDTO.setLatitude(-34.6);
        baseCityDTO.setLongitude(-58.4);
        cityController.createCity(baseCityDTO);

        BaseCityDTO baseCityDTO2 = new BaseCityDTO();
        baseCityDTO2.setName("Madrid");
        baseCityDTO2.setCountry("España");
        baseCityDTO2.setLatitude(40.4);
        baseCityDTO2.setLongitude(-3.7);
        cityController.createCity(baseCityDTO2);

        CategoryDTO category = new CategoryDTO("Nacional");
        categoryController.createCategory(category);

//        FlightRouteDTO flightRouteDTO = new FlightRouteDTO();
//        flightRouteDTO.setName("TEST");
//        flightRouteDTO.setDescription("Ruta Buenos Aires - Madrid");
//        flightRouteDTO.setCreatedAt(LocalDate.now());
//        flightRouteDTO.setPriceTouristClass(8000.0);
//        flightRouteDTO.setPriceBusinessClass(15000.0);
//        flightRouteDTO.setPriceExtraUnitBaggage(5000.0);
//        flightRouteDTO.setOriginCityName("Buenos Aires");
//        flightRouteDTO.setDestinationCityName("Madrid");
//        flightRouteDTO.setAirlineNickname("AA");
//        flightRouteDTO.setCategories(List.of("Nacional"));

        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("TEST");
        baseFlightRouteDTO.setDescription("Ruta Buenos Aires - Madrid");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(8000.0);
        baseFlightRouteDTO.setPriceBusinessClass(15000.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(5000.0);
        assertFalse(flightRouteController.existFlightRoute("TEST"));

        //  WHEN
//        flightRouteController.createFlightRoute(flightRouteDTO);
//        BaseFlightRouteDTO flightRouteDTO = (BaseFlightRouteDTO) flightRouteController.createFlightRoute(baseFlightRouteDTO, "Buenos Aires", "Madrid", "AA", List.of("Nacional"));

        //  THEN
        assertTrue(flightRouteController.existFlightRoute("TEST"));

        FlightRouteDTO retrieved = flightRouteController.getFlightRouteDetailsByName("TEST");
        assertEquals("TEST", retrieved.getName());
        assertEquals("Buenos Aires", retrieved.getOriginAeroCode());
        assertEquals("Madrid", retrieved.getDestinationAeroCode());
        assertEquals("AA", retrieved.getAirlineNickname());
        assertEquals(List.of("Nacional"), retrieved.getCategories());
    }
}
