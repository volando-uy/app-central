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
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BaseBuyPackageDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.flightRoutePackage.BaseFlightRoutePackageDTO;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.dtos.luggage.BaseBasicLuggageDTO;
import domain.dtos.luggage.BaseExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.user.*;
import domain.dtos.user.BaseAirlineDTO;
import domain.models.buypackage.BuyPackage;
import domain.models.enums.EnumEstatusRuta;
import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import domain.models.luggage.EnumEquipajeBasico;
import domain.models.luggage.EnumEquipajeExtra;
import domain.models.user.Customer;
import factory.ControllerFactory;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBInitThread extends Thread {


    @Override
    public void run() {
        try {
            Thread.currentThread().setPriority(Thread.NORM_PRIORITY);
            seed();
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

    private void seed() {
        DBConnection.cleanDB();

        List<BaseAirlineDTO> airlines = seed_generateAirlines();
        System.out.println("Airlines: " + airlines);

        List<BaseCustomerDTO> customers = seed_generateCustomers();
        System.out.println("Customers: " + customers);

        List<CategoryDTO> categories = seed_generateCategories();
        System.out.println("Categories: " + categories);

        List<BaseCityDTO> cities = seed_generateCities();
        System.out.println("Cities: " + cities);

        List<BaseAirportDTO> airports = seed_generateAirports(cities);
        System.out.println("Airports: " + airports);

        List<FlightRouteDTO> flightRoutes = seed_generateFlightRoutes(airlines, cities, categories);
        System.out.println("Flight Routes: " + flightRoutes);

        // Se necesita la aerolinea también para crear el vuelo
        // Pero la aerolinea ya va dentro de cada ruta de vuelo.
        List<BaseFlightDTO> flights = seed_generateFlights(flightRoutes);
        System.out.println("Flights: " + flights);

        List<BaseFlightRoutePackageDTO> flightRoutePackages = seed_generateFlightRoutePackages(flightRoutes);
        System.out.println("Flight Route Packages: " + flightRoutePackages);

        // TODO: Agregar seed para reservas de vuelos y compras de paquetes
        List<BaseBookFlightDTO> bookFlights = seed_generateBookFlights(customers, flights);
        System.out.println("Book Flights: " + bookFlights);

        List<BaseBuyPackageDTO> buyPackages = seed_generateBuyPackages(customers, flightRoutePackages);
        System.out.println("Buy Packages: " + buyPackages);
    }

    private List<BaseAirlineDTO> seed_generateAirlines() {
        List<String> airlinesNames = List.of(
                "Aerolineas Argentinas",
                "LATAM",
                "American Airlines",
                "Delta",
                "United Airlines",
                "Uruguayan Airlines",
                "Iberia"
        );

        List<BaseAirlineDTO> airlinesDTOs = new ArrayList<>();
        for (String airlineName : airlinesNames) {
            BaseAirlineDTO baseAirlineDTO = new BaseAirlineDTO();
            baseAirlineDTO.setName(airlineName);
            baseAirlineDTO.setNickname(airlineName.replaceAll(" ", "").toLowerCase());
            baseAirlineDTO.setMail("admin@" + baseAirlineDTO.getNickname() + ".com");
            baseAirlineDTO.setPassword("Password123!");
            baseAirlineDTO.setWeb("www." + baseAirlineDTO.getNickname() + ".com");
            baseAirlineDTO.setDescription("Somos" + airlineName + ", la mejor aerolínea del mundo.");

            BaseAirlineDTO createdAirline = ControllerFactory.getUserController().registerAirline(baseAirlineDTO);
            airlinesDTOs.add(createdAirline);
        }

        return airlinesDTOs;
    }

    private List<BaseCustomerDTO> seed_generateCustomers() {
        Map<String, String> customerNames = Map.of(
                "Juan Perez", "uruguayo",
                "Maria Gomez", "argentina",
                "Carlos Sanchez", "chileno",
                "Ana Martinez", "brasilena",
                "Luis Rodriguez", "colombiano",
                "Sofia Fernandez", "mexicana",
                "Diego Lopez" , "peruano"
        );

        List<BaseCustomerDTO> customersDTOs = new ArrayList<>();
        for (Map.Entry<String, String> entry : customerNames.entrySet()) {
            String customerName = entry.getKey();
            String citizenship = entry.getValue();

            String[] nameParts = customerName.split(" ");
            String name = nameParts[0];
            String surname = nameParts.length > 1 ? nameParts[1] : "";

            BaseCustomerDTO baseCustomerDTO = new BaseCustomerDTO();
            baseCustomerDTO.setName(name);
            baseCustomerDTO.setSurname(surname);
            baseCustomerDTO.setNickname((name.charAt(0) + surname).toLowerCase());
            baseCustomerDTO.setMail(baseCustomerDTO.getNickname() + "@gmail.com");
            baseCustomerDTO.setPassword("Password123!");
            baseCustomerDTO.setCitizenship(citizenship);
            baseCustomerDTO.setBirthDate(LocalDate.now().minusYears(18 + (int) (Math.random() * 30))); // Edad entre 18 y 48
            baseCustomerDTO.setDocType(EnumTipoDocumento.CI);
            baseCustomerDTO.setNumDoc(String.valueOf(10000000 + (int) (Math.random() * 90000000)));

            BaseCustomerDTO createdCustomer = ControllerFactory.getUserController().registerCustomer(baseCustomerDTO);
            customersDTOs.add(createdCustomer);
        }

        return customersDTOs;
    }

    private List<CategoryDTO> seed_generateCategories() {
        List<String> categoryNames = List.of(
                "Nacional",
                "Internacional",
                "Low Cost",
                "Premium",
                "Charter",
                "Regional",
                "Transcontinental"
        );

        List<CategoryDTO> categoryDTOs = new ArrayList<>();
        for (String categoryName : categoryNames) {
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setName(categoryName);

            CategoryDTO createdCategory = ControllerFactory.getCategoryController().createCategory(categoryDTO);
            categoryDTOs.add(createdCategory);
        }

        return categoryDTOs;
    }

    private List<BaseCityDTO> seed_generateCities() {
        List<String> cityNames = List.of(
                "Madrid",
                "Barcelona",
                "Brooklyn",
                "Seattle",
                "Chicago",
                "Miami"
        );

        List<BaseCityDTO> citiesDTOs = new ArrayList<>();
        for (String cityName : cityNames) {
            BaseCityDTO baseCityDTO = new BaseCityDTO();
            baseCityDTO.setName(cityName);
            baseCityDTO.setCountry("Country of " + cityName);
            baseCityDTO.setLatitude(Math.random() * 180 - 90); // Latitud aleatoria entre -90 y 90
            baseCityDTO.setLongitude(Math.random() * 360 - 180); // Longitud aleatoria entre -180 y 180

            BaseCityDTO createdCity = ControllerFactory.getCityController().createCity(baseCityDTO);
            citiesDTOs.add(createdCity);
        }

        return citiesDTOs;
    }

    private List<BaseAirportDTO> seed_generateAirports(List<BaseCityDTO> cities) {
        IAirportController airportController = ControllerFactory.getAirportController();
        List<String> airportNames = List.of(
                "International Airport",
                "City Airport",
                "Regional Airport",
                "Domestic Airport",
                "Metropolitan Airport"
        );

        List<BaseAirportDTO> airportsDTOs = new ArrayList<>();
        for (BaseCityDTO city : cities) {
            int numAirports = (int) (Math.random() * 2) + 1; // Entre 1 y 2 aeropuertos por ciudad
            for (int i = 1; i <= numAirports; i++) {
                BaseAirportDTO baseAirportDTO = new BaseAirportDTO();
                // Ejemplo de nombre: "Madrid International Airport"
                baseAirportDTO.setName(city.getName() + " " + airportNames.get((int) (Math.random() * airportNames.size())));
                // Ejemplos de código: Madrid: "MAD", "ADR", etc.
                baseAirportDTO.setCode(city.getName().substring(-1 + i, 2 + i).toUpperCase()); // Código basado en el nombre de la ciudad

                BaseAirportDTO createdAirport = airportController.createAirport(baseAirportDTO, city.getName());
                airportsDTOs.add(createdAirport);
            }
        }

        return airportsDTOs;
    }

    private List<FlightRouteDTO> seed_generateFlightRoutes(List<BaseAirlineDTO> airlines, List<BaseCityDTO> cities, List<CategoryDTO> categories) {
        IFlightRouteController flightRouteController = ControllerFactory.getFlightRouteController();

        List<FlightRouteDTO> flightRoutesDTOs = new ArrayList<>();
        for (BaseCityDTO originCity : cities) {
            for (BaseCityDTO destinationCity : cities) {
                if (originCity.equals(destinationCity)) continue; // Evitar rutas con la misma ciudad de origen y destino
                if (Math.random() > 0.5) continue; // 50% de probabilidad de crear una ruta entre estas dos ciudades

                // Seleccionar una aerolínea aleatoria
                BaseAirlineDTO airline = airlines.get((int) (Math.random() * airlines.size()));

                // Creamos los datos base de la nueva ruta
                BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
                baseFlightRouteDTO.setName(originCity.getName() + " -> " + destinationCity.getName());
                baseFlightRouteDTO.setDescription("Viaje desde " + originCity.getName() + " hasta " + destinationCity.getName() + " con " + airline.getName());
                baseFlightRouteDTO.setCreatedAt(LocalDate.now());
                baseFlightRouteDTO.setPriceTouristClass(100.0 + Math.random() * 400); // Precio entre 100 y 500
                baseFlightRouteDTO.setPriceBusinessClass(500.0 + Math.random() * 1000); // Precio entre 500 y 1500
                baseFlightRouteDTO.setPriceExtraUnitBaggage(50.0 + Math.random() * 150); // Precio entre 50 y 200

                // Seleccionar entre 1 y 3 categorías aleatorias
                int numCategories = (int) (Math.random() * 3) + 1;
                List<String> categoryNames = new ArrayList<>();
                List<CategoryDTO> categoriesCopy = new ArrayList<>(categories);
                for (int i = 0; i < numCategories; i++) {
                    if (categoriesCopy.isEmpty()) break;
                    int index = (int) (Math.random() * categoriesCopy.size());
                    categoryNames.add(categoriesCopy.get(index).getName());
                    categoriesCopy.remove(index);
                }

                // Crear la ruta de vuelo
                flightRouteController.createFlightRoute(
                        baseFlightRouteDTO,
                        originCity.getName(),
                        destinationCity.getName(),
                        airline.getNickname(),
                        categoryNames
                );

                // Agarrar la ruta de vuelo con todas las relaciones
                FlightRouteDTO createdFlightRoute = flightRouteController.getFlightRouteDetailsByName(baseFlightRouteDTO.getName());

                // 80% de probabilidad de cambiar el estado de la ruta: 60% de confirmarla, 40% de rechazarla
                if (Math.random() < 0.8) {
                    boolean confirm = Math.random() < 0.6;
                    flightRouteController.setStatusFlightRouteByName(createdFlightRoute.getName(), confirm);
                    if (confirm) {
                        createdFlightRoute.setStatus(EnumEstatusRuta.CONFIRMADA);
                        flightRoutesDTOs.add(createdFlightRoute);
                    }
                }

            }
        }

        return flightRoutesDTOs;
    }

    private List<BaseFlightDTO> seed_generateFlights(List<FlightRouteDTO> flightRoutes) {
        IFlightController flightController = ControllerFactory.getFlightController();

        List<BaseFlightDTO> flightsDTOs = new ArrayList<>();
        for (FlightRouteDTO flightRoute : flightRoutes) {
            int numFlights = (int) (Math.random() * 15); // Entre 0 y 14 vuelos por ruta
            for (int i = 1; i <= numFlights; i++) {
                BaseFlightDTO baseFlightDTO = new BaseFlightDTO();
                baseFlightDTO.setName(flightRoute.getName() + " Flight " + i);
                baseFlightDTO.setDepartureTime(LocalDateTime.now().plusDays((int) (Math.random() * 60) + 1)); // Fecha de salida en los próximos 60 días
                baseFlightDTO.setDuration((long) (60 + Math.random() * 300)); // Duración entre 60 y 360 minutos
                baseFlightDTO.setMaxEconomySeats(20 + (int) (Math.random() * 30)); // Entre 20 y 50 asientos en clase económica
                baseFlightDTO.setMaxBusinessSeats(5 + (int) (Math.random() * 10)); // Entre 5 y 15 asientos en clase business
                baseFlightDTO.setCreatedAt(LocalDateTime.now());

                // Crear el vuelo
                BaseFlightDTO createdFlight = flightController.createFlight(
                        baseFlightDTO,
                        flightRoute.getAirlineNickname(),
                        flightRoute.getName()
                );

                flightsDTOs.add(createdFlight);
            }
        }

        return flightsDTOs;
    }

    private List<BaseFlightRoutePackageDTO> seed_generateFlightRoutePackages(List<FlightRouteDTO> flightRoutes) {
        IFlightRoutePackageController flightRoutePackageController = ControllerFactory.getFlightRoutePackageController();

        List<BaseFlightRoutePackageDTO> flightRoutePackagesDTOs = new ArrayList<>();
        int numPackages = 5; // Crear 5 paquetes de rutas de vuelo
        for (int i = 1; i <= numPackages; i++) {
            BaseFlightRoutePackageDTO baseFlightRoutePackageDTO = new BaseFlightRoutePackageDTO();
            baseFlightRoutePackageDTO.setName("Package " + i);
            baseFlightRoutePackageDTO.setDescription("Descripción del paquete " + i);
            baseFlightRoutePackageDTO.setValidityPeriodDays(30 + (int) (Math.random() * 60)); // Validez entre 30 y 90 días
            baseFlightRoutePackageDTO.setDiscount(10.0 + Math.random() * 40); // Descuento entre 10% y 50%
            baseFlightRoutePackageDTO.setCreationDate(LocalDate.now().minusDays((int) (Math.random() * 30))); // Fecha de creación en los últimos 30 días
            baseFlightRoutePackageDTO.setSeatType(Math.random() > 0.5 ? EnumTipoAsiento.TURISTA : EnumTipoAsiento.EJECUTIVO);
            baseFlightRoutePackageDTO.setTotalPrice(0.0); // Se calculará luego

            // Crear el paquete de rutas de vuelo
            // En este caso no precisamos el paquete con todas sus relaciones
            BaseFlightRoutePackageDTO createdPackage = flightRoutePackageController.createFlightRoutePackage(
                    baseFlightRoutePackageDTO
            );
            flightRoutePackagesDTOs.add(createdPackage);


            // Seleccionar entre 1 y 3 rutas de vuelo aleatorias para el paquete
            int numRoutesInPackage = (int) (Math.random() * 3) + 1;
            List<String> routeNames = new ArrayList<>();
            List<FlightRouteDTO> flightRoutesCopy = new ArrayList<>(flightRoutes);
            for (int j = 0; j < numRoutesInPackage; j++) {
                if (flightRoutesCopy.isEmpty()) break;
                int index = (int) (Math.random() * flightRoutesCopy.size());
                routeNames.add(flightRoutesCopy.get(index).getName());
                flightRoutesCopy.remove(index);
            }

            // Agregar las rutas de vuelo al paquete
            for (int j = 0; j < numRoutesInPackage; j++) {
                flightRoutePackageController.addFlightRouteToPackage(
                        baseFlightRoutePackageDTO.getName(),
                        routeNames.get(j),
                        1
                );
            }
        }

        return flightRoutePackagesDTOs;
    }

    private List<BaseBookFlightDTO> seed_generateBookFlights(
            List<BaseCustomerDTO> customers,
            List<BaseFlightDTO> flights
    ) {

        List<BaseBookFlightDTO> bookFlightsDTOs = new ArrayList<>();
        int numBookings = 10; // Crear 10 reservas de vuelos
        for (int i = 1; i <= numBookings; i++) {
            if (flights.isEmpty() || customers.isEmpty()) break;

            // Seleccionar un cliente aleatorio
            String customerNickname = customers.get((int) (Math.random() * customers.size())).getNickname();

            // Seleccionar un vuelo aleatorio
            String flightName = flights.get((int) (Math.random() * flights.size())).getName();


            BaseBookFlightDTO baseBookFlightDTO = new BaseBookFlightDTO();

            // Seleccionar tipo de asiento aleatoriamente
            EnumTipoAsiento seatType = Math.random() > 0.5 ? EnumTipoAsiento.TURISTA : EnumTipoAsiento.EJECUTIVO;
            baseBookFlightDTO.setSeatType(seatType);

            baseBookFlightDTO.setCreatedAt(LocalDateTime.now());
            baseBookFlightDTO.setTotalPrice(0.0); // Se calculará luego

            // Crear los tickets con sus equipajes
            Map<BaseTicketDTO, List<LuggageDTO>> tickets = new HashMap<>();
            int numTickets = 1 + (int) (Math.random() * 3); // Entre 1 y 3 tickets por reserva
            for (int j = 1; j <= numTickets; j++) {
                BaseTicketDTO baseTicketDTO = new BaseTicketDTO();
                baseTicketDTO.setName("Passenger " + j);
                baseTicketDTO.setSurname("Surname " + j);
                baseTicketDTO.setDocType(EnumTipoDocumento.CI);
                baseTicketDTO.setNumDoc(String.valueOf(20000000 + (int) (Math.random() * 80000000)));


                // Crear lista de equipajes (1 básico + equipajes extra)
                List<LuggageDTO> luggageDTOs = new ArrayList<>();

                // 1 basic luggage per ticket
                BaseBasicLuggageDTO basicLuggageDTO = new BaseBasicLuggageDTO();
                basicLuggageDTO.setWeight(15.0 + Math.random() * 10);
                basicLuggageDTO.setCategory(EnumEquipajeBasico.MOCHILA);
                luggageDTOs.add(basicLuggageDTO);

                // 50% de probabilidad de agregar 1 equipaje extra
                if (Math.random() > 0.5) {
                    BaseExtraLuggageDTO extraLuggageDTO = new BaseExtraLuggageDTO();
                    extraLuggageDTO.setWeight(20.0 + Math.random() * 20);
                    extraLuggageDTO.setCategory(EnumEquipajeExtra.MALETA);
                    luggageDTOs.add(extraLuggageDTO);
                }

                tickets.put(baseTicketDTO, luggageDTOs);
            }

            // Crear la reserva de vuelo
            BaseBookFlightDTO createdBooking = ControllerFactory.getBookingController().createBooking(
                    baseBookFlightDTO,
                    tickets,
                    customerNickname,
                    flightName
            );
            bookFlightsDTOs.add(createdBooking);
        }

        return bookFlightsDTOs;
    }

    private List<BaseBuyPackageDTO> seed_generateBuyPackages(
            List<BaseCustomerDTO> customers,
            List<BaseFlightRoutePackageDTO> flightRoutePackages
    ) {

        Map<String, String> alreadyBought = new HashMap<>();
        List<BaseBuyPackageDTO> boughtPackagesDTOs = new ArrayList<>();
        int numPurchases = 10; // Crear 10 compras de paquetes
        for (int i = 1; i <= numPurchases; i++) {
            if (flightRoutePackages.isEmpty() || customers.isEmpty()) break;

            // Seleccionar un cliente aleatorio
            String customerNickname = customers.get((int) (Math.random() * customers.size())).getNickname();

            // Seleccionar un paquete de rutas de vuelo aleatorio
            String packageName = flightRoutePackages.get((int) (Math.random() * flightRoutePackages.size())).getName();

            if (alreadyBought.containsKey(customerNickname) &&
                    alreadyBought.get(customerNickname).equals(packageName)) {
                // El cliente ya compró este paquete, saltar esta iteración
                continue;
            }

            // Crear la compra del paquete
            BaseBuyPackageDTO boughtPackageDTO = ControllerFactory.getBuyPackageController().createBuyPackage(
                    customerNickname,
                    packageName
            );

            boughtPackagesDTOs.add(boughtPackageDTO);
            alreadyBought.put(customerNickname, packageName);
        }

        return boughtPackagesDTOs;
    }
}
