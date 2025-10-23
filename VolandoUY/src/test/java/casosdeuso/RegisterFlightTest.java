package casosdeuso;

import controllers.airport.IAirportController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightroute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightroute.BaseFlightRouteDTO;
import domain.dtos.flightroute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class RegisterFlightTest {

    private IUserController userController;
    private ICityController cityController;
    private IAirportController airportController;
    private IFlightRouteController flightRouteController;
    private IFlightController flightController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();

        userController = ControllerFactory.getUserController();
        cityController = ControllerFactory.getCityController();
        airportController = ControllerFactory.getAirportController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        flightController = ControllerFactory.getFlightController();

        // Paso 1: Crear aerolínea
        BaseAirlineDTO airlineDTO = new BaseAirlineDTO(
                "LATAM", "LATAM Airlines", "latam@mail.com", "password", null,
                "Aerolínea internacional", "www.latam.com"
        );
        userController.registerAirline(airlineDTO, null);

        // Paso 2: Crear ciudades (usando solo el controller)
        BaseCityDTO baseCityDTO = new BaseCityDTO();
        baseCityDTO.setName("Santiago");
        baseCityDTO.setCountry("Chile");
        baseCityDTO.setLatitude(-33.4489);
        baseCityDTO.setLongitude(-70.6693);
        cityController.createCity(baseCityDTO);

        BaseCityDTO baseCityDTO2 = new BaseCityDTO();
        baseCityDTO2.setName("Lima");
        baseCityDTO2.setCountry("Perú");
        baseCityDTO2.setLatitude(-12.0464);
        baseCityDTO2.setLongitude(-77.0428);
        cityController.createCity(baseCityDTO2);

        // Paso 3: Crear aeropuertos
        airportController.createAirport(
                new BaseAirportDTO("Aero Santiago", "SAN"),
                "Santiago"
        );
        airportController.createAirport(
                new BaseAirportDTO("Aero Lima", "LIM"),
                "Lima"
        );

        // Paso 4: Crear ruta de vuelo
        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("LATAM-SAN-LIM");
        baseFlightRouteDTO.setDescription("Ruta Santiago - Lima");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(500.0);
        baseFlightRouteDTO.setPriceBusinessClass(1000.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(150.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO, "SAN", "LIM", "LATAM", List.of(), null);
    }

    @Test
    @DisplayName("CU: Alta de vuelo exitoso con ruta existente")
    void altaDeVuelo_exitoso() {
        // Paso 5: Listar aerolíneas
        List<AirlineDTO> airlines = userController.getAllAirlinesDetails();
        assertFalse(airlines.isEmpty());

        // Paso 6: Seleccionar una ruta
        List<FlightRouteDTO> rutas = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname("LATAM");
        assertEquals(1, rutas.size());

        // Paso 7: Crear vuelo
        BaseFlightDTO vuelo = new BaseFlightDTO();
        vuelo.setName("LATAM123");
        vuelo.setCreatedAt(LocalDateTime.now());
        vuelo.setDepartureTime(LocalDateTime.now().plusDays(3));
        vuelo.setDuration(240L); // 4 horas
        vuelo.setMaxEconomySeats(180);
        vuelo.setMaxBusinessSeats(30);

        flightController.createFlight(vuelo, "LATAM", "LATAM-SAN-LIM", null);

        FlightDTO vueloCreado = flightController.getFlightDetailsByName(vuelo.getName());

        // Paso 8: Validar
        assertEquals("LATAM123", vueloCreado.getName());
        assertEquals("LATAM", vueloCreado.getAirlineNickname());
        assertEquals("LATAM-SAN-LIM", vueloCreado.getFlightRouteName());
        assertEquals(240L, vueloCreado.getDuration());
        assertEquals(180, vueloCreado.getMaxEconomySeats());
        assertEquals(30, vueloCreado.getMaxBusinessSeats());

        // Paso 9: Intentar duplicado
        Exception ex = assertThrows(UnsupportedOperationException.class, () -> {
            flightController.createFlight(vuelo, "LATAM", "LATAM-SAN-LIM", null);
        });
        assertEquals(String.format(ErrorMessages.ERR_FLIGHT_ALREADY_EXISTS, vuelo.getName()), ex.getMessage());
    }
}
