package casosdeuso;

import app.DBConnection;
import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.user.CustomerDTO;
import domain.dtos.user.UserDTO;
import domain.models.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ControllerFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
//TODO: Terminar test, esta a medias (falta los paquetes y reservas)
public class GetUserTest {

    private IUserService userService;
    private IUserController userController;

    @BeforeAll
    void cleanDBAndSetUpOnce() {
        EntityManager em = DBConnection.getEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE customer CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE airline CASCADE").executeUpdate();
        // otras tablas si aplican
        em.getTransaction().commit();
        em.close();
        userService = new UserService();
        userController = new UserController(userService);

    }

    @Test
    @DisplayName("GIVEN a registered customer WHEN searched by nickname THEN the correct user is returned")
    void getClient_shouldReturnRegisteredUser() {
        // ---------- GIVEN ----------
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname("cliente");
        customerDTO.setName("Juan");
        customerDTO.setSurname("Pérez");
        customerDTO.setMail("juan@example.com");
        customerDTO.setNumDoc("123");
        customerDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        customerDTO.setDocType(EnumTipoDocumento.CI);
        customerDTO.setCitizenship("Uruguayo");

        userController.registerCustomer(customerDTO);

        // ---------- WHEN ----------
        UserDTO retrievedUser = userController.getUserSimpleDetailsByNickname("cliente");

        // ---------- THEN ----------
        assertNotNull(retrievedUser, "El usuario no debería ser nulo");
        assertEquals("cliente", retrievedUser.getNickname(), "El nickname del usuario debería coincidir");
        assertEquals("Juan", retrievedUser.getName());
    }

    @Test
    @DisplayName("GIVEN a non-registered user WHEN searched by nickname THEN an exception is thrown")
    void getClient_shouldThrowExceptionForNonExistentUser() {
        // ---------- GIVEN ----------
        String nonExistentNickname = "no_existe";

        // ---------- WHEN & THEN ----------
        Exception exception = assertThrows(RuntimeException.class, () -> {
            userController.getUserSimpleDetailsByNickname(nonExistentNickname);
        });

        String expectedMessage = String.format(ErrorMessages.ERR_USER_NOT_FOUND, nonExistentNickname);
        String actualMessage = exception.getMessage();
        System.out.println(actualMessage);
        assertTrue(actualMessage.contains(expectedMessage), "El mensaje de la excepción debería coincidir");
    }

    @Test
    @DisplayName("GIVEN a registered airline WHEN searched by nickname THEN the correct user is returned")
    void getAirline_shouldReturnRegisteredAirline() {
        // ---------- GIVEN ----------
        domain.dtos.user.AirlineDTO airlineDTO = new domain.dtos.user.AirlineDTO();
        airlineDTO.setNickname("aerolinea");
        airlineDTO.setName("Aerolínea XYZ");
        airlineDTO.setMail("contacto@aerolíniaxyz.com");
        airlineDTO.setDescription("La mejor aerolínea del mundo");
        airlineDTO.setWeb("https://www.airline.com");
        userController.registerAirline(airlineDTO);
        // ---------- WHEN ----------
        UserDTO retrievedUser = userController.getUserSimpleDetailsByNickname("aerolinea");
        // ---------- THEN ----------
        assertNotNull(retrievedUser, "El usuario no debería ser nulo");
        assertEquals("aerolinea", retrievedUser.getNickname(), "El nickname del usuario debería coincidir");
        assertEquals("Aerolínea XYZ", retrievedUser.getName());

    }


}
