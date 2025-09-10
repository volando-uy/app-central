package casosdeuso;

import controllers.booking.IBookingController;
import controllers.category.ICategoryController;
import controllers.city.ICityController;
import controllers.flight.IFlightController;
import controllers.flightRoute.IFlightRouteController;
import controllers.ticket.ITicketController;
import controllers.user.IUserController;
import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.category.CategoryDTO;
import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.dtos.user.AirlineDTO;
import domain.dtos.user.BaseAirlineDTO;
import domain.dtos.user.BaseCustomerDTO;
import domain.models.bookflight.BookFlight;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.enums.EnumTipoDocumento;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.EnumCategoria;
import domain.models.luggage.EnumEquipajeBasico;
import domain.models.luggage.ExtraLuggage;
import domain.models.ticket.Ticket;
import factory.ControllerFactory;
import infra.repository.booking.BookingRepository;
import infra.repository.city.CityRepository;
import jdk.swing.interop.SwingInterOpUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;

public class BookFlightTest {

    IUserController userController;
    IFlightRouteController flightRouteController;
    ICategoryController categoryController;
    ICityController cityController;
    IFlightController flightController;
    IBookingController bookingController;
    ITicketController ticketController;
    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        userController = ControllerFactory.getUserController();
        flightRouteController = ControllerFactory.getFlightRouteController();
        categoryController = ControllerFactory.getCategoryController();
        cityController = ControllerFactory.getCityController();
        flightController = ControllerFactory.getFlightController();
        bookingController = ControllerFactory.getBookingController();
        ticketController = ControllerFactory.getTicketController();
        //Creo 2 aereolienas
        BaseAirlineDTO airlineDTO = new BaseAirlineDTO();
        airlineDTO.setNickname("LAT123");
        airlineDTO.setName("LATAM");
        airlineDTO.setMail("latam@gmail.com");
        airlineDTO.setDescription("LATAMfedafewafewafewa");
        airlineDTO.setWeb("https://www.google.com");
        userController.registerAirline(airlineDTO);
        //Crear aereolinea LAT123
        BaseAirlineDTO airlineDTO2 = new BaseAirlineDTO();
        airlineDTO2.setNickname("LAT999");
        airlineDTO2.setName("LATAM2");
        airlineDTO2.setMail("latam2@gmail.com");
        airlineDTO2.setDescription("LATAMfedafewafewafewa");
        airlineDTO2.setWeb("https://www.google.com");
        userController.registerAirline(airlineDTO2);

        //Crear 3 customers
        /**
         *             String nickname,
         *             String name,
         *             String mail,
         *             String surname,
         *             String citizenship,
         *             LocalDate birthDate,
         *             String numDoc,
         *             EnumTipoDocumento docType
         */
        BaseCustomerDTO customer1 = new BaseCustomerDTO();
        customer1.setNickname("customer1");
        customer1.setName("Customer 1");
        customer1.setMail("a@gmail.com");
        customer1.setSurname("Apellido1");
        customer1.setCitizenship("Argentina");
        customer1.setBirthDate(LocalDate.of(1990, 1, 1));
        customer1.setNumDoc("12345678");
        customer1.setDocType(EnumTipoDocumento.CI);
        userController.registerCustomer(customer1);
        BaseCustomerDTO customer2 = new BaseCustomerDTO();
        customer2.setNickname("customer2");
        customer2.setName("Customer 2");
        customer2.setMail("b@gmail.com");
        customer2.setSurname("Apellido2");
        customer2.setCitizenship("Argentina");
        customer2.setBirthDate(LocalDate.of(1990, 2, 2));
        customer2.setNumDoc("23456789");
        customer2.setDocType(EnumTipoDocumento.CI);
        userController.registerCustomer(customer2);
        BaseCustomerDTO customer3 = new BaseCustomerDTO();
        customer3.setNickname("customer3");
        customer3.setName("Customer 3");
        customer3.setMail("c@gmail.com");
        customer3.setSurname("Apellido3");
        customer3.setCitizenship("Argentina");
        customer3.setBirthDate(LocalDate.of(1990, 3, 3));
        customer3.setNumDoc("34567890");
        customer3.setDocType(EnumTipoDocumento.CI);
        userController.registerCustomer(customer3);


        //Crear categoria economica
        categoryController.createCategory(new CategoryDTO("Económica"));
        categoryController.createCategory(new CategoryDTO("Business"));
        //Crear ciudad A,B

