package utils;

import domain.dtos.user.*;
import domain.models.bookflight.BookFlight;
import domain.models.buypackage.BuyPackage;
import domain.models.flightroute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;
import domain.services.auth.AuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.utils.CustomModelMapper;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomModelMapperTest {

    private CustomModelMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = new CustomModelMapper();
    }

    @Test
    void mapCustomer_shouldReturnCustomerDTO() {
        Customer customer = new Customer();
        customer.setNickname("cus1");
        customer.setName("Pepe");
        customer.setMail("mail@test.com");

        CustomerDTO dto = mapper.mapCustomer(customer);

        assertNotNull(dto);
        assertEquals("cus1", dto.getNickname());
        assertEquals("Pepe", dto.getName());
        assertEquals("mail@test.com", dto.getMail());
    }

    @Test
    void mapFullCustomer_shouldIncludeBookFlightsAndPackages() {
        Customer customer = new Customer();
        customer.setNickname("cus2");

        BookFlight bf1 = new BookFlight();
        bf1.setId(1L);
        BookFlight bf2 = new BookFlight();
        bf2.setId(2L);
        customer.setBookedFlights(List.of(bf1, bf2));

        BuyPackage bp1 = new BuyPackage();
        bp1.setId(10L);
        customer.setBoughtPackages(List.of(bp1));

        CustomerDTO dto = mapper.mapFullCustomer(customer);

        assertEquals(2, dto.getBookFlightsIds().size());
        assertEquals(1, dto.getBoughtPackagesIds().size());
        assertTrue(dto.getBookFlightsIds().contains(1L));
        assertTrue(dto.getBoughtPackagesIds().contains(10L));
    }

    @Test
    void mapBaseAirline_shouldReturnBaseDTO() {
        Airline airline = new Airline();
        airline.setNickname("air1");
        airline.setName("FlyHigh");

        BaseAirlineDTO dto = mapper.mapBaseAirline(airline);

        assertNotNull(dto);
        assertEquals("air1", dto.getNickname());
        assertEquals("FlyHigh", dto.getName());
    }

    @Test
    void mapFullAirline_shouldIncludeFlightsAndRoutesNames() {
        Airline airline = new Airline();
        airline.setNickname("air2");

        var f1 = new domain.models.flight.Flight();
        f1.setName("Flight 101");
        var f2 = new domain.models.flight.Flight();
        f2.setName("Flight 102");

        var r1 = new FlightRoute();
        r1.setName("Route A");

        airline.setFlights(List.of(f1, f2));
        airline.setFlightRoutes(List.of(r1));

        AirlineDTO dto = mapper.mapFullAirline(airline);

        assertEquals(2, dto.getFlightsNames().size());
        assertEquals(1, dto.getFlightRoutesNames().size());
        assertTrue(dto.getFlightsNames().contains("Flight 101"));
        assertTrue(dto.getFlightRoutesNames().contains("Route A"));
    }

    @Test
    void mapUser_shouldMapCustomerAsBaseCustomerDTO() {
        Customer customer = new Customer();
        customer.setNickname("user1");
        UserDTO dto = mapper.mapUser(customer);

        assertTrue(dto instanceof BaseCustomerDTO);
        assertEquals("user1", dto.getNickname());
    }

    @Test
    void mapUser_shouldMapAirlineAsBaseAirlineDTO() {
        Airline airline = new Airline();
        airline.setNickname("airUser");
        UserDTO dto = mapper.mapUser(airline);

        assertTrue(dto instanceof BaseAirlineDTO);
        assertEquals("airUser", dto.getNickname());
    }

    @Test
    void mapFullUser_shouldReturnCustomerDTO() {
        Customer customer = new Customer();
        customer.setNickname("fullCus");
        UserDTO dto = mapper.mapFullUser(customer);

        assertTrue(dto instanceof CustomerDTO);
        assertEquals("fullCus", dto.getNickname());
    }

    @Test
    void mapFullUser_shouldReturnAirlineDTO() {
        Airline airline = new Airline();
        airline.setNickname("fullAir");
        UserDTO dto = mapper.mapFullUser(airline);

        assertTrue(dto instanceof AirlineDTO);
        assertEquals("fullAir", dto.getNickname());
    }

    @Test
    void mapUserDTO_shouldMapBaseCustomerToCustomer() {
        BaseCustomerDTO dto = new BaseCustomerDTO();
        dto.setNickname("dtoUser");
        dto.setName("Pepe");

        User user = mapper.mapUserDTO(dto);
        assertTrue(user instanceof Customer);
        assertEquals("dtoUser", user.getNickname());
    }

    @Test
    void mapUserDTO_shouldMapBaseAirlineToAirline() {
        BaseAirlineDTO dto = new BaseAirlineDTO();
        dto.setNickname("dtoAir");
        dto.setName("AirName");

        User user = mapper.mapUserDTO(dto);
        assertTrue(user instanceof Airline);
        assertEquals("dtoAir", user.getNickname());
    }

    @Test
    void mapUser_shouldThrowIfUnknownType() {
        User unknown = new User() {
            @Override
            public void updateDataFrom(UserDTO userDTO) {
                // implementación vacía solo para cumplir con la clase abstracta
            }
        };

        assertThrows(IllegalArgumentException.class, () -> {
            mapper.mapUser(unknown);
        });
    }


    @Test
    void mapUserDTO_shouldThrowIfUnknownDTOType() {
        UserDTO dto = new UserDTO() {};
        assertThrows(IllegalArgumentException.class, () -> mapper.mapUserDTO(dto));
    }
}
