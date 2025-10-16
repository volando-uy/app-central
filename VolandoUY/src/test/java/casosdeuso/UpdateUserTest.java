package casosdeuso;

import app.DBConnection;
import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.user.*;
import domain.models.enums.EnumTipoDocumento;
import domain.models.user.mapper.UserMapper;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS) // IMPORTANTE para que el @BeforeAll no sea estático, y se haga 1 unica instasncia de la clase
class UpdateUserTest {

    private IUserController userController;

    @BeforeAll
    void cleanDBAndSetUpOne() {
        // Limpiar la base de datos
        EntityManager em = DBConnection.getEntityManager();
        em.getTransaction().begin();
        em.createNativeQuery("TRUNCATE TABLE customer CASCADE").executeUpdate();
        em.createNativeQuery("TRUNCATE TABLE airline CASCADE").executeUpdate();
        em.getTransaction().commit();
        em.close();

        // Inicializar dependencias solo una vez
        ModelMapper modelMapper = new ModelMapper();
        UserMapper userMapper = new UserMapper(modelMapper);
        IUserService userService = new UserService();
        userController = new UserController(userService);

        // Crear datos base una sola vez
        BaseCustomerDTO testCustomer = new BaseCustomerDTO();
        testCustomer.setNickname("testNickname");
        testCustomer.setName("testName");
        testCustomer.setSurname("testSurname");
        testCustomer.setMail("test@mail.com");
        testCustomer.setPassword("testPassword");
        testCustomer.setCitizenship("testCitizenship");
        testCustomer.setDocType(EnumTipoDocumento.CI);
        testCustomer.setNumDoc("12345678");
        testCustomer.setBirthDate(LocalDate.of(1990, 1, 1));
        testCustomer.setImage(null);
        userController.registerCustomer(testCustomer, null);

        BaseAirlineDTO testAirline = new BaseAirlineDTO();
        testAirline.setNickname("airlineNickname");
        testAirline.setName("airlineName");
        testAirline.setMail("airline@mail.com");
        testAirline.setDescription("airlineDescription");
        testAirline.setWeb("testWeb.com");
        testAirline.setPassword("testPassword");
        testAirline.setImage(null);
        userController.registerAirline(testAirline, null);
    }



    @Test
    @DisplayName("Actualizar cliente: se deben reflejar los cambios en la entidad persistida")
    void updateCustomer_shouldReflectChanges() {
        // GIVEN
        String nickname = "gyabisito";
        BaseCustomerDTO original = (BaseCustomerDTO) userController.getUserSimpleDetailsByNickname(nickname);
        assertEquals("Jose", original.getName());

        // WHEN
        CustomerDTO modificado = new CustomerDTO();
        modificado.setNickname(nickname);
        modificado.setMail(original.getMail()); // inmutable
        modificado.setName("Carlos");
        modificado.setSurname("Martinez");
        modificado.setNumDoc("87654321");
        modificado.setCitizenship("Argentina");
        modificado.setBirthDate(LocalDate.of(1995, 5, 15));
        modificado.setDocType(EnumTipoDocumento.RUT);

        userController.updateUser(nickname, modificado, null);

        // THEN
        BaseCustomerDTO actualizado = (BaseCustomerDTO) userController.getUserSimpleDetailsByNickname(nickname);
        assertEquals("Carlos", actualizado.getName());
        assertEquals("Martinez", actualizado.getSurname());
        assertEquals("87654321", actualizado.getNumDoc());
        assertEquals("Argentina", actualizado.getCitizenship());
        assertEquals(LocalDate.of(1995, 5, 15), actualizado.getBirthDate());
        assertEquals(EnumTipoDocumento.RUT, actualizado.getDocType());

        // Campos inmutables
        assertEquals("gyabisito", actualizado.getNickname());
        assertEquals("gyabisito@mail.com", actualizado.getMail());
    }

    @Test
    @DisplayName("Actualizar aerolínea: se deben reflejar los cambios correctamente")
    void updateAirline_shouldReflectChanges() {
        // GIVEN
        String nickname = "flyuy";
        BaseAirlineDTO original = (BaseAirlineDTO) userController.getUserSimpleDetailsByNickname(nickname);
        assertEquals("FlyUY", original.getName());

        // WHEN
        AirlineDTO modificado = new AirlineDTO();
        modificado.setNickname(nickname);
        modificado.setMail(original.getMail()); // inmutable
        modificado.setName("FlyUruguay");
        modificado.setDescription("Nueva descripción");
        modificado.setWeb("www.flyuy.net");

        userController.updateUser(nickname, modificado, null);

        // THEN
        BaseAirlineDTO actualizado = (BaseAirlineDTO) userController.getUserSimpleDetailsByNickname(nickname);
        assertEquals("FlyUruguay", actualizado.getName());
        assertEquals("Nueva descripción", actualizado.getDescription());
        assertEquals("www.flyuy.net", actualizado.getWeb());

        // Campos inmutables
        assertEquals("flyuy", actualizado.getNickname());
        assertEquals("flyuy@mail.com", actualizado.getMail());
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
