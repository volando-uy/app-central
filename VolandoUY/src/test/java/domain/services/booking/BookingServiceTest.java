package domain.services.booking;

import controllers.booking.BookingController;
import controllers.booking.IBookingController;
import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.luggage.EnumEquipajeBasico;
import domain.models.luggage.EnumEquipajeExtra;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;
import domain.services.flight.IFlightService;
import domain.services.seat.ISeatService;
import domain.services.seat.SeatService;
import domain.services.ticket.ITicketService;
import domain.services.user.IUserService;
import infra.repository.booking.IBookingRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingServiceTest {
    @Mock
    private ISeatService seatService = Mockito.mock(ISeatService.class);

    @Mock
    private IFlightService flightService = Mockito.mock(IFlightService.class);

    @Mock
    private ITicketService ticketService = Mockito.mock(ITicketService.class);

    @Mock
    private IUserService userService = Mockito.mock(IUserService.class);

    @Mock
    private IBookingRepository bookingRepository = Mockito.mock(IBookingRepository.class);


    private IBookingService bookingService;

    @BeforeAll
    void setUp() {
        bookingService = new BookingService();
        bookingService.setSeatService(seatService);
        bookingService.setFlightService(flightService);
        bookingService.setUserService(userService);
        bookingService.setTicketService(ticketService);
        bookingService.setBookingRepository(bookingRepository);
    }

    @BeforeEach
    void resetMocks() {
        Mockito.reset(flightService);
        Mockito.reset(userService);
    }

    @Test
    void createBookingWithoutTickets_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingService.createBooking(
                    dto,
                    null,
                    "customer",
                    "flight"
            );
        });

        assertTrue(ex.getMessage().contains("No se pueden crear reservas sin tickets"));
    }

    @Test
    void createBookingInAFlightWithoutSeats_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(1L);
        dto.setTotalPrice(100.0);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setSeatType(EnumTipoAsiento.TURISTA);

        // Creates one ticket with one basic luggage
        Map<BaseTicketDTO, List<LuggageDTO>> tickets = new HashMap<>();
        tickets.put(new BaseTicketDTO(1L, "testName", "testSurname", "12345678", EnumTipoDocumento.CI), new ArrayList<>()  {{ add(new BasicLuggageDTO(10.0, 1L, EnumEquipajeBasico.BOLSO, 1L)); }});

        // Mock the flight and the customer
        Mockito.when(flightService.getFlightByName("flight", true)).thenReturn(new Flight() {{ setFlightRoute(new FlightRoute());}});
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenReturn(new Customer());

        // Mock the seat service
        Mockito.when(seatService.getLimitedAvailableSeatsByFlightNameAndSeatType("flight", 1, EnumTipoAsiento.TURISTA)).thenReturn(new ArrayList<>());

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingService.createBooking(
                    dto,
                    tickets,
                    "customer",
                    "flight"
            );
        });

        assertTrue(ex.getMessage().contains("No hay asientos disponibles"));
    }


    @Test
    @DisplayName("GIVEN valids parameters WHEN creating a booking in a flight with less seats than the numbers of tickets THEN throw error")
    void createBookingInAFlightWithInsufficientSeats_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(1L);
        dto.setTotalPrice(100.0);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setSeatType(EnumTipoAsiento.TURISTA);

        // Creates two ticket with one basic luggage
        Map<BaseTicketDTO, List<LuggageDTO>> tickets = new HashMap<>();
        tickets.put(new BaseTicketDTO(1L, "testName", "testSurname", "12345678", EnumTipoDocumento.CI), new ArrayList<>()  {{ add(new BasicLuggageDTO(10.0, 1L, EnumEquipajeBasico.BOLSO, 1L)); }});
        tickets.put(new BaseTicketDTO(2L, "testName", "testSurname", "12345678", EnumTipoDocumento.CI), new ArrayList<>()  {{ add(new BasicLuggageDTO(10.0, 2L, EnumEquipajeBasico.BOLSO, 2L)); }});

        // Mock the flight and the customer
        Mockito.when(flightService.getFlightByName("flight1", true)).thenReturn(new Flight() {{ setFlightRoute(new FlightRoute());}});
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenReturn(new Customer());

        // Mock the seat service
        // Return only 1 seat for 2 tickets
        Mockito.when(seatService.getLimitedAvailableSeatsByFlightNameAndSeatType("flight1", 2, EnumTipoAsiento.TURISTA)).thenReturn(new ArrayList<>() {{ add(new Seat()); }});

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingService.createBooking(
                    dto,
                    tickets,
                    "customer",
                    "flight1"
            );
        });

        assertTrue(ex.getMessage().contains("suficientes asientos"));
    }

    @Test
    void createBookingWithInexistentFlight_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(1L);
        dto.setTotalPrice(100.0);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setSeatType(EnumTipoAsiento.TURISTA);

        // Mock the ticket with one basic luggage
        Map<BaseTicketDTO, List<LuggageDTO>> tickets = new HashMap<>();
        tickets.put(new BaseTicketDTO(1L, "testName", "testSurname", "12345678", EnumTipoDocumento.CI), new ArrayList<>()  {{ add(new BasicLuggageDTO(10.0, 1L, EnumEquipajeBasico.BOLSO, 1L)); }});

        // Mock the customer
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenReturn(new Customer() {{ setNickname("customer"); }});

        // Mock the flightService to fail
        Mockito.when(flightService.getFlightByName("flight", true)).thenThrow(new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_NOT_FOUND, "flight")));


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(
                    dto,
                    tickets,
                    "customer",
                    "flight"
            );
        });

        System.out.println(ex.getMessage());

        assertTrue(ex.getMessage().equals(String.format(ErrorMessages.ERR_FLIGHT_NOT_FOUND, "flight")));
    }

    @Test
    void createBookingWithInexistentCustomer_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(1L);
        dto.setTotalPrice(100.0);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setSeatType(EnumTipoAsiento.TURISTA);

        // Mock the ticket with one basic luggage
        Map<BaseTicketDTO, List<LuggageDTO>> tickets = new HashMap<>();
        tickets.put(new BaseTicketDTO(1L, "testName", "testSurname", "12345678", EnumTipoDocumento.CI), new ArrayList<>()  {{ add(new BasicLuggageDTO(10.0, 1L, EnumEquipajeBasico.BOLSO, 1L)); }});

        // Mock the flightService
        Mockito.when(flightService.getFlightByName("flight", true)).thenReturn(new Flight() {{ setName("flight"); }});

        // Mock the customer to fail
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenThrow(new IllegalArgumentException(String.format(ErrorMessages.ERR_USER_NOT_FOUND, "customer")));


        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            bookingService.createBooking(
                    dto,
                    tickets,
                    "customer",
                    "flight"
            );
        });

        assertTrue(ex.getMessage().equals(String.format(ErrorMessages.ERR_USER_NOT_FOUND, "customer")));
    }

    @Test
    void createBookingWithValidDto_shouldCreateTheBooking() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(1L);
        dto.setTotalPrice(0.0);
        dto.setCreatedAt(LocalDateTime.now());
        dto.setSeatType(EnumTipoAsiento.TURISTA);

        // Mock the ticket with one basic luggage
        BaseTicketDTO newTicket = new BaseTicketDTO(1L, "testName", "testSurname", "12345678", EnumTipoDocumento.CI);
        Map<BaseTicketDTO, List<LuggageDTO>> tickets = new HashMap<>();
        tickets.put(newTicket, new ArrayList<>()  {{ add(new BasicLuggageDTO(10.0, 1L, EnumEquipajeBasico.BOLSO, 1L)); add(new ExtraLuggageDTO(10.0, 2L, EnumEquipajeExtra.MALETA, 1L)); }});

        // Mock the flight
        Mockito.when(flightService.getFlightByName("flight", true)).thenReturn(new Flight() {{ setName("flight"); setFlightRoute(new FlightRoute() {{ setPriceExtraUnitBaggage(300.0); setPriceTouristClass(100.0); }}); }});

        // Mock the customer
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenReturn(new Customer() {{ setNickname("customer"); }});

        // Mock the seats
        Mockito.when(seatService.getLimitedAvailableSeatsByFlightNameAndSeatType("flight", 1, EnumTipoAsiento.TURISTA)).thenReturn(new ArrayList<>() {{ add(new Seat()); }});

        // Mock the ticket
        Mockito.when(ticketService.createTicketWithoutPersistence(newTicket)).thenReturn(new Ticket());


        BaseBookFlightDTO created = bookingService.createBooking(
                dto,
                tickets,
                "customer",
                "flight"
        );

        assertTrue(created.getId() == 1L);
        assertTrue(created.getTotalPrice() == 400.0); // 100 base + 300 extra luggage
        assertTrue(created.getSeatType() == EnumTipoAsiento.TURISTA);
    }

    @Test
    void getAllBookingDetailsWithFull_shouldReturnAllBookings() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);
        test1.setCustomer(new Customer() {{ setNickname("customer1"); }});

        BookFlight test2 = new BookFlight();
        test2.setId(2L);
        test2.setTotalPrice(0.0);
        test2.setCreatedAt(LocalDateTime.now());
        test2.setSeatType(EnumTipoAsiento.TURISTA);
        test2.setCustomer(new Customer() {{ setNickname("customer2"); }});

        // Mock the repository to return two bookings
        Mockito.when(bookingRepository.findFullAll()).thenReturn(new ArrayList<>() {{ add(test1); add(test2); }});


        List<BookFlightDTO> bookflights = bookingService.getAllBookFlightsDetails(true);

        assertTrue(bookflights.size() == 2);
        assertTrue(bookflights.get(0).getCustomerNickname().equals("customer1"));
        assertTrue(bookflights.get(1).getCustomerNickname().equals("customer2"));
    }

    @Test
    void getAllBookingDetailsWithoutFull_shouldReturnAllBookings() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlight test2 = new BookFlight();
        test2.setId(2L);
        test2.setTotalPrice(0.0);
        test2.setCreatedAt(LocalDateTime.now());
        test2.setSeatType(EnumTipoAsiento.TURISTA);

        // Mock the repository to return two bookings
        Mockito.when(bookingRepository.findAll()).thenReturn(new ArrayList<>() {{ add(test1); add(test2); }});


        List<BookFlightDTO> bookflights = bookingService.getAllBookFlightsDetails(false);

        assertTrue(bookflights.size() == 2);
        assertTrue(bookflights.get(0).getId() == 1L);
        assertTrue(bookflights.get(1).getId() == 2L);
    }

    @Test
    void getAllBookingDetailsWithoutBookings_shouldReturnEmptyList() {

        // Mock the repository to return 0 bookings
        Mockito.when(bookingRepository.findAll()).thenReturn(new ArrayList<>());

        List<BookFlightDTO> bookflights = bookingService.getAllBookFlightsDetails(false);

        assertTrue(bookflights.isEmpty());
    }


    @Test
    void getAllBookingDetailsByCustomerNicknameWithoutBookings_shouldReturnEmptyList() {

        // Mock the repository to return 0 bookings
        Mockito.when(bookingRepository.findByCustomerNickname("customer")).thenReturn(new ArrayList<>());

        List<BookFlightDTO> bookflights = bookingService.getAllBookFlightsDetailsByCustomerNickname("customer", false);

        assertTrue(bookflights.isEmpty());
    }

    @Test
    void getAllBookingDetailsByCustomerNicknameWithoutFull_shouldReturnAllBookings() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlight test2 = new BookFlight();
        test2.setId(2L);
        test2.setTotalPrice(0.0);
        test2.setCreatedAt(LocalDateTime.now());
        test2.setSeatType(EnumTipoAsiento.TURISTA);

        // Mock the repository to return two bookings
        Mockito.when(bookingRepository.findByCustomerNickname("customer")).thenReturn(new ArrayList<>() {{ add(test1); add(test2); }});


        List<BookFlightDTO> bookflights = bookingService.getAllBookFlightsDetailsByCustomerNickname("customer", false);

        assertTrue(bookflights.size() == 2);
        assertTrue(bookflights.get(0).getId() == 1L);
        assertTrue(bookflights.get(1).getId() == 2L);
    }

    @Test
    void getAllBookingDetailsByCustomerNicknameWithFull_shouldReturnAllBookings() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);
        test1.setCustomer(new Customer() {{setNickname("customer1"); }});

        BookFlight test2 = new BookFlight();
        test2.setId(2L);
        test2.setTotalPrice(0.0);
        test2.setCreatedAt(LocalDateTime.now());
        test2.setSeatType(EnumTipoAsiento.TURISTA);
        test2.setCustomer(new Customer() {{setNickname("customer2"); }});

        // Mock the repository to return two bookings
        Mockito.when(bookingRepository.findFullByCustomerNickname("customer")).thenReturn(new ArrayList<>() {{ add(test1); add(test2); }});


        List<BookFlightDTO> bookflights = bookingService.getAllBookFlightsDetailsByCustomerNickname("customer", true);

        assertTrue(bookflights.size() == 2);
        assertTrue(bookflights.get(0).getCustomerNickname().equals("customer1"));
        assertTrue(bookflights.get(1).getCustomerNickname().equals("customer2"));
    }


    @Test
    void getAllBookingDetailsByFlightNameWithoutBookings_shouldReturnEmptyList() {

        // Mock the repository to return 0 bookings
        Mockito.when(bookingRepository.findByFlightName("flight")).thenReturn(new ArrayList<>());

        List<BookFlightDTO> bookflights = bookingService.getBookFlightsDetailsByFlightName("flight", false);

        assertTrue(bookflights.isEmpty());
    }

    @Test
    void getAllBookingDetailsByFlightNameWithoutFull_shouldReturnAllBookings() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlight test2 = new BookFlight();
        test2.setId(2L);
        test2.setTotalPrice(0.0);
        test2.setCreatedAt(LocalDateTime.now());
        test2.setSeatType(EnumTipoAsiento.TURISTA);

        // Mock the repository to return two bookings
        Mockito.when(bookingRepository.findByFlightName("flight")).thenReturn(new ArrayList<>() {{ add(test1); add(test2); }});


        List<BookFlightDTO> bookflights = bookingService.getBookFlightsDetailsByFlightName("flight", false);

        assertTrue(bookflights.size() == 2);
        assertTrue(bookflights.get(0).getId() == 1L);
        assertTrue(bookflights.get(1).getId() == 2L);
    }

    @Test
    void getAllBookingDetailsByFlightNameWithFull_shouldReturnAllBookings() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);
        test1.setCustomer(new Customer() {{setNickname("customer1"); }});

        BookFlight test2 = new BookFlight();
        test2.setId(2L);
        test2.setTotalPrice(0.0);
        test2.setCreatedAt(LocalDateTime.now());
        test2.setSeatType(EnumTipoAsiento.TURISTA);
        test2.setCustomer(new Customer() {{setNickname("customer2"); }});

        // Mock the repository to return two bookings
        Mockito.when(bookingRepository.findFullByFlightName("flight")).thenReturn(new ArrayList<>() {{ add(test1); add(test2); }});


        List<BookFlightDTO> bookflights = bookingService.getBookFlightsDetailsByFlightName("flight", true);

        assertTrue(bookflights.size() == 2);
        assertTrue(bookflights.get(0).getCustomerNickname().equals("customer1"));
        assertTrue(bookflights.get(1).getCustomerNickname().equals("customer2"));
    }

    @Test
    void getBookFlightDetailsByIdWithoutExistentBookFlight_shouldReturnEmptyList() {

        // Mock the repository to return 0 bookings
        Mockito.when(bookingRepository.findByKey(1L)).thenReturn(null);

        BookFlightDTO bookflight = bookingService.getBookFlightDetailsById(1L, false);

        assertTrue(bookflight == null);
    }

    @Test
    void getBookFlightDetailsByIdWithoutFull_shouldReturnCorrectBookFlight() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);

        // Mock the repository to return the bookflight
        Mockito.when(bookingRepository.findByKey(1L)).thenReturn(test1);


        BookFlightDTO bookflight = bookingService.getBookFlightDetailsById(1L, false);

        assertTrue(bookflight.getId() == 1L);
        assertTrue(bookflight.getTotalPrice() == 0.0);
        assertTrue(bookflight.getSeatType() == EnumTipoAsiento.TURISTA);
    }

    @Test
    void getBookFlightDetailsByIdWithFull_shouldReturnCorrectBookFlight() {
        BookFlight test1 = new BookFlight();
        test1.setId(1L);
        test1.setTotalPrice(0.0);
        test1.setCreatedAt(LocalDateTime.now());
        test1.setSeatType(EnumTipoAsiento.TURISTA);
        test1.setCustomer(new Customer() {{setNickname("customer1"); }});

        // Mock the repository to return the bookflight
        Mockito.when(bookingRepository.getFullBookingById(1L)).thenReturn(test1);


        BookFlightDTO bookflight = bookingService.getBookFlightDetailsById(1L, true);

        assertTrue(bookflight.getId() == 1L);
        assertTrue(bookflight.getTotalPrice() == 0.0);
        assertTrue(bookflight.getSeatType() == EnumTipoAsiento.TURISTA);
        assertTrue(bookflight.getCustomerNickname().equals("customer1"));
    }
}
