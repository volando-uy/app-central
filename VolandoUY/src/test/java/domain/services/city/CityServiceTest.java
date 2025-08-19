package domain.services.city;

import domain.dtos.city.CityDTO;
import domain.models.city.City;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CityServiceTest {
    private CityService cityService;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        cityService = new CityService();
        modelMapper= ControllerFactory.getModelMapper();
        // Inyectamos manualmente una lista vacía para testeo
        var citiesField = CityService.class.getDeclaredFields()[0];
        citiesField.setAccessible(true);
        try {
            citiesField.set(cityService, new ArrayList<>());
        } catch (Exception e) {
            throw new RuntimeException("No se pudo setear cities", e);
        }
    }

    private CityDTO crearCiudadBasica(String nombre) {
        return new CityDTO(new ArrayList<>(), nombre, "Uruguay", -34.9011, -56.1645);
    }
    @Test
    void agregarCiudad_nueva_deberiaAgregarlaCorrectamente() {
        CityDTO montevideo = crearCiudadBasica("Montevideo");
        cityService.addCity(montevideo);

        assertTrue(cityService.cityExists("Montevideo"));
    }

    @Test
    void agregarCiudad_existente_deberiaLanzarExcepcion() {
        CityDTO montevideo = crearCiudadBasica("Montevideo");
        cityService.addCity(montevideo);

        assertThrows(IllegalArgumentException.class, () -> cityService.addCity(montevideo));
    }

    @Test
    void updateCiudad_existente_deberiaActualizarla() {
        CityDTO mvd = crearCiudadBasica("Montevideo");
        cityService.addCity(mvd);

        CityDTO actualizada = crearCiudadBasica("Montevideo");
        actualizada.setCountry("Uruguay - actualizado");
        cityService.updateCity(actualizada);
        assertEquals("Uruguay - actualizado", cityService.getCity("Montevideo").getCountry());
    }

    @Test
    void updateCiudad_inexistente_deberiaLanzarExcepcion() {
        CityDTO noExiste = crearCiudadBasica("Atlantida");
        assertThrows(IllegalArgumentException.class, () -> cityService.updateCity(noExiste));
    }

    @Test
    void deleteCiudad_existente_deberiaEliminarla() {
        cityService.addCity(crearCiudadBasica("Canelones"));
        cityService.deleteCity("Canelones");

        assertFalse(cityService.cityExists("Canelones"));
    }

    @Test
    void deleteCiudad_inexistente_deberiaLanzarExcepcion() {
        assertThrows(IllegalArgumentException.class, () -> cityService.deleteCity("NoExiste"));
    }

    @Test
    void agregarAeropuerto_nuevo_deberiaAgregarlo() {
        cityService.addCity(crearCiudadBasica("Rivera"));
        cityService.addAirportToCity("Rivera", "Carrasco", "MVD");

        CityDTO cityDTO = cityService.getCity("Rivera");
        City city = modelMapper.map(cityDTO, City.class);
        assertTrue(city.isAirportInCity("Carrasco"));
    }

    @Test
    void agregarAeropuerto_existente_deberiaLanzarExcepcion() {
        cityService.addCity(crearCiudadBasica("Rivera"));
        cityService.addAirportToCity("Rivera", "Carrasco", "MVD");

        assertThrows(IllegalArgumentException.class, () ->
                cityService.addAirportToCity("Rivera", "Carrasco", "MVD"));
    }

    @Test
    void eliminarAeropuerto_existente_deberiaEliminarlo() {
        cityService.addCity(crearCiudadBasica("Colonia"));
        cityService.addAirportToCity("Colonia", "Colonia Airport", "COL");

        cityService.removeAirportFromCity("Colonia", "Colonia Airport");

        assertFalse(cityService.isAirportInCity("Colonia", "Colonia Airport"));
    }



    @Test
    void eliminarAeropuerto_inexistente_deberiaLanzarExcepcion() {
        cityService.addCity(crearCiudadBasica("Piriápolis"));

        assertThrows(IllegalArgumentException.class, () ->
                cityService.removeAirportFromCity("Piriápolis", "NoExiste"));
    }

    @Test
    void actualizarAeropuerto_deberiaCambiarNombreYCodigo() {
        cityService.addCity(crearCiudadBasica("Salto"));
        cityService.addAirportToCity("Salto", "Salto Airport", "SLT");

        cityService.updateAirportInCity("Salto", "Salto Airport", "Nuevo Salto", "NSLT");

        CityDTO saltoDTO = cityService.getCity("Salto");
        City salto= modelMapper.map(saltoDTO, City.class);
        assertTrue(salto.isAirportInCity("Nuevo Salto"));
    }

    @Test
    void getCityWithAirports_deberiaRetornarCiudadConAeropuertos() {
        cityService.addCity(crearCiudadBasica("Durazno"));
        cityService.addAirportToCity("Durazno", "Airport D", "DUR");

        CityDTO city = cityService.getCityWithAirports("Durazno");

        assertEquals(1, city.getAirports().size());
        assertEquals("DUR", city.getAirports().get(0).getCode());
    }

    @Test
    void isAirportInCity_trueSiExiste_falseSiNo() {
        cityService.addCity(crearCiudadBasica("Tacuarembó"));
        cityService.addAirportToCity("Tacuarembó", "TAC Airport", "TAC");

        assertTrue(cityService.isAirportInCity("Tacuarembó", "TAC Airport"));
        assertFalse(cityService.isAirportInCity("Tacuarembó", "Otro Airport"));
    }


}