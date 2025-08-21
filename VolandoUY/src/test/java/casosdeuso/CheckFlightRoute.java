/*
package casosdeuso;
import controllers.category.ICategoryController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import domain.dtos.category.CategoryDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.enums.EnumTipoDocumento;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.*;
public class CheckFlightRoute {

    IUserController userController= ControllerFactory.getUserController();
    IFlightRouteController flightRouteController=ControllerFactory.getFlightRouteController();
    ICategoryController categoryController=ControllerFactory.getCategoryController();
    ModelMapper modelMapper=new ModelMapper();
    IFlightController flightController=ControllerFactory.getFlightController();
    @BeforeEach
    void setUp() {
        AirlineDTO miAereolinea= new AirlineDTO();
        miAereolinea.setName("Aerolineas Argentinas");
        miAereolinea.setNickname("AA");
        miAereolinea.setMail("asdf@gmail.com");
        miAereolinea.setDescription("Aerolineas Argentinas");
        userController.registerAirline(miAereolinea);



        City arg= new City();
        arg.setName("Argentina Ciudad");
        arg.setCountry("Argentina");
        arg.setLongitude(-34.6037);
        arg.setLatitude(-58.3816);



        City madrid=new City();
        madrid.setName("Madrid Ciudad");
        madrid.setCountry("España");
        madrid.setLongitude(40.4168);
        madrid.setLatitude(-3.7038);
        madrid.addAirport("Madrid Airport","MAD");


        Category miCategoria= new Category();
        miCategoria.setName("Nacional");
        categoryController.createCategory(modelMapper.map(miCategoria, CategoryDTO.class));



        FlightRouteDTO miRuta= new FlightRouteDTO();
        miRuta.setName("TEST");
        miRuta.setDescription("Ruta Nacional");
        miRuta.setCreatedAt(LocalDateTime.now());
        miRuta.setPriceBusinessClass(15000.0);
        miRuta.setPriceTouristClass(8000.0);
        miRuta.setPriceExtraUnitBaggage(5000.0);
        miRuta.setOriginCity(arg);
        miRuta.setDestinationCity(madrid);
        miRuta.setCategory(List.of(modelMapper.map(miCategoria, CategoryDTO.class)));


        assertFalse(flightRouteController.existFlightRoute("TEST"));
        flightRouteController.createFlightRoute(miRuta,"AA");
        assertTrue(flightRouteController.existFlightRoute("TEST"));
        System.out.println("Ruta de vuelo creada: " + miRuta.getName());
        System.out.println("Ruta de vuelo creada: " + miRuta.getDescription());
        System.out.println("Ruta de vuelo creada: " + miRuta.getCreatedAt());
        System.out.println("Ruta de vuelo creada: " + miRuta.getPriceTouristClass());
        System.out.println("Ruta de vuelo creada: " + miRuta.getPriceBusinessClass());
        System.out.println("Ruta de vuelo creada: " + miRuta.getPriceExtraUnitBaggage());
        System.out.println("Ruta de vuelo creada: " + miRuta.getOriginCity().getName());
        System.out.println("Ruta de vuelo creada: " + miRuta.getDestinationCity().getName());
        System.out.println("Ruta de vuelo creada: " + miRuta.getCategory().get(0).getName());
        System.out.println("Ruta de vuelo creada: " + miRuta.getOriginCity().getAirports().get(0).getName());

        FlightDTO flightDTO= new FlightDTO();
        flightDTO.setFlightRoute(miRuta);
        flightDTO.setName("FLIGHT-TEST");
        flightDTO.setDepartureTime(LocalDateTime.now().plusDays(10));
        flightDTO.setDuration(180L);
        flightDTO.setMaxEconomySeats(100);
        flightDTO.setMaxBusinessSeats(50);
        flightDTO.setCreatedAt(LocalDateTime.now());
        System.out.println("Vuelo creado: " + flightDTO.getName());
        flightController.createFlight(flightDTO);


    }

    @Test
    public void consultaRutaVuelo(){
        //Listar Aereolinea
        assertNotNull(flightRouteController.getAllFlightRoutes());
        assertEquals(1, flightRouteController.getAllFlightRoutes().size());

        //Seleccionar Aereolinea
        AirlineDTO miAereolinea= userController.getAirlineByNickname("AA");
        assertEquals("Aerolineas Argentinas", miAereolinea.getName());
        assertTrue(flightRouteController.existFlightRoute("TEST"));

        //Listar Rutas de Vuelo
        List<FlightRouteDTO> rutas= flightRouteController.getFlightRoutesByAirline("AA");
        assertEquals(1, rutas.size());

        //Seleccionar Ruta de Vuelo de la lista
        FlightRouteDTO miRuta= rutas.get(0);
        assertEquals("TEST", miRuta.getName());
        assertEquals("Ruta Nacional", miRuta.getDescription());
        assertEquals(15000.0, miRuta.getPriceBusinessClass());
        assertEquals(8000.0, miRuta.getPriceTouristClass());
        assertEquals(5000.0, miRuta.getPriceExtraUnitBaggage());
        assertEquals("Argentina Ciudad", miRuta.getOriginCity().getName());
        assertEquals("Madrid Ciudad", miRuta.getDestinationCity().getName());
        assertEquals("Nacional", miRuta.getCategory().get(0).getName());
        assertEquals("Argentina Irport", miRuta.getOriginCity().getAirports().get(0).getName());

        //Seleccionar Ruta de Vuelo
        FlightRouteDTO rutaBuscada= flightRouteController.getFlightRouteByName("TEST");
        assertEquals("TEST", rutaBuscada.getName());
        assertEquals("Ruta Nacional", rutaBuscada.getDescription());
        assertEquals(15000.0, rutaBuscada.getPriceBusinessClass());
        assertEquals(8000.0, rutaBuscada.getPriceTouristClass());
        assertEquals(5000.0, rutaBuscada.getPriceExtraUnitBaggage());
        assertEquals("Argentina Ciudad", rutaBuscada.getOriginCity().getName());
        assertEquals("Madrid Ciudad", rutaBuscada.getDestinationCity().getName());
        assertEquals("Nacional", rutaBuscada.getCategory().get(0).getName());
        assertEquals("Argentina Irport", rutaBuscada.getOriginCity().getAirports().get(0).getName());

        //Seleccionar Vuelo
        FlightDTO flightDTO= flightController.getFlightByName("FLIGHT-TEST");
        assertEquals("FLIGHT-TEST", flightDTO.getName());
        assertEquals("TEST", flightDTO.getFlightRoute().getName());
        assertEquals("Ruta Nacional", flightDTO.getFlightRoute().getDescription());
        assertEquals(15000.0, flightDTO.getFlightRoute().getPriceBusinessClass());
        assertEquals(8000.0, flightDTO.getFlightRoute().getPriceTouristClass());
        assertEquals(5000.0, flightDTO.getFlightRoute().getPriceExtraUnitBaggage());
        assertEquals("Argentina Ciudad", flightDTO.getFlightRoute().getOriginCity().getName());
        assertEquals("Madrid Ciudad", flightDTO.getFlightRoute().getDestinationCity().getName());
        assertEquals("Nacional", flightDTO.getFlightRoute().getCategory().get(0).getName());
        assertEquals("Argentina Irport", flightDTO.getFlightRoute().getOriginCity().getAirports().get(0).getName());
        assertEquals(180L, flightDTO.getDuration());
        assertEquals(100, flightDTO.getMaxEconomySeats());
        assertEquals(50, flightDTO.getMaxBusinessSeats());

    }
}
*/