package casosdeuso;

import app.DBConnection;
import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.user.AirlineDTO;
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

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import static org.mockito.Mockito.verify;
@TestInstance(TestInstance.Lifecycle.PER_CLASS) // IMPORTANTE para que el @BeforeAll no sea estático, y se haga 1 unica instasncia de la clase
public class RegisterUserTest {

    private ModelMapper modelMapper = ControllerFactory.getModelMapper();
    private UserMapper userMapper = ControllerFactory.getUserMapper();

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
        userService = new UserService(modelMapper, userMapper);
        userController = new UserController(userService);

    }

    @Test
    @DisplayName("Debe crear el CustomerDTO")
    void registerCustomer_shouldCreateTheCustomer() {
        //Given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setNickname("gyabisito");
        customerDTO.setName("Jose");
        customerDTO.setSurname("Ramirez");
        customerDTO.setMail("gyabisito@example.com");
        customerDTO.setBirthDate(LocalDate.of(2000, 1, 1));
        customerDTO.setIdType(EnumTipoDocumento.CI);
        customerDTO.setId("01234567");
        customerDTO.setCitizenship("Uruguay");

        // When
        userController.registerCustomer(customerDTO);

        // Then
        UserDTO createdCustomer = userController.getUserByNickname("gyabisito");

        // Verificar que el CustomerDTO fue creado correctamente
        assertEquals("gyabisito", createdCustomer.getNickname());
    }

    @Test
    @DisplayName("Debe llamar a createCategory y mapear correctamente el DTO")
    void registerAirline_shouldCreateTheAirline() {
        // Given
        AirlineDTO airlineDTO = new AirlineDTO();
        airlineDTO.setNickname("flyuy");
        airlineDTO.setName("FlyUY");
        airlineDTO.setMail("flyuy@correo.com");
        airlineDTO.setDescription("Low cost desde el cielo");
        airlineDTO.setWeb("www.flyuy.com");

        // When
        userController.registerAirline(airlineDTO);

        // Then
        AirlineDTO createdAirline = userController.getAirlineByNickname("flyuy");

        // Verificar que el AirlineDTO fue creado correctamente
        assertEquals("flyuy", createdAirline.getNickname());
    }
    @AfterAll
    void cleanDBAfterAll() {
        // Limpiar la base de datos
        EntityManager em = DBConnection.getEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE customer CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE airline CASCADE").executeUpdate();
        em.getTransaction().commit();
        em.close();
    }
}
