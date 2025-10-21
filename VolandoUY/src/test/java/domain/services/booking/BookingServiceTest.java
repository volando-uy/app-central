package domain.services.booking;

import controllers.booking.BookingController;
import controllers.booking.IBookingController;
import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
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
        bookingService.setBookingRepository(bookingRepository);
        bookingService.setTicketService(ticketService);
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
//
//    @Test
//    void getAllBookingDetails_shouldReturnAllBookings() {
//        LocalDateTime nowTime = LocalDateTime.now();
//
//        BookFlightDTO dto1 = new BookFlightDTO();
//        dto1.setId(1L);
//        dto1.setCreatedAt(nowTime);
//        dto1.setTotalPrice(100.0);
//        dto1.setSeatType(EnumTipoAsiento.TURISTA);
//
//        BookFlightDTO dto2 = new BookFlightDTO();
//        dto2.setId(2L);
//        dto2.setCreatedAt(nowTime);
//        dto2.setTotalPrice(100.0);
//        dto2.setSeatType(EnumTipoAsiento.TURISTA);
//
//        Mockito.when(bookingService.getAllBookFlightsDetails(true)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});
//
//        List<BookFlightDTO> returned = bookingController.getAllBookFlightsDetails();
//
//        assertTrue(returned.size() == 2);
//        assertTrue(returned.get(0).getId() == 1L);
//        assertTrue(returned.get(1).getId() == 2L);
//    }
//
//
//    @Test
//    void getAllBookingSimpleDetails_shouldReturnAllBookings() {
//        LocalDateTime nowTime = LocalDateTime.now();
//
//        BookFlightDTO dto1 = new BookFlightDTO();
//        dto1.setId(1L);
//        dto1.setCreatedAt(nowTime);
//        dto1.setTotalPrice(100.0);
//        dto1.setSeatType(EnumTipoAsiento.TURISTA);
//
//        BookFlightDTO dto2 = new BookFlightDTO();
//        dto2.setId(2L);
//        dto2.setCreatedAt(nowTime);
//        dto2.setTotalPrice(100.0);
//        dto2.setSeatType(EnumTipoAsiento.TURISTA);
//
//        Mockito.when(bookingService.getAllBookFlightsDetails(false)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});
//
//        List<BaseBookFlightDTO> returned = bookingController.getAllBookFlightsSimpleDetails();
//
//        assertTrue(returned.size() == 2);
//        assertTrue(returned.get(0).getId() == 1L);
//        assertTrue(returned.get(1).getId() == 2L);
//    }
//
//    @Test
//    void getBookingsDetailsByCustomerName_shouldReturnBookings() {
//        LocalDateTime nowTime = LocalDateTime.now();
//
//        BookFlightDTO dto1 = new BookFlightDTO();
//        dto1.setId(1L);
//        dto1.setCreatedAt(nowTime);
//        dto1.setTotalPrice(100.0);
//        dto1.setSeatType(EnumTipoAsiento.TURISTA);
//
//        BookFlightDTO dto2 = new BookFlightDTO();
//        dto2.setId(2L);
//        dto2.setCreatedAt(nowTime);
//        dto2.setTotalPrice(100.0);
//        dto2.setSeatType(EnumTipoAsiento.TURISTA);
//
//        Mockito.when(bookingService.getAllBookFlightsDetailsByCustomerNickname("customer", true)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});
//
//        List<BookFlightDTO> returned = bookingController.getBookFlightsDetailsByCustomerNickname("customer");
//
//        assertTrue(returned.size() == 2);
//        assertTrue(returned.get(0).getId() == 1L);
//        assertTrue(returned.get(1).getId() == 2L);
//    }
//
//    @Test
//    void getBookingsSimpleDetailsByCustomerName_shouldReturnBookings() {
//        LocalDateTime nowTime = LocalDateTime.now();
//
//        BookFlightDTO dto1 = new BookFlightDTO();
//        dto1.setId(1L);
//        dto1.setCreatedAt(nowTime);
//        dto1.setTotalPrice(100.0);
//        dto1.setSeatType(EnumTipoAsiento.TURISTA);
//
//        BookFlightDTO dto2 = new BookFlightDTO();
//        dto2.setId(2L);
//        dto2.setCreatedAt(nowTime);
//        dto2.setTotalPrice(100.0);
//        dto2.setSeatType(EnumTipoAsiento.TURISTA);
//
//        Mockito.when(bookingService.getAllBookFlightsDetailsByCustomerNickname("customer", false)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});
//
//        List<BaseBookFlightDTO> returned = bookingController.getBookFlightsSimpleDetailsByCustomerNickname("customer");
//
//        assertTrue(returned.size() == 2);
//        assertTrue(returned.get(0).getId() == 1L);
//        assertTrue(returned.get(1).getId() == 2L);
//    }
//
//    @Test
//    void getBookingsDetailsByFlightName_shouldReturnBookings() {
//        LocalDateTime nowTime = LocalDateTime.now();
//
//        BookFlightDTO dto1 = new BookFlightDTO();
//        dto1.setId(1L);
//        dto1.setCreatedAt(nowTime);
//        dto1.setTotalPrice(100.0);
//        dto1.setSeatType(EnumTipoAsiento.TURISTA);
//
//        BookFlightDTO dto2 = new BookFlightDTO();
//        dto2.setId(2L);
//        dto2.setCreatedAt(nowTime);
//        dto2.setTotalPrice(100.0);
//        dto2.setSeatType(EnumTipoAsiento.TURISTA);
//
//        Mockito.when(bookingService.getBookFlightsDetailsByFlightName("flight", true)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});
//
//        List<BookFlightDTO> returned = bookingController.getBookFlightsDetailsByFlightName("flight");
//
//        assertTrue(returned.size() == 2);
//        assertTrue(returned.get(0).getId() == 1L);
//        assertTrue(returned.get(1).getId() == 2L);
//    }
//
//    @Test
//    void getBookingsSimpleDetailsByFlightName_shouldReturnBookings() {
//        LocalDateTime nowTime = LocalDateTime.now();
//
//        BookFlightDTO dto1 = new BookFlightDTO();
//        dto1.setId(1L);
//        dto1.setCreatedAt(nowTime);
//        dto1.setTotalPrice(100.0);
//        dto1.setSeatType(EnumTipoAsiento.TURISTA);
//
//        BookFlightDTO dto2 = new BookFlightDTO();
//        dto2.setId(2L);
//        dto2.setCreatedAt(nowTime);
//        dto2.setTotalPrice(100.0);
//        dto2.setSeatType(EnumTipoAsiento.TURISTA);
//
//        Mockito.when(bookingService.getBookFlightsDetailsByFlightName("flight", false)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});
//
//        List<BaseBookFlightDTO> returned = bookingController.getBookFlightsSimpleDetailsByFlightName("flight");
//
//        assertTrue(returned.size() == 2);
//        assertTrue(returned.get(0).getId() == 1L);
//        assertTrue(returned.get(1).getId() == 2L);
//    }
//
//    @Test
//    void getBookingById_shouldReturnBooking() {
//        LocalDateTime nowTime = LocalDateTime.now();
//
//        BookFlightDTO dto1 = new BookFlightDTO();
//        dto1.setId(1L);
//        dto1.setCreatedAt(nowTime);
//        dto1.setTotalPrice(100.0);
//        dto1.setSeatType(EnumTipoAsiento.TURISTA);
//
//
//        Mockito.when(bookingService.getBookFlightDetailsById(1L, true)).thenReturn(dto1);
//
//        BookFlightDTO created = bookingController.getBookFlightDetailsById(1L);
//
//        assertTrue(created.getId() == 1L);
//        assertTrue(created.getCreatedAt() == nowTime);
//        assertTrue(created.getTotalPrice() == 100.0);
//        assertTrue(created.getSeatType() == EnumTipoAsiento.TURISTA);
//    }
}
