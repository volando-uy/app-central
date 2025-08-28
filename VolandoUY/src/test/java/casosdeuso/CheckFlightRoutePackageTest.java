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
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
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
        airlineDTO.setDescription("Línea aérea nacional");
        userController.registerAirline(airlineDTO);


        //Crear categoria de Internacional
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setName("Internacional");
        categoryController.createCategory(categoryDTO);


        //Crear ciudad Madrid
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("Madrid");
        cityDTO.setCountry("España");
        cityDTO.setLatitude(40.4168);
        cityDTO.setLongitude(-3.7038);
        cityDTO.setAirportNames(List.of("Aeropuerto Internacional de Ezeiza"));
        cityController.createCity(cityDTO);

        //Crear ciudad Paris
        CityDTO cityDTO2 = new CityDTO();
        cityDTO2.setName("Paris");
        cityDTO2.setCountry("Francia");
        cityDTO2.setLatitude(48.8566);
        cityDTO2.setLongitude(2.3522);
        cityDTO2.setAirportNames(List.of("Aeropuerto Internacional de Ezeiza"));
        cityController.createCity(cityDTO2);

        //Crear ciudad Berlin
        CityDTO cityDTO3 = new CityDTO();
        cityDTO3.setName("Berlin");
        cityDTO3.setCountry("Alemania");
        cityDTO3.setLatitude(52.5200);
        cityDTO3.setLongitude(13.4050);
        cityDTO3.setAirportNames(List.of("Aeropuerto Internacional de Ezeiza"));
        cityController.createCity(cityDTO3);


        AirportDTO airportDTO = new AirportDTO();
        airportDTO.setName("Aeropuerto Internacional de Ezeiza");
        airportDTO.setCityName("Buenos Aires");
        airportDTO.setCode("EZE");
        airportController.createAirport(airportDTO, "Madrid");




        FlightDTO flightDTO1 = new FlightDTO();
        flightDTO1.setName("Vuelo1");
        flightDTO1.setDepartureTime(LocalDateTime.of(2025, 10, 1, 10, 0));
        flightDTO1.setDuration(120L);
        flightDTO1.setMaxEconomySeats(150);
        flightDTO1.setMaxBusinessSeats(50);
        flightDTO1.setCreatedAt(LocalDateTime.now());
        flightDTO1.setAirlineNickname("Aerolineas Argentinas");
        flightController.createFlight(flightDTO1);

        FlightDTO flightDTO2 = new FlightDTO();
        flightDTO2.setName("Vuelo2");
        flightDTO2.setDepartureTime(LocalDateTime.of(2025, 10, 2, 15, 0));
        flightDTO2.setDuration(130L);
        flightDTO2.setMaxEconomySeats(160);
        flightDTO2.setMaxBusinessSeats(40);
        flightDTO2.setCreatedAt(LocalDateTime.now());
        flightDTO2.setAirlineNickname("Aerolineas Argentinas");
        flightController.createFlight(flightDTO2);



        FlightDTO flightDTO3 = new FlightDTO();
        flightDTO3.setName("Vuelo3");
        flightDTO3.setDepartureTime(LocalDateTime.of(2025, 11, 1, 9, 0));
        flightDTO3.setDuration(140L);
        flightDTO3.setMaxEconomySeats(170);
        flightDTO3.setMaxBusinessSeats(30);
        flightDTO3.setCreatedAt(LocalDateTime.now());
        flightDTO3.setAirlineNickname("Aerolineas Argentinas");
        flightController.createFlight(flightDTO3);

        FlightDTO flightDTO4 = new FlightDTO();
        flightDTO4.setName("Vuelo4");
        flightDTO4.setDepartureTime(LocalDateTime.of(2025, 11, 2, 14, 0));
        flightDTO4.setDuration(150L);
        flightDTO4.setMaxEconomySeats(180);
        flightDTO4.setMaxBusinessSeats(20);
        flightDTO4.setCreatedAt(LocalDateTime.now());
        flightDTO4.setAirlineNickname("Aerolineas Argentinas");
        flightController.createFlight(flightDTO4);





        FlightRouteDTO flightRouteDTO = new FlightRouteDTO();
        flightRouteDTO.setName("Madrid - Paris");
        flightRouteDTO.setDescription("Ruta de vuelo de Madrid a Paris");
        flightRouteDTO.setCreatedAt(java.time.LocalDate.now());
        flightRouteDTO.setPriceTouristClass(150.0);
        flightRouteDTO.setPriceBusinessClass(300.0);
        flightRouteDTO.setPriceExtraUnitBaggage(50.0);
        flightRouteDTO.setOriginCityName("Madrid");
        flightRouteDTO.setDestinationCityName("Paris");
        flightRouteDTO.setAirlineNickname("Aerolineas Argentinas");
        flightRouteDTO.setCategories(List.of("Internacional"));
        flightRouteDTO.setFlightsNames(List.of("Vuelo1", "Vuelo2"));
        flightRouteController.createFlightRoute(flightRouteDTO);



        FlightRouteDTO flightRouteDTO2 = new FlightRouteDTO();
        flightRouteDTO2.setName("Paris - Berlin");
        flightRouteDTO2.setDescription("Ruta de vuelo de Paris a Berlin");
        flightRouteDTO2.setCreatedAt(java.time.LocalDate.now());
        flightRouteDTO2.setPriceTouristClass(200.0);
        flightRouteDTO2.setPriceBusinessClass(400.0);
        flightRouteDTO2.setPriceExtraUnitBaggage(70.0);
        flightRouteDTO2.setOriginCityName("Paris");
        flightRouteDTO2.setDestinationCityName("Berlin");
        flightRouteDTO2.setAirlineNickname("Aerolineas Argentinas");
        flightRouteDTO2.setCategories(List.of("Internacional"));
        flightRouteDTO2.setFlightsNames(List.of("Vuelo3", "Vuelo4"));
        flightRouteController.createFlightRoute(flightRouteDTO2);



        // 1: Listar paquetes registrados

        FlightRoutePackageDTO flightRoutePackageDTO = new FlightRoutePackageDTO();
        flightRoutePackageDTO.setName("Paquete1");
        flightRoutePackageDTO.setDescription("Descripcion del paquete 1");
        flightRoutePackageDTO.setValidityPeriodDays(30);
        flightRoutePackageDTO.setDiscount(10.0);
        flightRoutePackageDTO.setCreationDate(java.time.LocalDate.now());
        flightRoutePackageDTO.setSeatType(EnumTipoAsiento.TURISTA);
        flightRoutePackageDTO.setFlightRouteNames(List.of("Madrid - Paris", "Paris - Berlin"));
        flightRoutePackageController.createFlightRoutePackage(flightRoutePackageDTO);


        List<FlightRoutePackage> packages = flightRoutePackageController.getAllFlightRoutePackages();

        assertNotNull(packages);
        assertEquals(1, packages.size());
        assertEquals(flightRoutePackageDTO.getName(), packages.get(0).getName());
        assertFalse(packages.get(0).getDescription().isEmpty());

        System.out.println(packages);
        //Selecciono 1
        FlightRoutePackageDTO selectedPackage = flightRoutePackageController.getFlightRoutePackageByName("Paquete1");
        //Veo su informacion
        System.out.println(selectedPackage);
        assertTrue(selectedPackage.getName().equals("Paquete1"));
        assertTrue(selectedPackage.getDescription().equals("Descripcion del paquete 1"));
        assertTrue(selectedPackage.getValidityPeriodDays() == 30);
        assertTrue(selectedPackage.getDiscount() == 10.0);
        assertTrue(selectedPackage.getSeatType() == EnumTipoAsiento.TURISTA);
        assertNotNull(selectedPackage.getCreationDate());
        assertNotNull(selectedPackage.getFlightRouteNames()); //Aca deberia de ser la ruta: Madrid - Paris, Paris - Berlin
        assertFalse(selectedPackage.getFlightRouteNames().isEmpty());

        //Selecciono ruta de vuelo del paquete
        FlightRouteDTO flightRouteInPackage = flightRouteController.getFlightRouteByName("Madrid - Paris");
        //Veo su informacion detallada
        System.out.println(flightRouteInPackage);
        assertNotNull(flightRouteInPackage);
        assertTrue(flightRouteInPackage.getName().equals("Madrid - Paris"));
        assertTrue(flightRouteInPackage.getDescription().equals("Ruta de vuelo de Madrid a Paris"));
        assertTrue(flightRouteInPackage.getPriceTouristClass() == 150.0);
        assertTrue(flightRouteInPackage.getPriceBusinessClass() == 300.0);
        assertTrue(flightRouteInPackage.getPriceExtraUnitBaggage() == 50);
        assertTrue(flightRouteInPackage.getOriginCityName().equals("Madrid"));
        assertTrue(flightRouteInPackage.getDestinationCityName().equals("Paris"));
        assertTrue(flightRouteInPackage.getAirlineNickname().equals("Aerolineas Argentinas"));
        assertNotNull(flightRouteInPackage.getCategories());
        assertFalse(flightRouteInPackage.getCategories().isEmpty());
        assertNotNull(flightRouteInPackage.getFlightsNames());
        assertFalse(flightRouteInPackage.getFlightsNames().isEmpty());





    }

}
