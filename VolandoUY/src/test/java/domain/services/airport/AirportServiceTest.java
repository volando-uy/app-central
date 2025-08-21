/*
package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.models.airport.Airport;
import domain.models.city.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.lang.reflect.Field;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class AirportServiceTest {

    private AirportService airportService;
    private ModelMapper modelMapper;
    @BeforeEach
    void setUp() {
        airportService = new AirportService();
        modelMapper=new ModelMapper();

        // Inicializar la lista privada "airports" vía reflexión
        try {
            Field field = AirportService.class.getDeclaredField("airports");
            field.setAccessible(true);
            field.set(airportService, new ArrayList<>());
        } catch (Exception e) {
            throw new RuntimeException("Error seteando campo airports", e);
        }
    }

    private City crearCiudad(String nombre) {
        return new City(new ArrayList<>(), nombre, "UY", -34.9, -56.2);
    }

    private Airport crearAirport(String nombre, String codigo, City ciudad) {
        Airport airport = new Airport(nombre, codigo);
        airport.setCity(ciudad);
        return airport;
    }
    @Test
    void agregarAeropuerto_nuevo_deberiaAgregarlo() {
        City mvd = crearCiudad("Montevideo");
        CityDTO mvdDTO = modelMapper.map(mvd, CityDTO.class);
        airportService.addAirport(mvdDTO, "Carrasco", "MVD");

        assertTrue(airportService.airportExists("MVD"));

        // Solución: buscar el aeropuerto y acceder a la ciudad que tiene asociada
        AirportDTO airport = airportService.getAirportDetailsByCode("MVD"); // necesitarás agregar este método (ver abajo)
        assertEquals(1, airport.getCity().getAirports().size());
        assertEquals("Carrasco", airport.getCity().getAirports().get(0).getName());
    }

    @Test
    void agregarAeropuerto_existente_deberiaLanzarExcepcion() {
        City mvd = crearCiudad("Montevideo");
        CityDTO mvdDTO = modelMapper.map(mvd, CityDTO.class);
        airportService.addAirport(mvdDTO, "Carrasco", "MVD");
        assertThrows(IllegalArgumentException.class, () ->
                airportService.addAirport(mvdDTO, "Carrasco2", "MVD")); // mismo código
    }
    @Test
    void eliminarAeropuerto_existente_deberiaEliminarloDeListaYDeCiudad() {
        City mvd = crearCiudad("Montevideo");
        CityDTO mvdDTO = modelMapper.map(mvd, CityDTO.class);
        airportService.addAirport(mvdDTO, "Carrasco", "MVD");

        airportService.removeAirport("MVD");

        assertFalse(airportService.airportExists("MVD"));
        assertTrue(mvd.getAirports().isEmpty());
    }

    @Test
    void eliminarAeropuerto_inexistente_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () ->
                airportService.removeAirport("NOEXISTE"));
    }

    @Test
    void actualizarAeropuerto_deberiaCambiarCiudadYActualizarListas() {
        City mvd = crearCiudad("Montevideo");
        City punta = crearCiudad("Punta del Este");

        CityDTO mvdDTO = modelMapper.map(mvd, CityDTO.class);
        CityDTO puntaDTO = modelMapper.map(punta, CityDTO.class);

        AirportDTO result = airportService.getAirportDetailsByCode("MVD");

        assertEquals("Punta del Este", result.getCity().getName());
    }

    @Test
    void actualizarAeropuerto_inexistente_deberiaLanzarExcepcion() {
        City c = crearCiudad("Canelones");
        CityDTO cDTO = modelMapper.map(c, CityDTO.class);

        assertThrows(IllegalArgumentException.class, () ->
                airportService.updateAirport("NOEXISTE", cDTO));
    }
    @Test
    void getAirportByCode_deberiaRetornarCiudadCorrecta() {
        City c = crearCiudad("Salto");
        CityDTO cDTO = modelMapper.map(c, CityDTO.class);

        AirportDTO airportDTO = new AirportDTO(
                cDTO, "Salto Air", "SLT"
        );
        airportService.createAirport(airportDTO, c.getName());

        AirportDTO result = airportService.getAirportDetailsByCode("SLT");
        assertEquals("Salto Air", result.getName());
        assertEquals("Salto", result.getCity().getName());
    }

    @Test
    void getAirportByCode_inexistente_deberiaRetornarNull() {
        assertNull(airportService.getAirportByCode("NO"));
    }

    @Test
    void getAirportByName_deberiaRetornarCiudadCorrecta() {
        City c = crearCiudad("Paysandú");
        CityDTO cDTO = modelMapper.map(c, CityDTO.class);
        airportService.addAirport(cDTO, "Paysandú Air", "PAY");

        AirportDTO result = airportService.getAirportDetailsByCode("PAY");
        assertEquals("Paysandú Air", result.getName());
        assertEquals("Paysandú", result.getCity().getName());
    }

    @Test
    void getAirportByName_inexistente_deberiaRetornarNull() {
        assertNull(airportService.getAirportDetailsByCode("FYI"));
    }

}
*/