        BaseCityDTO cityA = new BaseCityDTO();
        cityA.setName("Ciudad A");
        cityA.setCountry("País A");
        cityA.setLatitude(-34.6037);
        cityA.setLongitude(-58.3816);
        cityController.createCity(cityA);

        BaseCityDTO cityB = new BaseCityDTO();
        cityB.setName("Ciudad B");
        cityB.setCountry("País B");
        cityB.setLatitude(-34.6037);
        cityB.setLongitude(-58.3816);
        cityController.createCity(cityB);

        BaseCityDTO cityC = new BaseCityDTO();
        cityC.setName("Ciudad C");
        cityC.setCountry("País C");
        cityC.setLatitude(-34.6037);
        cityC.setLongitude(-58.3816);
        cityController.createCity(cityC);

        //Ahora creo 3 ciudades mas pero para la aereolinea LAT999, en vez que sea A,B,C van a ser D,E,F
        BaseCityDTO cityD = new BaseCityDTO();
        cityD.setName("Ciudad D");
        cityD.setCountry("País D");
        cityD.setLatitude(-34.6037);
        cityD.setLongitude(-58.3816);
        cityController.createCity(cityD);
        BaseCityDTO cityE = new BaseCityDTO();
        cityE.setName("Ciudad E");
        cityE.setCountry("País E");
        cityE.setLatitude(-34.6037);
        cityE.setLongitude(-58.3816);
        cityController.createCity(cityE);
        BaseCityDTO cityF = new BaseCityDTO();
        cityF.setName("Ciudad F");
        cityF.setCountry("País F");
        cityF.setLatitude(-34.6037);
        cityF.setLongitude(-58.3816);
        cityController.createCity(cityF);


        //Creo 3 rutas de vuelo para la aereolinea LAT999

        BaseFlightRouteDTO baseFlightRouteDTO = new BaseFlightRouteDTO();
        baseFlightRouteDTO.setName("Ruta 1");
        baseFlightRouteDTO.setDescription("Descripcion ruta 1");
        baseFlightRouteDTO.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO.setPriceTouristClass(100.0);
        baseFlightRouteDTO.setPriceBusinessClass(200.0);
        baseFlightRouteDTO.setPriceExtraUnitBaggage(50.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO, "Ciudad A", "Ciudad B", "LAT123", List.of("Económica"));

        //Creo 3 rutas de vuelo para la aereolinea LAT123
        BaseFlightRouteDTO baseFlightRouteDTO2 = new BaseFlightRouteDTO();
        baseFlightRouteDTO2.setName("Ruta 2");
        baseFlightRouteDTO2.setDescription("Descripcion ruta 2");
        baseFlightRouteDTO2.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO2.setPriceTouristClass(150.0);
        baseFlightRouteDTO2.setPriceBusinessClass(300.0);
        baseFlightRouteDTO2.setPriceExtraUnitBaggage(75.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO2, "Ciudad B", "Ciudad C", "LAT123", List.of("Económica", "Business"));

        BaseFlightRouteDTO baseFlightRouteDTO3 = new BaseFlightRouteDTO();
        baseFlightRouteDTO3.setName("Ruta 3");
        baseFlightRouteDTO3.setDescription("Descripcion ruta 3");
        baseFlightRouteDTO3.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO3.setPriceTouristClass(200.0);
        baseFlightRouteDTO3.setPriceBusinessClass(400.0);
        baseFlightRouteDTO3.setPriceExtraUnitBaggage(100.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO3, "Ciudad A", "Ciudad C", "LAT123", List.of("Business"));

        //Ahora creo 3 mas pero para la aereolinea LAT999 y van a ser D,E,F
        BaseFlightRouteDTO baseFlightRouteDTO4 = new BaseFlightRouteDTO();
        baseFlightRouteDTO4.setName("Ruta 4");
        baseFlightRouteDTO4.setDescription("Descripcion ruta 4");
        baseFlightRouteDTO4.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO4.setPriceTouristClass(110.0);
        baseFlightRouteDTO4.setPriceBusinessClass(220.0);
        baseFlightRouteDTO4.setPriceExtraUnitBaggage(55.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO4, "Ciudad D", "Ciudad E", "LAT999", List.of("Económica"));


        BaseFlightRouteDTO baseFlightRouteDTO1 = new BaseFlightRouteDTO();
        baseFlightRouteDTO1.setName("Ruta 5");
        baseFlightRouteDTO1.setDescription("Descripcion ruta 5");
        baseFlightRouteDTO1.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO1.setPriceTouristClass(160.0);
        baseFlightRouteDTO1.setPriceBusinessClass(320.0);
        baseFlightRouteDTO1.setPriceExtraUnitBaggage(80.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO1, "Ciudad E", "Ciudad F", "LAT999", List.of("Económica", "Business"));

