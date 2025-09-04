package casosdeuso;

import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
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

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        userController = ControllerFactory.getUserController();
        flightController = ControllerFactory.getFlightController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        cityController = ControllerFactory.getCityController();

        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("airline1");
        airlineDTO.setName("Aerolíneas Argentinas");
        airlineDTO.setMail("mail@gmail.com");
        airlineDTO.setDescription("description");
        userController.registerAirline(airlineDTO);

        CityDTO cityA = new CityDTO();
        cityA.setName("City A");
        cityA.setCountry("Country A");
        cityA.setLatitude(-34.6037);
        cityA.setLongitude(-58.3816);
        cityController.createCity(cityA);

        CityDTO cityB = new CityDTO();
        cityB.setName("City B");
        cityB.setCountry("Country B");
        cityB.setLatitude(-34.6037);
        cityB.setLongitude(-58.3816);
        cityController.createCity(cityB);

//        FlightRouteDTO flightRouteDTO = new FlightRouteDTO();
//        flightRouteDTO.setName("Route 101");
//        flightRouteDTO.setDescription("Route from City A to City B");
//        flightRouteDTO.setOriginCityName("City A");
//        flightRouteDTO.setDestinationCityName("City B");
//        flightRouteDTO.setAirlineNickname("airline1");
//        flightRouteDTO.setCreatedAt(LocalDate.now());
//        flightRouteDTO.setPriceTouristClass(10.0);
//        flightRouteDTO.setPriceBusinessClass(20.0);
//        flightRouteDTO.setPriceExtraUnitBaggage(5.0);
//        flightRouteController.createFlightRoute(flightRouteDTO);

        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("Route 101");
        baseFlightRouteDTO.setDescription("Route from City A to City B");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(10.0);
        baseFlightRouteDTO.setPriceBusinessClass(20.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(5.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO, "City A", "City B", "airline1", List.of());

//        FlightDTO flightDTO = new FlightDTO();
//        flightDTO.setName("Flight 101");
//        flightDTO.setDepartureTime(LocalDateTime.of(2026, 8, 1, 10, 0));
//        flightDTO.setDuration(180L); // 3 hours
//        flightDTO.setMaxEconomySeats(150);
//        flightDTO.setMaxBusinessSeats(30);
//        flightDTO.setAirlineNickname("airline1");
//        flightDTO.setFlightRouteName("Route 101");
//        flightController.createFlight(flightDTO);

        BaseFlightDTO baseFlightDTO = new BaseFlightDTO();
        baseFlightDTO.setName("Flight 101");
        baseFlightDTO.setDepartureTime(LocalDateTime.of(2026, 8,1,10,0));
        baseFlightDTO.setDuration(180L); // 3 hours
        baseFlightDTO.setMaxEconomySeats(150);
        baseFlightDTO.setMaxBusinessSeats(30);
        flightController.createFlight(baseFlightDTO, "airline1", "Route 101");

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
        flightController.getAllFlightsByAirline(airlineNickname).forEach(System.out::println);
        assertNotNull(flightController.getAllFlightsByAirline(airlineNickname));

        //Seleccionar ruta de vuelo
        FlightRouteDTO flightRouteDTO = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineNickname).get(0);
        assertEquals("Route 101", flightRouteDTO.getName());
        assertEquals("City A", flightRouteDTO.getOriginCityName());
        assertEquals("City B", flightRouteDTO.getDestinationCityName());

        //Listar vuelos asociados a dicha ruta
        flightController.getAllFlightsByRouteName(flightRouteDTO.getName()).forEach(System.out::println);
        assertNotNull(flightController.getAllFlightsByRouteName(flightRouteDTO.getName()));

        // Seleccionar vuelo

        FlightDTO flightDTO = flightController.getAllFlightsByRouteName(flightRouteDTO.getName()).get(0); // java.lang.ArrayIndexOutOfBoundsException: Index 0 out of bounds for length 0
        // No esta encontrando el vuelo

        assertEquals("Flight 101", flightDTO.getName());
        assertEquals(LocalDateTime.of(2026, 8, 1, 10, 0), flightDTO.getDepartureTime());
        assertEquals(180L, flightDTO.getDuration());
        assertEquals(150, flightDTO.getMaxEconomySeats());
        assertEquals(30, flightDTO.getMaxBusinessSeats());

        // TODO: Agregar validación de reservas cuando se implemente esa funcionalidad
    }
}
