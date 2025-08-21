package casosdeuso;

import controllers.flight.IFlightController;
import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.flight.FlightDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.user.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class CheckFlightTest {
    private IUserController userController;
    private IFlightController flightController;

    @BeforeEach
    void setUp() {
        userController = ControllerFactory.getUserController();
        flightController = ControllerFactory.getFlightController();
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("airline1");
        airlineDTO.setName("Aerolíneas Argentinas");
        airlineDTO.setMail("mail@gmail.com");
        airlineDTO.setDescription("description");
        userController.registerAirline(airlineDTO);


        FlightDTO flightDTO = new FlightDTO();
        flightDTO.setName("Flight 101");
        flightDTO.setDepartureTime(LocalDateTime.of(2026, 8, 1, 10, 0));
        flightDTO.setDuration(180L); // 3 hours
        flightDTO.setMaxEconomySeats(150);
        flightDTO.setMaxBusinessSeats(30);
        flightDTO.setAirlineNickname("airline1");
        flightController.createFlight(flightDTO);
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
        String flightRouteName = flightController.getAllFlightsByAirline(airlineNickname).
                stream()
                .findFirst()
                .orElseThrow(() -> new UnsupportedOperationException("No hay vuelos disponibles"))
                .getName();
        assertNotNull(flightRouteName);
    }
}
