package casosdeuso;

import controllers.airport.IAirportController;
import controllers.booking.IBookingController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.ticket.ITicketController;
import controllers.user.IUserController;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.luggage.*;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.dtos.user.BaseCustomerDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import domain.models.luggage.EnumEquipajeExtra;
import domain.models.luggage.EnumEquipajeBasico;
import factory.ControllerFactory;
import org.junit.jupiter.api.*;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookFlightTest {

    IUserController userController;
    IFlightRouteController flightRouteController;
    ICategoryController categoryController;
    ICityController cityController;
    IFlightController flightController;
    IBookingController bookingController;
    ITicketController ticketController;
    IAirportController airportController;

    @BeforeAll
    void setUp() {
        TestUtils.cleanDB();
        userController = ControllerFactory.getUserController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        categoryController = ControllerFactory.getCategoryController();
        cityController = ControllerFactory.getCityController();
        flightController = ControllerFactory.getFlightController();
        bookingController = ControllerFactory.getBookingController();
        ticketController = ControllerFactory.getTicketController();
        airportController = ControllerFactory.getAirportController();

        // Crear dos aerolineas
        BaseAirlineDTO airlineDTO = new BaseAirlineDTO() {{
            setNickname("LAT1");
            setName("LATAM");
            setMail("latam@gmail.com");
            setPassword("password");
            setDescription("LATAMfedafewafewafewa");
            setWeb("https://www.google.com");
        }};
        userController.registerAirline(airlineDTO, null);

        BaseAirlineDTO airlineDTO2 = new BaseAirlineDTO() {{
            setNickname("LAT2");
            setName("LATAM2");
            setMail("latam2@gmail.com");
            setPassword("password2");
            setDescription("LATAMfedafewafewafewa");
            setWeb("https://www.google.com");
        }};
        userController.registerAirline(airlineDTO2, null);

        //Crear 3 customers
        BaseCustomerDTO customer1 = new BaseCustomerDTO() {{
            setNickname("customer1");
            setName("Customer 1");
            setMail("a@gmail.com");
            setPassword("password");
            setSurname("Apellido1");
            setCitizenship("Argentina");
            setBirthDate(LocalDate.of(1990, 1, 1));
            setNumDoc("12345678");
            setDocType(EnumTipoDocumento.CI);
        }};
        userController.registerCustomer(customer1, null);

        BaseCustomerDTO customer2 = new BaseCustomerDTO() {{
            setNickname("customer2");
            setName("Customer 2");
            setMail("b@gmail.com");
            setPassword("password");
            setSurname("Apellido2");
            setCitizenship("Argentina");
            setBirthDate(LocalDate.of(1990, 2, 2));
            setNumDoc("23456789");
            setDocType(EnumTipoDocumento.CI);
        }};
        userController.registerCustomer(customer2, null);

        BaseCustomerDTO customer3 = new BaseCustomerDTO() {{
            setNickname("customer3");
            setName("Customer 3");
            setMail("c@gmail.com");
            setPassword("password");
            setSurname("Apellido3");
            setCitizenship("Argentina");
            setBirthDate(LocalDate.of(1990, 3, 3));
            setNumDoc("34567890");
            setDocType(EnumTipoDocumento.CI);
        }};
        userController.registerCustomer(customer3, null);


        //Crear categoria economica
        categoryController.createCategory(new CategoryDTO("Económica"));
        categoryController.createCategory(new CategoryDTO("Business"));

        //Creo una ciudad
        BaseCityDTO cityA = new BaseCityDTO();
        cityA.setName("CiudadTest");
        cityA.setCountry("Uruguay");
        cityA.setLatitude(-34.6037);
        cityA.setLongitude(-58.3816);
        cityController.createCity(cityA);

        // Creo todos los aeropuertos
        BaseAirportDTO airportA = new BaseAirportDTO();
        airportA.setName("Aeropuerto A");
        airportA.setCode("AAA");
        airportController.createAirport(airportA, cityA.getName());

        BaseAirportDTO airportB = new BaseAirportDTO();
        airportB.setName("Aeropuerto B");
        airportB.setCode("BBB");
        airportController.createAirport(airportB, cityA.getName());


        BaseAirportDTO airportC = new BaseAirportDTO();
        airportC.setName("Aeropuerto C");
        airportC.setCode("CCC");
        airportController.createAirport(airportC, cityA.getName());


        //Creo una ruta de vuelo para cada aerolinea
        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("Ruta 1");
        baseFlightRouteDTO.setDescription("Descripcion ruta 1");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(100.0);
        baseFlightRouteDTO.setPriceBusinessClass(200.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(50.0);
        flightRouteController.createFlightRoute(baseFlightRouteDTO, "AAA", "BBB", "LAT1", List.of("Económica"), null);

        BaseFlightRouteDTO baseFlightRouteDTO2 = new BaseFlightRouteDTO();
        baseFlightRouteDTO2.setName("Ruta 2");
        baseFlightRouteDTO2.setDescription("Descripcion ruta 2");
        baseFlightRouteDTO2.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO2.setPriceTouristClass(150.0);
        baseFlightRouteDTO2.setPriceBusinessClass(300.0);
        baseFlightRouteDTO2.setPriceExtraUnitBaggage(75.0);
        flightRouteController.createFlightRoute(baseFlightRouteDTO2, "BBB", "CCC", "LAT2", List.of("Económica", "Business"), null);

        BaseFlightDTO baseFlightDTO = new BaseFlightDTO();
        baseFlightDTO.setName("LAT1-1 001");
        baseFlightDTO.setCreatedAt(LocalDateTime.now());
        baseFlightDTO.setDepartureTime(LocalDateTime.now().plusDays(1));
        baseFlightDTO.setDuration(120L);
        baseFlightDTO.setMaxEconomySeats(100);
        baseFlightDTO.setMaxBusinessSeats(20);
        flightController.createFlight(baseFlightDTO, "LAT1", "Ruta 1", null);

        BaseFlightDTO baseFlightDTO2 = new BaseFlightDTO();
        baseFlightDTO2.setName("LAT2-2 001");
        baseFlightDTO2.setCreatedAt(LocalDateTime.now());
        baseFlightDTO2.setDepartureTime(LocalDateTime.now().plusDays(2));
        baseFlightDTO2.setDuration(130L);
        baseFlightDTO2.setMaxEconomySeats(100);
        baseFlightDTO2.setMaxBusinessSeats(20);
        flightController.createFlight(baseFlightDTO2, "LAT2", "Ruta 2", null);
    }

    @Test
    @DisplayName("CU-01: Reservar un vuelo")
    void bookExistingFlight() {
        // Listar Aereolienas
        List<BaseAirlineDTO> listAirlines = userController.getAllAirlinesSimpleDetails();
        assertEquals(2, listAirlines.size());

        // Selecciono la airline LAT1
        BaseAirlineDTO airlineSelected = listAirlines.stream().filter(a -> a.getNickname().equals("LAT1")).findFirst().orElse(null);
        assertNotNull(airlineSelected);
        assertEquals("LAT1", airlineSelected.getNickname());

        //Ahora listo las rutas de vuelo de esa aereolinea seleccionada
        List<FlightRouteDTO> flightRoutes = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineSelected.getNickname());
        assertEquals(1, flightRoutes.size());

        // Selecciono la ruta de vuelo
        FlightRouteDTO selectedFlightRoute = flightRoutes.stream().filter(fr -> fr.getName().equals("Ruta 1")).findFirst().orElse(null);

        assertNotNull(selectedFlightRoute);

        assertEquals("AAA", selectedFlightRoute.getOriginAeroCode());
        assertEquals("BBB", selectedFlightRoute.getDestinationAeroCode());


        //Ahora listo lso vuelos que tiene esta ruta, que me deberia de dar 3: E-E1, E1-E2, E2-F
        List<BaseFlightDTO> flights = flightController.getAllFlightsSimpleDetailsByRouteName(selectedFlightRoute.getName());
        assertEquals(1, flights.size());
        BaseFlightDTO vuelo_001 = flights.stream().filter(f -> f.getName().equals("LAT1-1 001")).findFirst().orElse(null);
        assertNotNull(vuelo_001);

        //Seleccionar el Vuelo
        BaseFlightDTO flightSelected = flights.stream().filter(f -> f.getName().equals("LAT1-1 001")).findFirst().orElse(null);
        assertNotNull(flightSelected);
        assertEquals("LAT1-1 001", flightSelected.getName());

        //Conseguir informacion completa del vuelo
        FlightDTO flightDetails = flightController.getFlightDetailsByName(flightSelected.getName());
        assertNotNull(flightDetails);
        assertEquals("LAT1-1 001", flightDetails.getName());
        assertEquals("LAT1", flightDetails.getAirlineNickname());
        assertEquals("Ruta 1", flightDetails.getFlightRouteName());
        assertEquals(100, flightDetails.getMaxEconomySeats());
        assertEquals(20, flightDetails.getMaxBusinessSeats());

        //Listar clientes
        List<BaseCustomerDTO> customers = userController.getAllCustomersSimpleDetails();
        assertEquals(3, customers.size());
        BaseCustomerDTO customer1 = customers.stream().filter(c -> c.getNickname().equals("customer1")).findFirst().orElse(null);
        BaseCustomerDTO customer2 = customers.stream().filter(c -> c.getNickname().equals("customer2")).findFirst().orElse(null);
        BaseCustomerDTO customer3 = customers.stream().filter(c -> c.getNickname().equals("customer3")).findFirst().orElse(null);
        assertNotNull(customer1);
        assertNotNull(customer2);
        assertNotNull(customer3);

        //Seleccionar cliente, elijo al customer3
        BaseCustomerDTO customerSelected = customers.stream().filter(c -> c.getNickname().equals("customer3")).findFirst().orElse(null);
        assertNotNull(customerSelected);
        assertEquals("customer3", customerSelected.getNickname());

        //Si existe reserva, me tira throw. Supongamos que no existe reserva
        //Ingresar Datos Reserva
        // Datos de la reserva
        BaseBookFlightDTO booking = new BaseBookFlightDTO();
        booking.setSeatType(EnumTipoAsiento.TURISTA);
        booking.setCreatedAt(LocalDateTime.now());
        // Si tu DTO tiene fecha, setéala (ajustá al campo real si existe)
        // booking.setBookingDate(LocalDate.now());

        // Construimos los tickets + equipaje para 1 pasajero (customer3)
        var tickets = new java.util.LinkedHashMap<BaseTicketDTO, java.util.List<LuggageDTO>>();

        // Ticket del pasajero
        BaseTicketDTO t1 = new BaseTicketDTO();
        t1.setName("Customer 3");       // nombre pasajero
        t1.setSurname("Apellido3");     // apellido pasajero
        t1.setNumDoc("34567890");       // doc pasajero (coincide con customer3)
        t1.setDocType(EnumTipoDocumento.CI);

        // Equipaje del pasajero
        var luggagesT1 = new java.util.ArrayList<LuggageDTO>();

        BaseBasicLuggageDTO basic = new BaseBasicLuggageDTO();
        basic.setWeight(8.0);
        basic.setCategory(EnumEquipajeBasico.BOLSO);

        BaseExtraLuggageDTO extra = new BaseExtraLuggageDTO();
        extra.setWeight(10.0);
        extra.setCategory(EnumEquipajeExtra.MALETA);

        luggagesT1.add(basic);
        luggagesT1.add(extra);

        // Agregamos el ticket y su equipaje al mapa
        tickets.put(t1, luggagesT1);

        // Finalmente, invocamos el caso de uso
        BaseBookFlightDTO created = bookingController.createBooking(
                booking,
                tickets,
                "customer3",                 // userNickname (cliente que reserva)
                "LAT1-1 001"            // flightName (vuelo elegido)
        );

// Asserts básicos
        assertNotNull(created);

        assertEquals(100.0, created.getTotalPrice());

        // Asserts básicos ya hechos
        assertNotNull(created);
        assertEquals(100.0, created.getTotalPrice());

// ====== Asserts extra fuertes ======
        List<BookFlightDTO> persistedBookings = bookingController.getAllBookFlightsDetails();

// 1) Hay una (y solo una) reserva persistida
        assertEquals(1, persistedBookings.size(), "Debe existir 1 reserva persistida");
        BookFlightDTO bf = persistedBookings.get(0);

// 2) Datos de la reserva
        assertEquals(bf.getCustomerNickname(), "customer3");
        assertTrue(bf.getId() != null && bf.getId() > 0, "La reserva debe tener ID generado");
        assertEquals(100.0, bf.getTotalPrice(), "Precio total debe coincidir");

// 3) La reserva debe tener 1 ticket
        assertNotNull(bf.getTicketIds(), "La reserva debe tener ticketIds");
        assertEquals(1, bf.getTicketIds().size(), "La reserva debe tener 1 ticket");
        Long ticketId = bf.getTicketIds().get(0);
        assertTrue(ticketId != null && ticketId > 0, "El ticket debe tener ID válido");

        TicketDTO persistedTicket = ticketController.getTicketDetailsById(ticketId);

// 4) Datos del ticket
        assertEquals("Customer 3", persistedTicket.getName());
        assertEquals("Apellido3", persistedTicket.getSurname());
        assertEquals("34567890", persistedTicket.getNumDoc());

// 5) El ticket debe estar asociado a un asiento
        assertNotNull(persistedTicket.getSeatNumber(), "El seatNumber no puede ser null");

// 6) Equipajes del ticket (1 básico + 1 extra)
        assertNotNull(persistedTicket.getBasicLuggages(), "La lista de basicLuggages no puede ser null");
        assertNotNull(persistedTicket.getExtraLuggages(), "La lista de extraLuggages no puede ser null");
        assertEquals(1, persistedTicket.getBasicLuggages().size(), "Debe haber 1 equipaje básico");
        assertEquals(1, persistedTicket.getExtraLuggages().size(), "Debe haber 1 equipaje extra");

// 6.a) Validar el equipaje básico
        BasicLuggageDTO bl = persistedTicket.getBasicLuggages().get(0);
        System.out.println(bl.getWeight() + " - " + bl.getCategory() + " - " + bl.getTicketId());
        assertEquals(8.0, bl.getWeight());
        assertEquals(EnumEquipajeBasico.BOLSO, bl.getCategory());
        assertEquals(persistedTicket.getId(), bl.getTicketId());

// 6.b) Validar el equipaje extra
        ExtraLuggageDTO el = persistedTicket.getExtraLuggages().get(0);
        assertEquals(10.0, el.getWeight());
        assertEquals(EnumEquipajeExtra.MALETA, el.getCategory());
        assertEquals(persistedTicket.getId(), el.getTicketId()  );

// 7) (Opcional) Intento negativo: crear reserva sin tickets -> debe tirar
        assertThrows(UnsupportedOperationException.class, () ->
                        bookingController.createBooking(
                                new domain.dtos.bookFlight.BaseBookFlightDTO(), // sin precio ni nada
                                new java.util.LinkedHashMap<>(),               // sin tickets
                                "customer3",
                                "LAT2-2 001"
                        ),
                "No debería permitir reservas sin tickets"
        );

    }
}
