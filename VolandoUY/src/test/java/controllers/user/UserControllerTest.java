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
        BaseCustomerDTO customer = new BaseCustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setSurname("Ramirez");
        customer.setMail("gyabisito@example.com");
        customer.setPassword("password");
        customer.setBirthDate(LocalDate.of(2000, 1, 1));
        customer.setDocType(EnumTipoDocumento.CI);
        customer.setNumDoc("12345678");
        customer.setCitizenship("Uruguay");

        usuarioController.registerCustomer(customer, null);

        BaseCustomerDTO result = usuarioController.getCustomerSimpleDetailsByNickname("gyabisito");
        assertNotNull(result);
        assertEquals("gyabisito", result.getNickname());
    }

    @Test
    @DisplayName("Debe registrar una aerolínea correctamente")
    void registerAirline_shouldPersistAirline() {
        BaseAirlineDTO airline = new BaseAirlineDTO();
        airline.setNickname("flyuy");
        airline.setName("FlyUY");
        airline.setPassword("password");
        airline.setMail("contacto@flyuy.com");
        airline.setDescription("Low cost desde el cielo");

        usuarioController.registerAirline(airline, null);

        BaseAirlineDTO result = usuarioController.getAirlineSimpleDetailsByNickname("flyuy");

        assertNotNull(result);
        assertEquals("flyuy", result.getNickname());
    }

    @Test
    @DisplayName("Debe retornar todos los usuarios")
    void getAllUsers_shouldReturnAll() {
        BaseCustomerDTO customer = new BaseCustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setPassword("password");
        customer.setSurname("Ramirez");
        customer.setMail("email@gmail.com");
        customer.setBirthDate(LocalDate.of(2000, 1, 1));
        customer.setDocType(EnumTipoDocumento.CI);
        customer.setNumDoc("12345678");
        customer.setCitizenship("Uruguay");

        usuarioService.registerCustomer(customer, null);

        BaseAirlineDTO airline = new BaseAirlineDTO();
        airline.setNickname("flyuy");
        airline.setName("FlyUY");
        airline.setPassword("password");
        airline.setMail("f@gmail.com");
        airline.setDescription("Low cost desde el cielo");
        usuarioService.registerAirline(airline, null);

        List<UserDTO> users = usuarioController.getAllUsersSimpleDetails();

        assertEquals(2, users.size());
    }

    @Test
    @DisplayName("Debe retornar los nicknames de todos los usuarios")
    void getAllUsersNicknames_shouldReturnList() {
        BaseCustomerDTO customer = new BaseCustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setSurname("Ramirez");
        customer.setPassword("password");
        customer.setMail("email@gmail.com");
        customer.setBirthDate(LocalDate.of(2000, 1, 1));
        customer.setDocType(EnumTipoDocumento.CI);
        customer.setNumDoc("12345678");
        customer.setCitizenship("Uruguay");

        usuarioService.registerCustomer(customer, null);

        BaseAirlineDTO airline = new BaseAirlineDTO();
        airline.setNickname("flyuy");
        airline.setName("FlyUY");
        airline.setPassword("password");
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
        BaseCustomerDTO customer = new BaseCustomerDTO();
        customer.setNickname("gyabisito");
        customer.setName("Jose");
        customer.setSurname("Ramirez");
        customer.setPassword("password");
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
