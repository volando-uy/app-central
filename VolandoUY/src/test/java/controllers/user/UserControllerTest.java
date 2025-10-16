package controllers.user;

import domain.dtos.user.*;
import domain.models.enums.EnumTipoDocumento;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.TestUtils;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    private IUserService usuarioService;
    private IUserController usuarioController;

    @BeforeEach
    void setUp() {
        TestUtils.cleanDB();
        usuarioService = new UserService();
        usuarioController = new UserController(usuarioService);
    }

    @Test
    @DisplayName("Debe registrar un customer correctamente")
    void registerCustomer_shouldPersistCustomer() {
        CustomerDTO customer = new CustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setSurname("Ramirez");
        customer.setMail("gyabisito@example.com");
        customer.setBirthDate(LocalDate.of(2000, 1, 1));
        customer.setDocType(EnumTipoDocumento.CI);
        customer.setNumDoc("12345678");
        customer.setCitizenship("Uruguay");

        usuarioController.registerCustomer(customer, null);

        UserDTO result = usuarioController.getCustomerDetailsByNickname("gyabisito");
        assertNotNull(result);
        assertEquals("gyabisito", result.getNickname());
    }

    @Test
    @DisplayName("Debe registrar una aerolínea correctamente")
    void registerAirline_shouldPersistAirline() {
        AirlineDTO airline = new AirlineDTO();
        airline.setNickname("flyuy");
        airline.setName("FlyUY");
        airline.setMail("contacto@flyuy.com");
        airline.setDescription("Low cost desde el cielo");

        usuarioController.registerAirline(airline, null);

        AirlineDTO result = usuarioController.getAirlineDetailsByNickname("flyuy");

        assertNotNull(result);
        assertEquals("flyuy", result.getNickname());
    }

    @Test
    @DisplayName("Debe retornar todos los usuarios")
    void getAllUsers_shouldReturnAll() {
        CustomerDTO customer = new CustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setSurname("Ramirez");
        customer.setMail("email@gmail.com");
        customer.setBirthDate(LocalDate.of(2000, 1, 1));
        customer.setDocType(EnumTipoDocumento.CI);
        customer.setNumDoc("12345678");
        customer.setCitizenship("Uruguay");

        usuarioService.registerCustomer(customer, null);

        AirlineDTO airline = new AirlineDTO();
        airline.setNickname("flyuy");
        airline.setName("FlyUY");
        airline.setMail("f@gmail.com");
        airline.setDescription("Low cost desde el cielo");

        usuarioService.registerAirline(airline, null);

        List<UserDTO> users = usuarioController.getAllUsersSimpleDetails();

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Debe retornar los nicknames de todos los usuarios")
    void getAllUsersNicknames_shouldReturnList() {
        CustomerDTO customer = new CustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setSurname("Ramirez");
        customer.setMail("email@gmail.com");
        customer.setBirthDate(LocalDate.of(2000, 1, 1));
        customer.setDocType(EnumTipoDocumento.CI);
        customer.setNumDoc("12345678");
        customer.setCitizenship("Uruguay");

        usuarioService.registerCustomer(customer, null);

        AirlineDTO airline = new AirlineDTO();
        airline.setNickname("flyuy");
        airline.setName("FlyUY");
        airline.setMail("f@gmail.com");
        airline.setDescription("Low cost desde el cielo");
        usuarioService.registerAirline(airline, null);

        List<String> nicknames = usuarioController.getAllUsersNicknames();

        assertTrue(nicknames.contains("gyabisito"));
        assertTrue(nicknames.contains("flyuy"));
    }

    @Test
    @DisplayName("Debe actualizar un usuario")
    void updateUser_shouldWorkCorrectly() {
        CustomerDTO customer = new CustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setSurname("Ramirez");
        customer.setMail("email@gmail.com");
        customer.setBirthDate(LocalDate.of(2000, 1, 1));
        customer.setDocType(EnumTipoDocumento.CI);
        customer.setNumDoc("12345678");
        customer.setCitizenship("Uruguay");
        usuarioService.registerCustomer(customer, null);

        CustomerDTO updated = new CustomerDTO();
        updated.setNickname("gyabisito");
        updated.setName("NuevoNombre");


        UserDTO result = usuarioController.updateUser("gyabisito", updated, null);

        assertNotNull(result);
        assertEquals("NuevoNombre", result.getName());
    }
}
