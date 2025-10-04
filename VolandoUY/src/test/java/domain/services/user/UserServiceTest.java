package domain.services.user;

import app.DBConnection;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.*;
import domain.models.enums.EnumTipoDocumento;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.mapper.UserMapper;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;


    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        ModelMapper modelMapper = new ModelMapper();
        UserMapper userMapper = new UserMapper(modelMapper);
        userService = new UserService();
    }

    @Test
    void registerCustomer_shouldAddCustomerSuccessfully_withAllFields() {
        // GIVEN
        CustomerDTO dto = new CustomerDTO(
                "nick1", "Pepe", "pepe@mail.com", "González", "Uruguay",
                LocalDate.of(1990, 1, 1), "12345678", EnumTipoDocumento.CI
        );

        // WHEN
        BaseCustomerDTO result = userService.registerCustomer(dto);

        // THEN
        assertNotNull(result);
        assertEquals("nick1", result.getNickname());
        assertEquals("González", result.getSurname());
        assertEquals("Uruguay", result.getCitizenship());
    }

    @Test
    void registerCustomer_shouldThrowExceptionIfUserExists() {
        // GIVEN
        CustomerDTO dto = new CustomerDTO(
                "nick2", "Pama", "pama@mail.com", "Pérez", "Argentina",
                LocalDate.of(1985, 5, 5), "87654321", EnumTipoDocumento.PASAPORTE
        );
        userService.registerCustomer(dto);

        // WHEN + THEN
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            userService.registerCustomer(dto);
        });
        assertEquals(String.format(ErrorMessages.ERR_USER_EXISTS, dto.getNickname()), ex.getMessage());
    }

    @Test
    void registerAirline_shouldRegisterSuccessfully() {
        // GIVEN
        AirlineDTO dto = new AirlineDTO("air123", "FlyHigh", "fly@mail.com", "Líder regional", "www.flyhigh.com");

        // WHEN
        BaseAirlineDTO result = userService.registerAirline(dto);

        // THEN
        assertNotNull(result);
        assertEquals("air123", result.getNickname());
        assertEquals("FlyHigh", result.getName());
    }

    @Test
    void getUserByNickname_shouldReturnCustomerDTO() {
        // GIVEN
        CustomerDTO dto = new CustomerDTO(
                "nickCustomer", "Pama", "pama@mail.com", "Sosa", "Uruguay",
                LocalDate.of(1995, 3, 15), "45612378", EnumTipoDocumento.CI
        );
        userService.registerCustomer(dto);

        // WHEN
        UserDTO result = userService.getUserDetailsByNickname("nickCustomer",false);

        // THEN
        assertNotNull(result);
        assertEquals("Pama", result.getName());
        assertTrue(result instanceof BaseCustomerDTO);
        assertEquals("Sosa", ((BaseCustomerDTO) result).getSurname());
    }

    @Test
    void getAllUsersNicknames_shouldReturnAllNicknames() {
        // GIVEN
        CustomerDTO dto = new CustomerDTO(
                "nick1", "Carlos", "carlos@mail.com", "Pérez", "Uruguay",
                LocalDate.of(1995, 1, 1), "12345678", EnumTipoDocumento.CI
        );
        userService.registerCustomer(dto);

        // WHEN
        List<String> nicknames = userService.getAllUsersNicknames();

        // THEN
        assertEquals(1, nicknames.size());
        assertTrue(nicknames.contains("nick1"));
    }

    @Test
    void getAllUsers_shouldReturnAllUsersMapped() {
        // GIVEN
        CustomerDTO dto = new CustomerDTO(
                "nick1", "Luis", "luis@mail.com", "Gómez", "Paraguay",
                LocalDate.of(1990, 5, 15), "99911122", EnumTipoDocumento.CI
        );
        userService.registerCustomer(dto);

        // WHEN
        List<UserDTO> allUsers = userService.getAllUsers(false);

        // THEN
        assertEquals(1, allUsers.size());
        assertEquals("nick1", allUsers.get(0).getNickname());
    }

    @Test
    void getUserByNickname_shouldThrowExceptionIfNotFound() {
        // WHEN + THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.getUserDetailsByNickname("inexistente",false);
        });
        //El usuario inexistente no fue encontrado
        assertEquals(String.format(ErrorMessages.ERR_USER_NOT_FOUND, "inexistente"), ex.getMessage());
    }

    @Test
    void updateUser_shouldUpdateCustomerData() {
        // GIVEN
        CustomerDTO original = new CustomerDTO(
                "cliente1", "Pedro", "pedro@mail.com", "López", "Chile",
                LocalDate.of(1980, 1, 1), "98765432", EnumTipoDocumento.CI
        );
        CustomerDTO updated = new CustomerDTO(
                "cliente1", "Pedro Actualizado", "pedro@mail.com", "García", "Argentina",
                LocalDate.of(1982, 2, 2), "99887766", EnumTipoDocumento.PASAPORTE
        );
        userService.registerCustomer(original);

        // WHEN
        BaseCustomerDTO customerResult = (BaseCustomerDTO) userService.updateUser("cliente1", updated, null);

        // THEN
        assertEquals("Pedro Actualizado", customerResult.getName());
        assertEquals("Argentina", customerResult.getCitizenship());

    }

    @Test
    void updateUser_shouldUpdateSuccessfully() {
        // GIVEN
        AirlineDTO original = new AirlineDTO("airline1", "Airline One", "air@gmail.com", "Desc123456789", "www.original.com");
        AirlineDTO updated = new AirlineDTO("airline1", "Airline One Updated", "air@gmail.com", "New Desc123456789", "www.updated.com");
        userService.registerAirline(original);

        // WHEN
        UserDTO result = userService.updateUser("airline1", updated, null);

        // THEN
        assertNotNull(result);
        assertTrue(result instanceof BaseAirlineDTO);
        assertEquals("Airline One Updated", result.getName());
        assertEquals("New Desc123456789", ((BaseAirlineDTO) result).getDescription());
        assertEquals("www.updated.com", ((BaseAirlineDTO) result).getWeb());
    }

    @Test
    void updateUser_shouldThrowExceptionIfUserNotFound() {
        // GIVEN
        AirlineDTO updatedDTO = new AirlineDTO("notFound", "Name", "email@mail.com", "desc123456789", "web");

        // WHEN + THEN
        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.updateUser("notFound", updatedDTO, null);
        });

        assertTrue(String.format(ErrorMessages.ERR_USER_NOT_FOUND, "notFound").contains("notFound"));
    }

    @Test
    void getAllAirlines_shouldReturnOnlyAirlines() {
        // GIVEN
        userService.registerAirline(new AirlineDTO("air1", "Air1", "a1@mail.com", "desc123456", "www.air1.com"));
        userService.registerAirline(new AirlineDTO("air2", "Air2", "a2@mail.com", "desc234567", "www.air2.com"));

        // WHEN
        List<AirlineDTO> result = userService.getAllAirlinesDetails(false);

        // THEN
        assertEquals(2, result.size());
        assertTrue(result.stream().anyMatch(a -> a.getNickname().equals("air1")));
    }

    @Test
    void getAirlineByNickname_shouldReturnEntity() {
        // GIVEN
        AirlineDTO dto = new AirlineDTO("vuela", "Vuela", "vuela@mail.com", "Super Airline", "web.com");
        userService.registerAirline(dto);

        // WHEN
        Airline result = userService.getAirlineByNickname("vuela",false);

        // THEN
        assertEquals("vuela", result.getNickname());
        assertEquals("Vuela", result.getName());
    }

    @Test
    void getAirlineDetailsByNickname_shouldReturnDTO() {
        // GIVEN
        AirlineDTO dto = new AirlineDTO("express", "Express", "express@mail.com", "Compañía aérea rápida", "express.com");
        userService.registerAirline(dto);

        // WHEN
        AirlineDTO result = userService.getAirlineDetailsByNickname("express",false);

        // THEN
        assertNotNull(result);
        assertEquals("express", result.getNickname());
        assertEquals("Express", result.getName());
        assertEquals("Compañía aérea rápida", result.getDescription());
        assertEquals("express.com", result.getWeb());
    }

    @Test
    void getUserByNickname_shouldBeCaseInsensitive() {
        // GIVEN
        userService.registerCustomer(new CustomerDTO(
                "TeStNiCk", "Nombre", "mail@test.com", "Apellido", "Pais",
                LocalDate.of(1990, 1, 1), "12345678", EnumTipoDocumento.CI));

        // WHEN
        UserDTO result = userService.getUserDetailsByNickname("testnick",false);

        // THEN
        assertNotNull(result);
        assertEquals("TeStNiCk", result.getNickname());
    }

    @Test
    void registerCustomer_shouldThrowIfMailAlreadyExists() {
        // GIVEN
        CustomerDTO first = new CustomerDTO(
                "nick1", "Nombre", "shared@mail.com", "Apellido", "Pais",
                LocalDate.of(1990, 1, 1), "12345678", EnumTipoDocumento.CI
        );
        CustomerDTO second = new CustomerDTO(
                "nick2", "Otro", "shared@mail.com", "Apellido", "Pais",
                LocalDate.of(1990, 1, 1), "87654321", EnumTipoDocumento.CI
        );

        userService.registerCustomer(first);

        // WHEN + THEN
        UnsupportedOperationException ex = assertThrows(UnsupportedOperationException.class, () -> {
            userService.registerCustomer(second);
        });

        assertEquals(String.format(ErrorMessages.ERR_USER_EXISTS, second.getNickname()), ex.getMessage());
    }

    @Test
    void registerCustomer_shouldFailValidationIfDataIsInvalid() {
        // GIVEN: Nickname vacío
        CustomerDTO invalid = new CustomerDTO(
                "", "Nombre", "badmail.com", "Apellido", "Pais",
                LocalDate.of(1990, 1, 1), "", null
        );

        // WHEN + THEN
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> {
            userService.registerCustomer(invalid);
        });

        assertTrue(ex.getMessage().contains("nickname"));
    }

}
