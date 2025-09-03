package app.config;

import app.DBConnection;
import controllers.airport.IAirportController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.user.IUserController;
import domain.dtos.airport.AirportDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import factory.ControllerFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DBInitThread extends Thread {

    @Override
    public void run() {
        try {
            Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            // initDB();
            testConnection();
            System.out.println("DB inicializada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void testConnection() {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createNativeQuery("SELECT 1").getSingleResult();
            em.getTransaction().commit();
        } finally {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            em.close();
        }
    }
    private void initDB() {
        DBConnection.cleanDB();

        IUserController userController = ControllerFactory.getUserController();
        ICategoryController categoryController = ControllerFactory.getCategoryController();
        ICityController cityController = ControllerFactory.getCityController();
        IFlightController flightController = ControllerFactory.getFlightController();
        IFlightRouteController flightRouteController = ControllerFactory.getFlightRouteController();
        IFlightRoutePackageController flightRoutePackageController = ControllerFactory.getFlightRoutePackageController();
        IAirportController airportController = ControllerFactory.getAirportController();
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
        //flightRouteDTO.setCategories(List.of("Internacional"));
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
        // flightRouteDTO2.setCategories(List.of("Internacional"));
        flightRouteDTO2.setFlightsNames(List.of("Vuelo3", "Vuelo4"));
        flightRouteController.createFlightRoute(flightRouteDTO2);


        FlightDTO flightDTO1 = new FlightDTO();
        flightDTO1.setName("Vuelo1");
        flightDTO1.setDepartureTime(LocalDateTime.of(2025, 10, 1, 10, 0));
        flightDTO1.setDuration(120L);
        flightDTO1.setMaxEconomySeats(150);
        flightDTO1.setMaxBusinessSeats(50);
        flightDTO1.setCreatedAt(LocalDateTime.now());
        flightDTO1.setAirlineNickname("Aerolineas Argentinas");
        flightDTO1.setFlightRouteName("Paris - Berlin");
        flightController.createFlight(flightDTO1);

        FlightDTO flightDTO2 = new FlightDTO();
        flightDTO2.setName("Vuelo2");
        flightDTO2.setDepartureTime(LocalDateTime.of(2025, 10, 2, 15, 0));
        flightDTO2.setDuration(130L);
        flightDTO2.setMaxEconomySeats(160);
        flightDTO2.setMaxBusinessSeats(40);
        flightDTO2.setCreatedAt(LocalDateTime.now());
        flightDTO2.setAirlineNickname("Aerolineas Argentinas");
        flightDTO2.setFlightRouteName("Madrid - Paris");
        flightController.createFlight(flightDTO2);


        FlightDTO flightDTO3 = new FlightDTO();
        flightDTO3.setName("Vuelo3");
        flightDTO3.setDepartureTime(LocalDateTime.of(2025, 11, 1, 9, 0));
        flightDTO3.setDuration(140L);
        flightDTO3.setMaxEconomySeats(170);
        flightDTO3.setMaxBusinessSeats(30);
        flightDTO3.setCreatedAt(LocalDateTime.now());
        flightDTO3.setAirlineNickname("Aerolineas Argentinas");
        flightDTO3.setFlightRouteName("Madrid - Paris");
        flightController.createFlight(flightDTO3);

        FlightDTO flightDTO4 = new FlightDTO();
        flightDTO4.setName("Vuelo4");
        flightDTO4.setDepartureTime(LocalDateTime.of(2025, 11, 2, 14, 0));
        flightDTO4.setDuration(150L);
        flightDTO4.setMaxEconomySeats(180);
        flightDTO4.setMaxBusinessSeats(20);
        flightDTO4.setCreatedAt(LocalDateTime.now());
        flightDTO4.setAirlineNickname("Aerolineas Argentinas");
        flightDTO4.setFlightRouteName("Paris - Berlin");
        flightController.createFlight(flightDTO4);



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


        //========================================

        // Crear clientes
        CustomerDTO customerDTO1 = new CustomerDTO();
        customerDTO1.setNickname("customer1");
        customerDTO1.setName("Quian");
        customerDTO1.setSurname("Aparicio");
        customerDTO1.setMail("waza@gmail.com");
        customerDTO1.setCitizenship("uruguayo");
        customerDTO1.setBirthDate(LocalDate.of(2004, 12, 12));
        customerDTO1.setNumDoc("123123123");
        customerDTO1.setDocType(EnumTipoDocumento.CI);
        userController.registerCustomer(customerDTO1);

        CustomerDTO customerDTO2 = new CustomerDTO();
        customerDTO2.setNickname("customer2");
        customerDTO2.setName("Gonzalez");
        customerDTO2.setSurname("Nahuel");
        customerDTO2.setMail("waza2@gmail.com");
        customerDTO2.setCitizenship("uruguayo");
        customerDTO2.setBirthDate(LocalDate.of(2003, 1, 1));
        customerDTO2.setNumDoc("123123124");
        customerDTO2.setDocType(EnumTipoDocumento.CI);
        userController.registerCustomer(customerDTO2);

// Crear aerolíneas
        AirlineDTO airlineDTO1 = new AirlineDTO();
        airlineDTO1.setNickname("airline1");
        airlineDTO1.setName("Aerolíneas Argentinas");
        airlineDTO1.setMail("aa@mail.com");
        airlineDTO1.setDescription("Aerolíneas Argentinas S.A.");
        airlineDTO1.setWeb("www.aerolineas.com.ar");
        userController.registerAirline(airlineDTO1);

        AirlineDTO airlineDTO2 = new AirlineDTO();
        airlineDTO2.setNickname("airline2");
        airlineDTO2.setName("LATAM Airlines");
        airlineDTO2.setMail("ltm@gmail.com");
        airlineDTO2.setDescription("LATAM Airlines Group S.A.");
        airlineDTO2.setWeb("www.latam.com");
        userController.registerAirline(airlineDTO2);

// Crear paquetes de rutas de vuelo
        FlightRoutePackageDTO packageDTO1 = new FlightRoutePackageDTO();
        packageDTO1.setName("package1");
        packageDTO1.setDescription("Paquete de vuelo 1");
        packageDTO1.setValidityPeriodDays(10);
        packageDTO1.setDiscount(50.0);
        packageDTO1.setCreationDate(LocalDate.now());
        packageDTO1.setSeatType(EnumTipoAsiento.TURISTA);
        flightRoutePackageController.createFlightRoutePackage(packageDTO1);

        FlightRoutePackageDTO packageDTO2 = new FlightRoutePackageDTO();
        packageDTO2.setName("package2");
        packageDTO2.setDescription("Paquete de vuelo 2");
        packageDTO2.setValidityPeriodDays(20);
        packageDTO2.setDiscount(100.0);
        packageDTO2.setCreationDate(LocalDate.now().plusDays(1));
        packageDTO2.setSeatType(EnumTipoAsiento.EJECUTIVO);
        flightRoutePackageController.createFlightRoutePackage(packageDTO2);

// Crear categorías
        CategoryDTO categoryDTO1 = new CategoryDTO();
        categoryDTO1.setName("category1");
        categoryController.createCategory(categoryDTO1);

        CategoryDTO categoryDTO2 = new CategoryDTO();
        categoryDTO2.setName("category2");
        categoryController.createCategory(categoryDTO2);

// Crear ciudades
        CityDTO city1 = new CityDTO();
        city1.setName("San José");
        city1.setCountry("Uruguay");
        city1.setLatitude(50.0);
        city1.setLongitude(50.0);
        city1.setAirportNames(new ArrayList<>());
        cityController.createCity(city1);

        CityDTO city2 = new CityDTO();
        city2.setName("Montevideo");
        city2.setCountry("Uruguay");
        city2.setLatitude(60.0);
        city2.setLongitude(60.0);
        city2.setAirportNames(new ArrayList<>());
        cityController.createCity(city2);

// Crear rutas de vuelo
        FlightRouteDTO routeDTO1 = new FlightRouteDTO();
        routeDTO1.setName("route1");
        routeDTO1.setDescription("Ruta de vuelo 1");
        routeDTO1.setCreatedAt(LocalDate.now());
        routeDTO1.setPriceTouristClass(100.0);
        routeDTO1.setPriceBusinessClass(200.0);
        routeDTO1.setPriceExtraUnitBaggage(50.0);
        routeDTO1.setOriginCityName("San José");
        routeDTO1.setDestinationCityName("Montevideo");
        routeDTO1.setAirlineNickname("airline1");
        // routeDTO1.setCategories(List.of("category1", "category2"));
        flightRouteController.createFlightRoute(routeDTO1);

        FlightRouteDTO routeDTO2 = new FlightRouteDTO();
        routeDTO2.setName("route2");
        routeDTO2.setDescription("Ruta de vuelo 2");
        routeDTO2.setCreatedAt(LocalDate.now().plusDays(1));
        routeDTO2.setPriceTouristClass(150.0);
        routeDTO2.setPriceBusinessClass(250.0);
        routeDTO2.setPriceExtraUnitBaggage(75.0);
        routeDTO2.setOriginCityName("Montevideo");
        routeDTO2.setDestinationCityName("San José");
        routeDTO2.setAirlineNickname("airline2");
        // routeDTO2.setCategories(List.of("category1"));
        flightRouteController.createFlightRoute(routeDTO2);

    }

}
