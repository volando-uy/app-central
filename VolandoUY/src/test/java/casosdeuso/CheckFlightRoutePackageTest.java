package casosdeuso;

import controllers.airport.IAirportController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.FlightRoutePackageController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import factory.ControllerFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.modelmapper.ModelMapper;
import utils.TestUtils;
import infra.repository.city.CityRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

/**
 * El caso de uso comienza cuando el administrador quiere consultar la
 * información de un paquete de rutas de vuelo. En primer lugar, el sistema
 * lista los paquetes registrados. El administrador selecciona un paquete y el
 * sistema devuelve sus datos, su costo y los tipos y cantidades de rutas de
 * vuelo que lo integran. En caso de seleccionar una ruta de vuelo, se puede
 * ver su información detallada, tal como se indica en el caso de uso
 * Consulta de Ruta de Vuelo
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CheckFlightRoutePackageTest {

    IFlightRoutePackageController flightRoutePackageController;
    ICategoryController categoryController;
    IFlightRouteController flightRouteController;
    IUserController userController;
    IAirportController airportController;
    ICityController cityController;
    IFlightController flightController;
    @BeforeAll
    void setUp() {
        TestUtils.cleanDB();
        flightRoutePackageController = ControllerFactory.getFlightRoutePackageController();
        categoryController = ControllerFactory.getCategoryController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        userController = ControllerFactory.getUserController();
        airportController = ControllerFactory.getAirportController();
        cityController = ControllerFactory.getCityController();
        flightController = ControllerFactory.getFlightController();
    }

    @Test
    void checkFlightRoutePackageTest() {

        //Crear Aereolineas Argentinas
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setName("Aerolineas Argentinas");
        airlineDTO.setNickname("Aerolineas Argentinas");
        airlineDTO.setMail("airline@gmail.com");
        airlineDTO.setPassword("password");
        airlineDTO.setDescription("Línea aérea nacional");
        userController.registerAirline(airlineDTO, null);


        //Crear categoria de Internacional
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Internacional");
        categoryController.createCategory(categoryDTO);


        //Crear ciudad Madrid
        BaseCityDTO cityDTO = new BaseCityDTO();
        cityDTO.setName("Madrid");
        cityDTO.setCountry("España");
        cityDTO.setLatitude(40.4168);
        cityDTO.setLongitude(-3.7038);
        cityController.createCity(cityDTO);

        //Crear ciudad Paris
        BaseCityDTO cityDTO2 = new BaseCityDTO();
        cityDTO2.setName("Paris");
        cityDTO2.setCountry("Francia");
        cityDTO2.setLatitude(48.8566);
        cityDTO2.setLongitude(2.3522);
        cityController.createCity(cityDTO2);

        //Crear ciudad Berlin
        BaseCityDTO cityDTO3 = new BaseCityDTO();
        cityDTO3.setName("Berlin");
        cityDTO3.setCountry("Alemania");
        cityDTO3.setLatitude(52.5200);
        cityDTO3.setLongitude(13.4050);
        cityController.createCity(cityDTO3);

        airportController.createAirport(
                new BaseAirportDTO("Aero Madrid", "MAD"),
                "Madrid"
        );

        airportController.createAirport(
                new BaseAirportDTO("Aero Paris", "PAR"),
                "Paris"
        );

        airportController.createAirport(
                new BaseAirportDTO("Aero Berlin", "BER"),
                "Berlin"
        );



        BaseFlightRouteDTO flightRouteDTO = new BaseFlightRouteDTO();
        flightRouteDTO.setName("Madrid - Paris");
        flightRouteDTO.setDescription("Ruta de vuelo de Madrid a Paris");
        flightRouteDTO.setCreatedAt(java.time.LocalDate.now());
        flightRouteDTO.setPriceTouristClass(150.0);
        flightRouteDTO.setPriceBusinessClass(300.0);
        flightRouteDTO.setPriceExtraUnitBaggage(50.0);
        flightRouteController.createFlightRoute(
            flightRouteDTO,
            "MAD",
            "PAR",
            "Aerolineas Argentinas",
            List.of("Internacional"),
            null
        );



        BaseFlightRouteDTO flightRouteDTO2 = new BaseFlightRouteDTO();
        flightRouteDTO2.setName("Paris - Berlin");
        flightRouteDTO2.setDescription("Ruta de vuelo de Paris a Berlin");
        flightRouteDTO2.setCreatedAt(java.time.LocalDate.now());
        flightRouteDTO2.setPriceTouristClass(200.0);
        flightRouteDTO2.setPriceBusinessClass(400.0);
        flightRouteDTO2.setPriceExtraUnitBaggage(70.0);
        flightRouteController.createFlightRoute(
                flightRouteDTO2,
                "PAR",
                "BER",
                "Aerolineas Argentinas",
                List.of("Internacional"),
                null
        );


        BaseFlightDTO flightDTO1 = new BaseFlightDTO();
        flightDTO1.setName("Vuelo1");
        flightDTO1.setDepartureTime(LocalDateTime.now().plusHours(24));
        flightDTO1.setDuration(120L);
        flightDTO1.setMaxEconomySeats(150);
        flightDTO1.setMaxBusinessSeats(50);
        flightDTO1.setCreatedAt(LocalDateTime.now());
        flightController.createFlight(
                flightDTO1,
                "Aerolineas Argentinas",
                "Madrid - Paris",
                null
        );

        BaseFlightDTO flightDTO2 = new BaseFlightDTO();
        flightDTO2.setName("Vuelo2");
        flightDTO2.setDepartureTime(LocalDateTime.now().plusHours(24));
        flightDTO2.setDuration(130L);
        flightDTO2.setMaxEconomySeats(160);
        flightDTO2.setMaxBusinessSeats(40);
        flightDTO2.setCreatedAt(LocalDateTime.now());
        flightController.createFlight(
                flightDTO2,
                "Aerolineas Argentinas",
                "Madrid - Paris",
                null
        );



        BaseFlightDTO flightDTO3 = new BaseFlightDTO();
        flightDTO3.setName("Vuelo3");
        flightDTO3.setDepartureTime(LocalDateTime.now().plusHours(24));
        flightDTO3.setDuration(140L);
        flightDTO3.setMaxEconomySeats(170);
        flightDTO3.setMaxBusinessSeats(30);
        flightDTO3.setCreatedAt(LocalDateTime.now());
        flightController.createFlight(
                flightDTO3,
                "Aerolineas Argentinas",
                "Paris - Berlin",
                null
        );

        BaseFlightDTO flightDTO4 = new BaseFlightDTO();
        flightDTO4.setName("Vuelo4");
        flightDTO4.setDepartureTime(LocalDateTime.now().plusHours(24));
        flightDTO4.setDuration(150L);
        flightDTO4.setMaxEconomySeats(180);
        flightDTO4.setMaxBusinessSeats(20);
        flightDTO4.setCreatedAt(LocalDateTime.now());
        flightController.createFlight(
                flightDTO4,
                "Aerolineas Argentinas",
                "Paris - Berlin",
                null
        );



        // 1: Listar paquetes registrados

        BaseFlightRoutePackageDTO flightRoutePackageDTO = new BaseFlightRoutePackageDTO();
        flightRoutePackageDTO.setName("Paquete1");
        flightRoutePackageDTO.setDescription("Descripcion del paquete 1");
        flightRoutePackageDTO.setValidityPeriodDays(30);
        flightRoutePackageDTO.setDiscount(10.0);
        flightRoutePackageDTO.setCreationDate(java.time.LocalDate.now());
        flightRoutePackageDTO.setSeatType(EnumTipoAsiento.TURISTA);
        flightRoutePackageDTO.setTotalPrice(100.0);
        flightRoutePackageController.createFlightRoutePackage(flightRoutePackageDTO);

        // Añadir la ruta de vuelo "Madrid - Paris" al paquete
        flightRoutePackageController.addFlightRouteToPackage("Paquete1", "Madrid - Paris", 1);
        // Añadir la ruta de vuelo "Paris - Berlin" al paquete
        flightRoutePackageController.addFlightRouteToPackage("Paquete1", "Paris - Berlin", 1);


        List<FlightRoutePackage> packages = flightRoutePackageController.getAllFlightRoutesPackages();

        assertNotNull(packages);
        assertEquals(1, packages.size());
        assertEquals(flightRoutePackageDTO.getName(), packages.get(0).getName());
        assertFalse(packages.get(0).getDescription().isEmpty());

        //Selecciono 1
        FlightRoutePackageDTO selectedPackage = flightRoutePackageController.getFlightRoutePackageDetailsByName("Paquete1");

        //Veo su informacion
        System.out.println(selectedPackage);
        assertTrue(selectedPackage.getName().equals("Paquete1"));
        assertTrue(selectedPackage.getDescription().equals("Descripcion del paquete 1"));
        assertTrue(selectedPackage.getValidityPeriodDays() == 30);
        assertTrue(selectedPackage.getDiscount() == 10.0);
        assertTrue(selectedPackage.getSeatType() == EnumTipoAsiento.TURISTA);
        assertNotNull(selectedPackage.getCreationDate());
        assertNotNull(selectedPackage.getFlightRouteNames()); //Aca deberia de ser la ruta: Madrid - Paris, Paris - Berlin
        assertFalse(selectedPackage.getFlightRouteNames().isEmpty()); //Imprimiria la ruta: Madrid - Paris, Paris - Berlin

        //Selecciono ruta de vuelo del paquete
        FlightRouteDTO flightRouteInPackage = flightRouteController.getFlightRouteDetailsByName("Madrid - Paris");
        //Veo su informacion detallada
        System.out.println(flightRouteInPackage);
        assertNotNull(flightRouteInPackage);
        assertTrue(flightRouteInPackage.getName().equals("Madrid - Paris"));
        assertTrue(flightRouteInPackage.getDescription().equals("Ruta de vuelo de Madrid a Paris"));
        assertTrue(flightRouteInPackage.getPriceTouristClass() == 150.0);
        assertTrue(flightRouteInPackage.getPriceBusinessClass() == 300.0);
        assertTrue(flightRouteInPackage.getPriceExtraUnitBaggage() == 50);
        assertTrue(flightRouteInPackage.getOriginAeroCode().equals("MAD"));
        assertTrue(flightRouteInPackage.getDestinationAeroCode().equals("PAR"));
        assertTrue(flightRouteInPackage.getAirlineNickname().equals("Aerolineas Argentinas"));
        assertNotNull(flightRouteInPackage.getCategoriesNames());
        assertFalse(flightRouteInPackage.getCategoriesNames().isEmpty());
        assertNotNull(flightRouteInPackage.getFlightsNames());
        assertFalse(flightRouteInPackage.getFlightsNames().isEmpty());





    }

}
