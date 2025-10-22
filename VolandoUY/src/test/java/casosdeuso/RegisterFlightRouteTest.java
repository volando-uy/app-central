package casosdeuso;

import controllers.airport.IAirportController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flightroute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import factory.ControllerFactory;
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
    IAirportController airportController = ControllerFactory.getAirportController();

    @BeforeAll
    void cleanDB() {
        TestUtils.cleanDB();
    }

    @Test
    @DisplayName("GIVEN valid airline, cities and category WHEN creating a flight route THEN it is saved and retrievable")
    public void testCrearRutaVuelo() {
        // GIVEN
        AirlineDTO airline = new AirlineDTO("AA", "Aerolineas Argentinas", "arg@gmail.com", "password", null, "Arg airline", "www.aa.com");
        userController.registerAirline(airline, null);

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

        airportController.createAirport(
                new BaseAirportDTO("Aero Buenos Aires", "BAA"),
                "Buenos Aires"
        );
        airportController.createAirport(
                new BaseAirportDTO("Aero Madrid", "MAD"),
                "Madrid"
        );

        CategoryDTO category = new CategoryDTO("Nacional");
        categoryController.createCategory(category);

        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("TEST");
        baseFlightRouteDTO.setDescription("Ruta Buenos Aires - Madrid");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(8000.0);
        baseFlightRouteDTO.setPriceBusinessClass(15000.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(5000.0);
        assertFalse(flightRouteController.existFlightRoute("TEST"));

        //  WHEN
        flightRouteController.createFlightRoute(baseFlightRouteDTO, "BAA", "MAD", "AA", List.of("Nacional"), null);

        //  THEN
        assertTrue(flightRouteController.existFlightRoute("TEST"));

        FlightRouteDTO retrieved = flightRouteController.getFlightRouteDetailsByName("TEST");
        assertEquals("TEST", retrieved.getName());
        assertEquals("BAA", retrieved.getOriginAeroCode());
        assertEquals("MAD", retrieved.getDestinationAeroCode());
        assertEquals("AA", retrieved.getAirlineNickname());
        assertEquals(List.of("Nacional"), retrieved.getCategoriesNames());
    }
}