        //Creo vuelos para la ruta 5, de ciudad E a ciudad E1, de E1 a E2 y de E2 a F

        BaseFlightDTO baseFlightDTO = new BaseFlightDTO();
        baseFlightDTO.setName("LAT999-001 E-E1");
        baseFlightDTO.setCreatedAt(LocalDateTime.now());
        baseFlightDTO.setDepartureTime(LocalDateTime.now().plusDays(1));
        baseFlightDTO.setDuration(120L);
        baseFlightDTO.setMaxEconomySeats(100);
        baseFlightDTO.setMaxBusinessSeats(20);
        flightController.createFlight(baseFlightDTO, "LAT999", "Ruta 5");

        BaseFlightDTO baseFlightDTO2 = new BaseFlightDTO();
        baseFlightDTO2.setName("LAT999-002 E1-E2");
        baseFlightDTO2.setCreatedAt(LocalDateTime.now());
        baseFlightDTO2.setDepartureTime(LocalDateTime.now().plusDays(2));
        baseFlightDTO2.setDuration(130L);
        baseFlightDTO2.setMaxEconomySeats(100);
        baseFlightDTO2.setMaxBusinessSeats(20);
        flightController.createFlight(baseFlightDTO2, "LAT999", "Ruta 5");

        BaseFlightDTO baseFlightDTO3 = new BaseFlightDTO();
        baseFlightDTO3.setName("LAT999-003 E2-F");
        baseFlightDTO3.setCreatedAt(LocalDateTime.now());
        baseFlightDTO3.setDepartureTime(LocalDateTime.now().plusDays(3));
        baseFlightDTO3.setDuration(140L);
        baseFlightDTO3.setMaxEconomySeats(100);
        baseFlightDTO3.setMaxBusinessSeats(20);
        flightController.createFlight(baseFlightDTO3, "LAT999", "Ruta 5");


        BaseFlightRouteDTO baseFlightRouteDTO6 = new BaseFlightRouteDTO();
        baseFlightRouteDTO6.setName("Ruta 6");
        baseFlightRouteDTO6.setDescription("Descripcion ruta 6");
        baseFlightRouteDTO6.setCreatedAt(LocalDate.now());
        baseFlightRouteDTO6.setPriceTouristClass(210.0);
        baseFlightRouteDTO6.setPriceBusinessClass(420.0);
        baseFlightRouteDTO6.setPriceExtraUnitBaggage(105.0);

