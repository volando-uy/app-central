package casosdeuso;

import controllers.category.ICategoryController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.category.CategoryDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.category.Category;
import domain.models.city.City;
import factory.ControllerFactory;
import infra.repository.city.CityRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CheckFlightRouteTest {

    private IUserController userController;
    private IFlightRouteController flightRouteController;
    private IFlightController flightController;
    private ICategoryController categoryController;
    private CityRepository cityRepository;



    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        userController = ControllerFactory.getUserController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        flightController = ControllerFactory.getFlightController();
        categoryController = ControllerFactory.getCategoryController();
        cityRepository = new CityRepository();
        // Registrar aerolínea
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setName("Aerolineas Argentinas");
        airlineDTO.setNickname("AA");
        airlineDTO.setMail("aa@gmail.com");
        airlineDTO.setDescription("Línea aérea nacional");

        userController.registerAirline(airlineDTO);

        // Crear ciudades
        City origen = new City();
        origen.setName("Buenos Aires");
        origen.setCountry("Argentina");
        origen.setLatitude(-34.6037);
        origen.setLongitude(-58.3816);
        origen.setAirports(List.of());


        City destino = new City();
        destino.setName("Madrid");
        destino.setCountry("España");
        destino.setLatitude(40.4168);
        destino.setLongitude(-3.7038);
        destino.setAirports(List.of());

        cityRepository.save(origen);
        cityRepository.save(destino);

        // Crear categoría
        Category categoria = new Category();
        categoria.setName("Internacional");
        CategoryDTO categoriaDTO = categoryController.createCategory(new CategoryDTO(categoria.getName()));

        // Crear ruta de vuelo
        FlightRouteDTO rutaDTO = new FlightRouteDTO();
        rutaDTO.setName("AA-MAD");
        rutaDTO.setDescription("Ruta internacional Buenos Aires - Madrid");
        rutaDTO.setCreatedAt(LocalDate.now());
        rutaDTO.setPriceTouristClass(10000.0);
        rutaDTO.setPriceBusinessClass(20000.0);
        rutaDTO.setPriceExtraUnitBaggage(3500.0);
        rutaDTO.setOriginCityName("Buenos Aires");
        rutaDTO.setDestinationCityName("Madrid");
        rutaDTO.setAirlineNickname("AA");
        rutaDTO.setCategories(List.of("Internacional"));

        assertFalse(flightRouteController.existFlightRoute("AA-MAD"));
        flightRouteController.createFlightRoute(rutaDTO);
        assertTrue(flightRouteController.existFlightRoute("AA-MAD"));

        // Crear vuelo asociado (indirectamente relacionado con la ruta)
        FlightDTO vueloDTO = new FlightDTO();
        vueloDTO.setName("VUELO-AA001");
        vueloDTO.setAirlineNickname("AA");
        vueloDTO.setCreatedAt(LocalDateTime.now());
        vueloDTO.setDepartureTime(LocalDateTime.now().plusDays(5));
        vueloDTO.setDuration(720L);
        vueloDTO.setMaxEconomySeats(250);
        vueloDTO.setMaxBusinessSeats(50);
        vueloDTO.setFlightRouteName("AA-MAD");


        flightController.createFlight(vueloDTO);
    }

    @Test
    void consultaRutaDeVuelo_completa() {
        // Paso 1: Listar aerolíneas
        List<AirlineDTO> aerolineas = userController.getAllAirlines();
        assertFalse(aerolineas.isEmpty());

        // Paso 2: Seleccionar aerolínea
        AirlineDTO aerolinea = userController.getAirlineByNickname("AA");
        assertNotNull(aerolinea);
        assertEquals("Aerolineas Argentinas", aerolinea.getName());

        // Paso 3: Listar rutas de vuelo asociadas
        List<FlightRouteDTO> rutas = flightRouteController.getAllFlightRoutesByAirlineNickname("AA");
        assertEquals(1, rutas.size());

        FlightRouteDTO ruta = rutas.get(0);

        // Paso 4: Validar datos de la ruta
        assertEquals("AA-MAD", ruta.getName());
        assertEquals("Ruta internacional Buenos Aires - Madrid", ruta.getDescription());
        assertEquals(10000.0, ruta.getPriceTouristClass());
        assertEquals(20000.0, ruta.getPriceBusinessClass());
        assertEquals(3500.0, ruta.getPriceExtraUnitBaggage());
        assertEquals("Buenos Aires", ruta.getOriginCityName());
        assertEquals("Madrid", ruta.getDestinationCityName());
        assertEquals("AA", ruta.getAirlineNickname());
        System.out.println("Categorias asignadas: " + ruta.getCategories());

        //Categorias asignadas: [Category(name=Internacional)]
        assertTrue(ruta.getCategories().get(0).contains("Internacional"));

        // Paso 5: Ver vuelos asociados a esa ruta
        //Vuelos:
        //Ver ruta de vuelo
        //Agarrar Ruta De vUELO
        System.out.println("Ruta de vuelo: " + flightRouteController.getFlightRouteByName("AA-MAD"));
        System.out.println("Vuelos:" + flightController.getAllFlightsByRouteName("AA-MAD"));
        List<FlightDTO> vuelos = flightController.getAllFlightsByRouteName("AA-MAD");
        assertEquals(1, vuelos.size());

        // Paso 6: Ver datos del vuelo
        FlightDTO vuelo = vuelos.get(0);
        assertEquals("VUELO-AA001", vuelo.getName());
        assertEquals("AA", vuelo.getAirlineNickname());
        assertEquals(250, vuelo.getMaxEconomySeats());
        assertEquals(50, vuelo.getMaxBusinessSeats());
        assertEquals(720L, vuelo.getDuration());
        assertNotNull(vuelo.getCreatedAt());
        assertNotNull(vuelo.getDepartureTime());
    }
}
