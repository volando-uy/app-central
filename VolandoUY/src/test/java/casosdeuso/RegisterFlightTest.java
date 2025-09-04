package casosdeuso;

import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
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
    private IFlightRouteController flightRouteController;
    private IFlightController flightController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();

        userController = ControllerFactory.getUserController();
        cityController = ControllerFactory.getCityController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        flightController = ControllerFactory.getFlightController();

        // Paso 1: Crear aerolínea
        AirlineDTO airlineDTO = new AirlineDTO(
                "LATAM", "LATAM Airlines", "latam@mail.com",
                "Aerolínea internacional", "www.latam.com"
        );
        userController.registerAirline(airlineDTO);

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

        // Paso 3: Crear ruta de vuelo
//        FlightRouteDTO routeDTO = new FlightRouteDTO();
//        routeDTO.setName("LATAM-SCL-LIM");
//        routeDTO.setDescription("Ruta Santiago - Lima");
//        routeDTO.setCreatedAt(LocalDate.now());
//        routeDTO.setPriceTouristClass(500.0);
//        routeDTO.setPriceBusinessClass(1000.0);
//        routeDTO.setPriceExtraUnitBaggage(150.0);
//        routeDTO.setOriginCityName("Santiago");
//        routeDTO.setDestinationCityName("Lima");
//        routeDTO.setAirlineNickname("LATAM");
//        routeDTO.setCategories(List.of());
        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("LATAM-SCL-LIM");
        baseFlightRouteDTO.setDescription("Ruta Santiago - Lima");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(500.0);
        baseFlightRouteDTO.setPriceBusinessClass(1000.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(150.0);


        flightRouteController.createFlightRoute(baseFlightRouteDTO, "Santiago", "Lima", "LATAM", List.of());
    }

    @Test
    @DisplayName("CU: Alta de vuelo exitoso con ruta existente")
    void altaDeVuelo_exitoso() {
        // Paso 4: Listar aerolíneas
        List<AirlineDTO> airlines = userController.getAllAirlinesDetails();
        assertFalse(airlines.isEmpty());

        // Paso 5: Seleccionar una ruta
        List<FlightRouteDTO> rutas = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname("LATAM");
        assertEquals(1, rutas.size());

        // Paso 6: Crear vuelo
//        FlightDTO vuelo = new FlightDTO();
//        vuelo.setName("LATAM123");
//        vuelo.setAirlineNickname("LATAM");
//        vuelo.setFlightRouteName("LATAM-SCL-LIM");
//        vuelo.setCreatedAt(LocalDateTime.now());
//        vuelo.setDepartureTime(LocalDateTime.now().plusDays(3));
//        vuelo.setDuration(240L); // 4 horas
//        vuelo.setMaxEconomySeats(180);
//        vuelo.setMaxBusinessSeats(30);

        BaseFlightDTO baseFlightDTO  = new BaseFlightDTO();
        baseFlightDTO.setName("LATAM123");
        baseFlightDTO.setCreatedAt(LocalDateTime.now());
        baseFlightDTO.setDepartureTime(LocalDateTime.now().plusDays(3));
        baseFlightDTO.setDuration(240L); // 4 horas
        baseFlightDTO.setMaxEconomySeats(180);
        baseFlightDTO.setMaxBusinessSeats(30);


        BaseFlightDTO creado = flightController.createFlight(baseFlightDTO, "LATAM", "LATAM-SCL-LIM");

        // Paso 7: Validar
        assertEquals("LATAM123", creado.getName());
//        assertEquals("LATAM", creado.getAirlineNickname());
        assertEquals(180, creado.getMaxEconomySeats());
        assertEquals(30, creado.getMaxBusinessSeats());
        assertEquals(240L, creado.getDuration());

        // Paso 8: Intentar duplicado
        Exception ex = assertThrows(UnsupportedOperationException.class, () -> {
            flightController.createFlight(baseFlightDTO, "LATAM", "LATAM-SCL-LIM");
        });
        assertEquals(String.format(ErrorMessages.ERR_FLIGHT_ALREADY_EXISTS, baseFlightDTO.getName()), ex.getMessage());
    }
}
