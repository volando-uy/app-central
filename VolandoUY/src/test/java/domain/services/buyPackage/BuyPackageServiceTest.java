package domain.services.buyPackage;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.buyPackage.BaseBuyPackageDTO;
import domain.dtos.buyPackage.BuyPackageDTO;
import domain.dtos.luggage.BasicLuggageDTO;
import domain.dtos.luggage.ExtraLuggageDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
import domain.models.buypackage.BuyPackage;
import domain.models.enums.EnumTipoAsiento;
import domain.models.enums.EnumTipoDocumento;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.luggage.EnumEquipajeBasico;
import domain.models.luggage.EnumEquipajeExtra;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;
import domain.services.booking.BookingService;
import domain.services.booking.IBookingService;
import domain.services.flight.IFlightService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.seat.ISeatService;
import domain.services.ticket.ITicketService;
import domain.services.user.IUserService;
import infra.repository.booking.IBookingRepository;
import infra.repository.buyPackage.IBuyPackageRepository;
import org.junit.jupiter.api.*;
import org.mockito.Mock;
import org.mockito.Mockito;
import shared.constants.ErrorMessages;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class BuyPackageServiceTest {

    @Mock
    private IFlightRoutePackageService flightRoutePackageService = Mockito.mock(IFlightRoutePackageService.class);

    @Mock
    private IUserService userService = Mockito.mock(IUserService.class);

    @Mock
    private IBuyPackageRepository buyPackageRepository = Mockito.mock(IBuyPackageRepository.class);

    private IBuyPackageService buyPackageService;

    @BeforeAll
    void setUp() {
        buyPackageService = new BuyPackageService();
        buyPackageService.setUserService(userService);
        buyPackageService.setFlightRoutePackageService(flightRoutePackageService);
        buyPackageService.setBuyPackageRepository(buyPackageRepository);
    }

    @BeforeEach
    void resetMocks() {
        Mockito.reset(userService);
        Mockito.reset(flightRoutePackageService);
        Mockito.reset(buyPackageRepository);
    }

    @Test
    void createBuyPackageWithValidParameters_shouldCreateTheBuyPackage() {

        // Mock the user with an empty list of bought packages
        Customer customer = new Customer() {{ setBoughtPackages(new ArrayList<>()); }};
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenReturn(customer);

        // Mock the flight route package with a valid period
        FlightRoutePackage flightRoutePackage = new FlightRoutePackage() {{
            setCreationDate(LocalDate.now().minusDays(5));
            setValidityPeriodDays(10);
            setTotalPrice(100.0);
        }};
        Mockito.when(flightRoutePackageService.getFlightRoutePackageByName("flightRoutePackage", true)).thenReturn(flightRoutePackage);

        BaseBuyPackageDTO created = buyPackageService.createBuyPackage(
                "customer",
                "flightRoutePackage"
        );
        assertNotNull(created);
    }

    @Test
    void createBuyPackageAlreadyBought_shouldThrowError() {

        // Mock the flight route package with a valid period
        FlightRoutePackage flightRoutePackage = new FlightRoutePackage() {{
            setName("flightRoutePackage");
            setCreationDate(LocalDate.now().minusDays(5));
            setValidityPeriodDays(10);
            setTotalPrice(100.0);
        }};

        // Mock the user with a bought package that includes the flight route package
        Customer customer = new Customer() {{ setBoughtPackages(new ArrayList<>() {{ add(new BuyPackage() {{ setFlightRoutePackage(flightRoutePackage); }}); }}); }};
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenReturn(customer);


        Mockito.when(flightRoutePackageService.getFlightRoutePackageByName("flightRoutePackage", true)).thenReturn(flightRoutePackage);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            buyPackageService.createBuyPackage(
                    "customer",
                    "flightRoutePackage"
            );
        });

        assertTrue(ex.getMessage().contains("ya compró"));
    }

    @Test
    void createBuyPackageWithExpiredPackage_shouldThrowError() {

        // Mock the user with an empty list of bought packages
        Customer customer = new Customer() {{ setBoughtPackages(new ArrayList<>()); }};
        Mockito.when(userService.getCustomerByNickname("customer", true)).thenReturn(customer);

        // Mock the flight route package with an expired validity
        FlightRoutePackage flightRoutePackage = new FlightRoutePackage() {{
            setCreationDate(LocalDate.now().minusDays(5));
            setValidityPeriodDays(2);
            setTotalPrice(100.0);
        }};
        Mockito.when(flightRoutePackageService.getFlightRoutePackageByName("flightRoutePackage", true)).thenReturn(flightRoutePackage);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            buyPackageService.createBuyPackage(
                    "customer",
                    "flightRoutePackage"
            );
        });

        assertTrue(ex.getMessage().contains("ya expiró"));
    }

    @Test
    void getBuyPackageDetailsByIdWithFull_shouldReturnTheBuyPackage() {

        BuyPackage buyPackage = new BuyPackage() {{
            setId(1L);
            setCreatedAt(LocalDateTime.now());
            setTotalPrice(200.0);
            setBookFlights(new ArrayList<>());
        }};

        Mockito.when(buyPackageRepository.getFullBuyPackageById(1L)).thenReturn(buyPackage);

        BuyPackageDTO returned = buyPackageService.getBuyPackageDetailsById(1L, true);

        assertEquals(buyPackage.getId(), returned.getId());
        assertEquals(200.0, returned.getTotalPrice(), 0.01);
    }

    @Test
    void getBuyPackageDetailsByIdWithoutFull_shouldReturnTheBuyPackage() {

        BuyPackage buyPackage = new BuyPackage() {{
            setId(1L);
            setCreatedAt(LocalDateTime.now());
            setTotalPrice(200.0);
        }};

        Mockito.when(buyPackageRepository.getBuyPackageById(1L)).thenReturn(buyPackage);

        BuyPackageDTO returned = buyPackageService.getBuyPackageDetailsById(1L, false);

        assertEquals(buyPackage.getId(), returned.getId());
        assertEquals(200.0, returned.getTotalPrice(), 0.01);
    }

    @Test
    void getBuyPackageDetailsByIdInexistentPackage_shouldThrowError() {

        Mockito.when(buyPackageRepository.getBuyPackageById(1L)).thenReturn(null);

        assertThrows(IllegalArgumentException.class, () -> {
            buyPackageService.getBuyPackageDetailsById(
                    1L,
                    false
            );
        });

    }

}
