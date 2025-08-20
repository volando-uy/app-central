package casosdeuso;

import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.category.Category;
import domain.models.city.City;
import factory.ControllerFactory;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;

public class RegisterFlightRouteTest {

    IUserController userController= ControllerFactory.getUserController();
    IFlightRouteController flightRouteController=ControllerFactory.getFlightRouteController();

    @Test
    public void testCrearRutaVuelo(){
        AirlineDTO miAereolinea= new AirlineDTO();
        miAereolinea.setName("Aerolineas Argentinas");
        miAereolinea.setNickname("AA");
        miAereolinea.setMail("asdf@gmail.com");
        miAereolinea.setDescription("Aerolineas Argentinas");
        userController.registerAirline(miAereolinea);
        System.out.println(userController.getAllAirlines());



        City arg= new City();
        arg.setName("Argentina Ciudad");
        arg.setCountry("Argentina");
        arg.setLongitude(-34.6037);
        arg.setLatitude(-58.3816);
        arg.addAirport("Argentina Irport","ARG");
        System.out.println("Ciudad de origen: "+arg);



        City madrid=new City();
        madrid.setName("Madrid Ciudad");
        madrid.setCountry("España");
        madrid.setLongitude(40.4168);
        madrid.setLatitude(-3.7038);
        madrid.addAirport("Madrid Airport","MAD");
        System.out.println("Ciudad de destino: "+madrid);


        Category miCategoria= new Category();
        miCategoria.setName("Nacional");
        System.out.println("Categoria: "+miCategoria);


        FlightRouteDTO miRuta= new FlightRouteDTO();
        miRuta.setName("TEST");
        miRuta.setDescription("Ruta Nacional");
        miRuta.setCreatedAt(LocalDateTime.now());
        miRuta.setPriceBusinessClass(15000.0);
        miRuta.setPriceTouristClass(8000.0);
        miRuta.setPriceExtraUnitBaggage(5000.0);
        miRuta.setOriginCity(arg);
        miRuta.setDestinationCity(madrid);
        miRuta.setCategory(List.of(miCategoria));
        System.out.println("Ruta a crear: "+miRuta);

        assertFalse(flightRouteController.existFlightRoute("TEST"));
        flightRouteController.createFlightRoute(miRuta);
        System.out.println("Rutas existentes: "+flightRouteController.getAllFlightRoutes());
        assertTrue(flightRouteController.existFlightRoute("TEST"));

    }
}
