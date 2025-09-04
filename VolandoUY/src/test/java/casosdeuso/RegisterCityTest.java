package casosdeuso;

import controllers.airport.IAirportController;
import controllers.city.ICityController;
import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class RegisterCityTest {
    private ICityController cityController;
    private IAirportController airportController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        cityController = ControllerFactory.getCityController();
        airportController = ControllerFactory.getAirportController();
    }

    @Test
    @DisplayName("CU: Alta exitosa de ciudad")
    void altaCiudadExitosa() {
//        CityDTO dto = new CityDTO(
//                "Lima",
//                "Perú",
//                -12.0464,
//                -77.0428,
//                List.of("Aeropuerto Jorge Chávez")
//        );
        BaseCityDTO baseCityDTO = new BaseCityDTO();
        baseCityDTO.setName("Lima");
        baseCityDTO.setCountry("Perú");
        baseCityDTO.setLatitude(-12.0464);
        baseCityDTO.setLongitude(-77.0428);

        BaseCityDTO result = cityController.createCity(baseCityDTO);

        assertEquals("Lima", result.getName());
        assertEquals("Perú", result.getCountry());
        assertEquals(-12.0464, result.getLatitude());
        assertEquals(-77.0428, result.getLongitude());
    }

    @Test
    @DisplayName("CU: No se puede crear ciudad duplicada")
    void ciudadDuplicadaFalla() {
        BaseCityDTO baseCityDTO = new BaseCityDTO();
        baseCityDTO.setName("Lima");
        baseCityDTO.setCountry("Perú");
        baseCityDTO.setLatitude(-12.0464);
        baseCityDTO.setLongitude(-77.0428);

        cityController.createCity(baseCityDTO);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            cityController.createCity(baseCityDTO);
        });

        assertEquals(String.format(ErrorMessages.ERR_CITY_ALREADY_EXISTS, "Lima"), ex.getMessage());
    }

    @Test
    @DisplayName("CU: Verificar existencia de ciudad por nombre")
    void verificarExistenciaCiudad() {
        assertFalse(cityController.cityExists("Quito"));
        BaseCityDTO baseCityDTO = new BaseCityDTO();
        baseCityDTO.setName("Quito");
        baseCityDTO.setCountry("Ecuador");
        baseCityDTO.setLatitude(-0.1807);
        baseCityDTO.setLongitude(-78.4678);

        cityController.createCity(baseCityDTO);

        assertTrue(cityController.cityExists("Quito"));
    }

    @Test
    @DisplayName("CU: Ver detalle de ciudad por nombre")
    void obtenerDetallesCiudad() {
        BaseCityDTO baseCityDTO = new BaseCityDTO();
        baseCityDTO.setName("Bogotá");
        baseCityDTO.setCountry("Colombia");
        baseCityDTO.setLatitude(4.7110);
        baseCityDTO.setLongitude(-74.0721);

        cityController.createCity(baseCityDTO);

        CityDTO bogota = cityController.getCityDetailsByName("Bogotá");

        assertNotNull(bogota);
        assertEquals("Colombia", bogota.getCountry());
    }


    @Test
    @DisplayName("CU: Alta de ciudad y aeropuerto asociado")
    void altaCiudadConAeropuerto() {
        // Paso 1: Crear la ciudad
//        CityDTO ciudad = new CityDTO(
//                "Montevideo",
//                "Uruguay",
//                -34.9011,
//                -56.1645,
//                null // Sin aeropuertos en la creación
//        );

        BaseCityDTO baseCityDTO = new BaseCityDTO();
        baseCityDTO.setName("Montevideo");
        baseCityDTO.setCountry("Uruguay");
        baseCityDTO.setLatitude(-34.9011);
        baseCityDTO.setLongitude(-56.1645);


        BaseCityDTO ciudadCreada = cityController.createCity(baseCityDTO);

        assertNotNull(ciudadCreada);
        assertEquals("Montevideo", ciudadCreada.getName());
        assertEquals("Uruguay", ciudadCreada.getCountry());

        // Paso 2: Crear aeropuerto asociado
        AirportDTO aeropuerto = new AirportDTO();
        aeropuerto.setName("Carrasco");
        aeropuerto.setCode("MVD");
        aeropuerto.setCityName("Montevideo");

        BaseAirportDTO aeropuertoCreado = airportController.createAirport(aeropuerto, "Montevideo");

        assertNotNull(aeropuertoCreado);
        assertEquals("MVD", aeropuertoCreado.getCode());
        assertEquals("Carrasco", aeropuertoCreado.getName());

        // Paso 3: Verificar que la ciudad tenga el aeropuerto
        CityDTO ciudadConAeropuerto = cityController.getCityDetailsByName("Montevideo");
        assertNotNull(ciudadConAeropuerto.getAirportNames());
        assertTrue(ciudadConAeropuerto.getAirportNames().contains("Carrasco"));
    }

}
