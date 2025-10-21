package controllers.booking;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.enums.EnumTipoAsiento;
import domain.services.booking.BookingService;
import domain.services.booking.IBookingService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BookingControllerTest {

    private IBookingController bookingController;

    @Mock
    private IBookingService  bookingService = Mockito.mock(BookingService.class);

    @BeforeAll
    void setUp() {
        bookingController = new BookingController(bookingService);
    }

    @Test
    void createBookingWithoutTickets_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();

        Mockito.when(bookingService.createBooking(dto, new HashMap<>(), "customer", "flight")).thenThrow(new UnsupportedOperationException("No se pueden crear tickets sin reserva"));

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingController.createBooking(
                    dto,
                    new HashMap<>(),
                    "customer",
                    "flight"
            );
        });

        assertTrue(ex.getMessage().contains("No se pueden crear tickets sin reserva"));
    }

    @Test
    void createBookingInAFlightWithoutSeats_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();

        Mockito.when(bookingService.createBooking(dto, new HashMap<>(), "customer", "flight")).thenThrow(new UnsupportedOperationException("No hay asientos disponibles"));

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingController.createBooking(
                    dto,
                    new HashMap<>(),
                    "customer",
                    "flight"
            );
        });

        assertTrue(ex.getMessage().contains("No hay asientos disponibles"));
    }

    @Test
    void createBookingInAFlightWithInsufficientSeats_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();

        Mockito.when(bookingService.createBooking(dto, new HashMap<>(), "customer", "flight")).thenThrow(new UnsupportedOperationException("No hay suficientes asientos disponibles"));

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingController.createBooking(
                    dto,
                    new HashMap<>(),
                    "customer",
                    "flight"
            );
        });

        assertTrue(ex.getMessage().contains("No hay suficientes asientos disponibles"));
    }

    @Test
    void createBookingWithInexistentFlight_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();

        Mockito.when(bookingService.createBooking(dto, new HashMap<>(), "customer", "inexistentFlight")).thenThrow(new UnsupportedOperationException("No existe el vuelo"));

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingController.createBooking(
                    dto,
                    new HashMap<>(),
                    "customer",
                    "inexistentFlight"
            );
        });

        assertTrue(ex.getMessage().contains("No existe el vuelo"));
    }

    @Test
    void createBookingWithInexistentCustomer_shouldThrowError() {
        BaseBookFlightDTO dto = new BaseBookFlightDTO();

        Mockito.when(bookingService.createBooking(dto, new HashMap<>(), "inexistentCustomer", "flight")).thenThrow(new UnsupportedOperationException("No existe el cliente"));

        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            bookingController.createBooking(
                    dto,
                    new HashMap<>(),
                    "inexistentCustomer",
                    "flight"
            );
        });

        assertTrue(ex.getMessage().contains("No existe el cliente"));
    }

    @Test
    void createBookingWithValidDto_shouldCreateTheBooking() {
        LocalDateTime nowTime = LocalDateTime.now();

        BaseBookFlightDTO dto = new BaseBookFlightDTO();
        dto.setId(1L);
        dto.setCreatedAt(nowTime);
        dto.setTotalPrice(100.0);
        dto.setSeatType(EnumTipoAsiento.TURISTA);

        Mockito.when(bookingService.createBooking(dto, new HashMap<>(), "customer", "flight")).thenReturn(dto);

        BaseBookFlightDTO created = bookingController.createBooking(
                dto,
                new HashMap<>(),
                "customer",
                "flight"
        );

        assertTrue(created.getId() == 1L);
        assertTrue(created.getCreatedAt() == nowTime);
        assertTrue(created.getTotalPrice() == 100.0);
        assertTrue(created.getSeatType() == EnumTipoAsiento.TURISTA);
    }

    @Test
    void getAllBookingDetails_shouldReturnAllBookings() {
        LocalDateTime nowTime = LocalDateTime.now();

        BookFlightDTO dto1 = new BookFlightDTO();
        dto1.setId(1L);
        dto1.setCreatedAt(nowTime);
        dto1.setTotalPrice(100.0);
        dto1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlightDTO dto2 = new BookFlightDTO();
        dto2.setId(2L);
        dto2.setCreatedAt(nowTime);
        dto2.setTotalPrice(100.0);
        dto2.setSeatType(EnumTipoAsiento.TURISTA);

        Mockito.when(bookingService.getAllBookFlightsDetails(true)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<BookFlightDTO> returned = bookingController.getAllBookFlightsDetails();

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getId() == 1L);
        assertTrue(returned.get(1).getId() == 2L);
    }


    @Test
    void getAllBookingSimpleDetails_shouldReturnAllBookings() {
        LocalDateTime nowTime = LocalDateTime.now();

        BookFlightDTO dto1 = new BookFlightDTO();
        dto1.setId(1L);
        dto1.setCreatedAt(nowTime);
        dto1.setTotalPrice(100.0);
        dto1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlightDTO dto2 = new BookFlightDTO();
        dto2.setId(2L);
        dto2.setCreatedAt(nowTime);
        dto2.setTotalPrice(100.0);
        dto2.setSeatType(EnumTipoAsiento.TURISTA);

        Mockito.when(bookingService.getAllBookFlightsDetails(false)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<BaseBookFlightDTO> returned = bookingController.getAllBookFlightsSimpleDetails();

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getId() == 1L);
        assertTrue(returned.get(1).getId() == 2L);
    }

    @Test
    void getBookingsDetailsByCustomerName_shouldReturnBookings() {
        LocalDateTime nowTime = LocalDateTime.now();

        BookFlightDTO dto1 = new BookFlightDTO();
        dto1.setId(1L);
        dto1.setCreatedAt(nowTime);
        dto1.setTotalPrice(100.0);
        dto1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlightDTO dto2 = new BookFlightDTO();
        dto2.setId(2L);
        dto2.setCreatedAt(nowTime);
        dto2.setTotalPrice(100.0);
        dto2.setSeatType(EnumTipoAsiento.TURISTA);

        Mockito.when(bookingService.getAllBookFlightsDetailsByCustomerNickname("customer", true)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<BookFlightDTO> returned = bookingController.getBookFlightsDetailsByCustomerNickname("customer");

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getId() == 1L);
        assertTrue(returned.get(1).getId() == 2L);
    }

    @Test
    void getBookingsSimpleDetailsByCustomerName_shouldReturnBookings() {
        LocalDateTime nowTime = LocalDateTime.now();

        BookFlightDTO dto1 = new BookFlightDTO();
        dto1.setId(1L);
        dto1.setCreatedAt(nowTime);
        dto1.setTotalPrice(100.0);
        dto1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlightDTO dto2 = new BookFlightDTO();
        dto2.setId(2L);
        dto2.setCreatedAt(nowTime);
        dto2.setTotalPrice(100.0);
        dto2.setSeatType(EnumTipoAsiento.TURISTA);

        Mockito.when(bookingService.getAllBookFlightsDetailsByCustomerNickname("customer", false)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<BaseBookFlightDTO> returned = bookingController.getBookFlightsSimpleDetailsByCustomerNickname("customer");

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getId() == 1L);
        assertTrue(returned.get(1).getId() == 2L);
    }

    @Test
    void getBookingsDetailsByFlightName_shouldReturnBookings() {
        LocalDateTime nowTime = LocalDateTime.now();

        BookFlightDTO dto1 = new BookFlightDTO();
        dto1.setId(1L);
        dto1.setCreatedAt(nowTime);
        dto1.setTotalPrice(100.0);
        dto1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlightDTO dto2 = new BookFlightDTO();
        dto2.setId(2L);
        dto2.setCreatedAt(nowTime);
        dto2.setTotalPrice(100.0);
        dto2.setSeatType(EnumTipoAsiento.TURISTA);

        Mockito.when(bookingService.getBookFlightsDetailsByFlightName("flight", true)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<BookFlightDTO> returned = bookingController.getBookFlightsDetailsByFlightName("flight");

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getId() == 1L);
        assertTrue(returned.get(1).getId() == 2L);
    }

    @Test
    void getBookingsSimpleDetailsByFlightName_shouldReturnBookings() {
        LocalDateTime nowTime = LocalDateTime.now();

        BookFlightDTO dto1 = new BookFlightDTO();
        dto1.setId(1L);
        dto1.setCreatedAt(nowTime);
        dto1.setTotalPrice(100.0);
        dto1.setSeatType(EnumTipoAsiento.TURISTA);

        BookFlightDTO dto2 = new BookFlightDTO();
        dto2.setId(2L);
        dto2.setCreatedAt(nowTime);
        dto2.setTotalPrice(100.0);
        dto2.setSeatType(EnumTipoAsiento.TURISTA);

        Mockito.when(bookingService.getBookFlightsDetailsByFlightName("flight", false)).thenReturn(new ArrayList<>() {{ add(dto1); add(dto2); }});

        List<BaseBookFlightDTO> returned = bookingController.getBookFlightsSimpleDetailsByFlightName("flight");

        assertTrue(returned.size() == 2);
        assertTrue(returned.get(0).getId() == 1L);
        assertTrue(returned.get(1).getId() == 2L);
    }

    @Test
    void getBookingById_shouldReturnBooking() {
        LocalDateTime nowTime = LocalDateTime.now();

        BookFlightDTO dto1 = new BookFlightDTO();
        dto1.setId(1L);
        dto1.setCreatedAt(nowTime);
        dto1.setTotalPrice(100.0);
        dto1.setSeatType(EnumTipoAsiento.TURISTA);


        Mockito.when(bookingService.getBookFlightDetailsById(1L, true)).thenReturn(dto1);

        BookFlightDTO created = bookingController.getBookFlightDetailsById(1L);

        assertTrue(created.getId() == 1L);
        assertTrue(created.getCreatedAt() == nowTime);
        assertTrue(created.getTotalPrice() == 100.0);
        assertTrue(created.getSeatType() == EnumTipoAsiento.TURISTA);
    }
}
