package casosdeuso;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.category.Category;
import domain.models.city.City;
import factory.ControllerFactory;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ConsultaRutaVueloTest {

    IUserController userController = ControllerFactory.getUserController();
    IFlightRouteController flightRouteController = ControllerFactory.getFlightRouteController();

    @Test
    public void testConsultaRutaVuelo() {
        // 1. Registrar Aerolínea
        AirlineDTO airline = new AirlineDTO();
        airline.setName("LATAM Airlines");
        airline.setNickname("LATAM");
        airline.setMail("latam@mail.com");
        airline.setDescription("Aerolinea internacional");
        userController.registerAirline(airline);

        // 2. Crear Ciudades
        City santiago = new City("Santiago", "Chile", -33.4489, -70.6693);
        santiago.addAirport("SCL Airport", "SCL");

        City lima = new City("Lima", "Peru", -12.0464, -77.0428);
        lima.addAirport("LIM Airport", "LIM");

        // 3. Crear Categoría
        Category internacional = new Category("Internacional");

        // 4. Crear Ruta de Vuelo
        FlightRouteDTO ruta = new FlightRouteDTO();
        ruta.setName("SCL-LIM");
        ruta.setDescription("Ruta Santiago - Lima");
        ruta.setCreatedAt(LocalDateTime.now());
        ruta.setPriceBusinessClass(1200.0);
        ruta.setPriceTouristClass(600.0);
        ruta.setPriceExtraUnitBaggage(100.0);
        ruta.setOriginCity(santiago);
        ruta.setDestinationCity(lima);
        ruta.setCategory(List.of(internacional));

        flightRouteController.createFlightRoute(ruta);

        // 5. Verificar que la ruta exista
        assertTrue(flightRouteController.existFlightRoute("SCL-LIM"));

        // 6. Consultar todas las rutas
        List<FlightRouteDTO> rutas = flightRouteController.getAllFlightRoutes();
        assertFalse(rutas.isEmpty());
        assertEquals("SCL-LIM", rutas.get(0).getName());

        // 7. Consultar detalle de la ruta específica
        FlightRouteDTO detalle = rutas.stream()
                .filter(r -> r.getName().equals("SCL-LIM"))
                .findFirst()
                .orElse(null);

        assertNotNull(detalle);
        assertEquals("Santiago", detalle.getOriginCity().getName());
        assertEquals("Lima", detalle.getDestinationCity().getName());

        System.out.println("Consulta de Ruta de Vuelo OK -> " + detalle);
    }
}
