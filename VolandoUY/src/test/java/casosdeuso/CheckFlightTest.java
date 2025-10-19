package casosdeuso;

import controllers.airport.IAirportController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class CheckFlightTest {
    private IUserController userController;
    private IFlightController flightController;
    private IFlightRouteController flightRouteController;
    private ICityController cityController;
    private IAirportController airportController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        userController = ControllerFactory.getUserController();
        flightController = ControllerFactory.getFlightController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        cityController = ControllerFactory.getCityController();
        airportController = ControllerFactory.getAirportController();

        BaseAirlineDTO airlineDTO = new BaseAirlineDTO();
        airlineDTO.setNickname("airline1");
        airlineDTO.setName("Aerolíneas Argentinas");
        airlineDTO.setMail("mail@gmail.com");
        airlineDTO.setPassword("password");
        airlineDTO.setDescription("description");
        userController.registerAirline(airlineDTO, null);

        BaseCityDTO cityA = new BaseCityDTO();
        cityA.setName("City A");
        cityA.setCountry("Uruguay");
        cityA.setLatitude(-34.6037);
        cityA.setLongitude(-58.3816);
        cityController.createCity(cityA);

        CityDTO cityB = new CityDTO();
        cityB.setName("City B");
        cityB.setCountry("Uruguay");
        cityB.setLatitude(-34.6037);
        cityB.setLongitude(-58.3816);
        cityController.createCity(cityB);

        airportController.createAirport(
                new BaseAirportDTO("Aero City A", "APA"),
                "City A"
        );
        airportController.createAirport(
                new BaseAirportDTO("Aero City B", "APB"),
                "City B"
        );

        BaseFlightRouteDTO flightRouteDTO = new BaseFlightRouteDTO();
        flightRouteDTO.setName("Route 101");
        flightRouteDTO.setDescription("Route from City A to City B");
        flightRouteDTO.setCreatedAt(LocalDate.now());
        flightRouteDTO.setPriceTouristClass(10.0);
        flightRouteDTO.setPriceBusinessClass(20.0);
        flightRouteDTO.setPriceExtraUnitBaggage(5.0);
        flightRouteController.createFlightRoute(flightRouteDTO, "APA", "APB", "airline1", null, null);


        BaseFlightDTO baseFlightDTO = new BaseFlightDTO();
        baseFlightDTO.setName("Flight 101");
        baseFlightDTO.setDepartureTime(LocalDateTime.of(2026, 8,1,10,0));
        baseFlightDTO.setDuration(180L); // 3 hours
        baseFlightDTO.setMaxEconomySeats(150);
        baseFlightDTO.setMaxBusinessSeats(30);
        baseFlightDTO.setCreatedAt(LocalDateTime.now());
        flightController.createFlight(baseFlightDTO, "airline1", "Route 101", null);

    }


    /**
     * El caso de uso comienza cuando el administrador desea consultar un
     * vuelo. En primer lugar, el sistema lista las aerolíneas, el administrador
     * selecciona la aerolínea que publicó el vuelo y el sistema lista las rutas de
     * vuelo asociadas a dicha aerolínea. El administrador elige una de ellas y el
     * sistema devuelve todos los vuelos asociados a dicha ruta. Finalmente, el
     * administrador elige un vuelo y el sistema devuelve todos los datos del
     * vuelo, incluyendo las reservas asociadas.
     */
    @Test
    void checkFlightTest() {
        //Listar Aerolíneas.
        userController.getAllAirlinesNicknames().forEach(System.out::println);
        assertNotNull(userController.getAllAirlinesNicknames());

        //Seleccionar Aerolínea.
        String airlineNickname = userController.getAllAirlinesNicknames().get(0);
        assertEquals("airline1", airlineNickname);

        //Listar rutas asociadas a dicha aereolinea
        assertNotNull(flightController.getAllFlightsSimpleDetailsByAirline(airlineNickname));

        //Seleccionar ruta de vuelo
        FlightRouteDTO flightRouteDTO = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname).get(0);
        assertEquals("Route 101", flightRouteDTO.getName());
        assertEquals("APA", flightRouteDTO.getOriginAeroCode());
        assertEquals("APB", flightRouteDTO.getDestinationAeroCode());

        //Listar vuelos asociados a dicha ruta
        assertNotNull(flightController.getAllFlightsSimpleDetailsByRouteName(flightRouteDTO.getName()));

        // Seleccionar vuelo

        BaseFlightDTO flightDTO = flightController.getAllFlightsSimpleDetailsByRouteName(flightRouteDTO.getName()).get(0);
        // No esta encontrando el vuelo

        assertEquals("Flight 101", flightDTO.getName());
        assertEquals(LocalDateTime.of(2026, 8, 1, 10, 0), flightDTO.getDepartureTime());
        assertEquals(180L, flightDTO.getDuration());
        assertEquals(150, flightDTO.getMaxEconomySeats());
        assertEquals(30, flightDTO.getMaxBusinessSeats());

        // TODO: Agregar validación de reservas cuando se implemente esa funcionalidad
    }
}