        flightRouteController.createFlightRoute(baseFlightRouteDTO6, "Ciudad D", "Ciudad F", "LAT999", List.of("Business"));
    }

    @Test
    @DisplayName("CU-01: Reservar un vuelo")
    void bookExistingFlight() {
        //Lister Aereolienas
        List<BaseAirlineDTO> listAirlines = userController.getAllAirlinesSimpleDetails();
        assertEquals(2, listAirlines.size());
        //Sedlecciono al airline LAT999
        BaseAirlineDTO airlineSelected = listAirlines.stream().filter(a -> a.getNickname().equals("LAT999")).findFirst().orElse(null);
        assertNotNull(airlineSelected);
        assertEquals("LAT999", airlineSelected.getNickname());

        //Ahora listo las rutas de vuelo de esa aereolinea seleccionada
        List<FlightRouteDTO> flightRoutes = flightRouteController.getAllFlightRoutesDetailsByAirlineNickname(airlineSelected.getNickname());
        assertEquals(3, flightRoutes.size());

        FlightRouteDTO ruta4 = flightRoutes.stream().filter(fr -> fr.getName().equals("Ruta 4")).findFirst().orElse(null);
        FlightRouteDTO ruta5 = flightRoutes.stream().filter(fr -> fr.getName().equals("Ruta 5")).findFirst().orElse(null);
        FlightRouteDTO ruta6 = flightRoutes.stream().filter(fr -> fr.getName().equals("Ruta 6")).findFirst().orElse(null);

        assertNotNull(ruta4);
        assertNotNull(ruta5);
        assertNotNull(ruta6);

        assertEquals("Ciudad E", ruta4.getDestinationCityName());
        assertEquals("Ciudad F", ruta5.getDestinationCityName());
        assertEquals("Ciudad F", ruta6.getDestinationCityName());


        //Selecciono la ruta 5 que es de E a F
        FlightRouteDTO flightRouteSelected = flightRoutes.stream().filter(fr -> fr.getName().equals("Ruta 5")).findFirst().orElse(null);
        assertNotNull(flightRouteSelected);
        assertEquals("Ruta 5", flightRouteSelected.getName());

        //Ahora listo lso vuelos que tiene esta ruta, que me deberia de dar 3: E-E1, E1-E2, E2-F
        List<BaseFlightDTO> flights = flightController.getAllFlightsSimpleDetailsByRouteName(flightRouteSelected.getName());
        assertEquals(3, flights.size());
        BaseFlightDTO vueloE_E1 = flights.stream().filter(f -> f.getName().equals("LAT999-001 E-E1")).findFirst().orElse(null);
        BaseFlightDTO vueloE1_E2 = flights.stream().filter(f -> f.getName().equals("LAT999-002 E1-E2")).findFirst().orElse(null);
        BaseFlightDTO vueloE2_F = flights.stream().filter(f -> f.getName().equals("LAT999-003 E2-F")).findFirst().orElse(null);
        assertNotNull(vueloE_E1);
        assertNotNull(vueloE1_E2);
        assertNotNull(vueloE2_F);

        //Seleccionar el Vuelo
        BaseFlightDTO flightSelected = flights.stream().filter(f -> f.getName().equals("LAT999-001 E-E1")).findFirst().orElse(null);
        assertNotNull(flightSelected);
        assertEquals("LAT999-001 E-E1", flightSelected.getName());

        //Conseguir informacion completa del vuelo
        FlightDTO flightDetails = flightController.getFlightDetailsByName(flightSelected.getName());
        assertNotNull(flightDetails);
        assertEquals("LAT999-001 E-E1", flightDetails.getName());
        assertEquals("LAT999", flightDetails.getAirlineNickname());
        assertEquals("Ruta 5", flightDetails.getFlightRouteName());
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
        booking.setTotalPrice(100.0);
        // Si tu DTO tiene fecha, setéala (ajustá al campo real si existe)
        // booking.setBookingDate(LocalDate.now());

        // Construimos los tickets + equipaje para 1 pasajero (customer3)
        var tickets = new java.util.LinkedHashMap<BaseTicketDTO, java.util.List<LuggageDTO>>();

        // Ticket del pasajero
        BaseTicketDTO t1 = new BaseTicketDTO();
        t1.setName("Customer 3");       // nombre pasajero
        t1.setSurname("Apellido3");     // apellido pasajero
        t1.setNumDoc("34567890");       // doc pasajero (coincide con customer3)

        // Equipaje del pasajero
        var luggagesT1 = new java.util.ArrayList<LuggageDTO>();

        // ⚠️ Asegurate que tus DTOs de equipaje tengan getters/setters (agregá @Getter/@Setter de Lombok si falta)
        BasicLuggageDTO basic = new BasicLuggageDTO();
        basic.setWeight(8.0);
        basic.setCategory(EnumEquipajeBasico.BOLSO); // ajustá a tu enum real

        ExtraLuggageDTO extra = new ExtraLuggageDTO();
        extra.setWeight(10.0);
        extra.setCategory(EnumCategoria.MALETA); // ajustá a tu enum real

        luggagesT1.add(basic);
        luggagesT1.add(extra);

        // Agregamos el ticket y su equipaje al mapa
        tickets.put(t1, luggagesT1);

        // Finalmente, invocamos el caso de uso
        BaseBookFlightDTO created = bookingController.createBooking(
                booking,
                tickets,
                "customer3",                 // userNickname (cliente que reserva)
                "LAT999-001 E-E1"            // flightName (vuelo elegido)
        );

// Asserts básicos
        assertNotNull(created);

        assertEquals(100.0, created.getTotalPrice());

        // Asserts básicos ya hechos
        assertNotNull(created);
        assertEquals(100.0, created.getTotalPrice());

// ====== Asserts extra fuertes ======
        List<BookFlightDTO> persistedBookings = bookingController.findAllBookFlightDetails();

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
        assertEquals(EnumCategoria.MALETA, el.getCategory());
        assertEquals(persistedTicket.getId(), el.getTicketId()  );

// 7) (Opcional) Intento negativo: crear reserva sin tickets -> debe tirar
        assertThrows(UnsupportedOperationException.class, () ->
                        bookingController.createBooking(
                                new domain.dtos.bookFlight.BaseBookFlightDTO(), // sin precio ni nada
                                new java.util.LinkedHashMap<>(),               // sin tickets
                                "customer3",
                                "LAT999-001 E-E1"
                        ),
                "No debería permitir reservas sin tickets"
        );

    }
}
