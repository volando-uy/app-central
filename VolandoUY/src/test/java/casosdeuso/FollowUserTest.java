package casosdeuso;

import app.DBConnection;
import app.config.DBInitThread;
import controllers.user.IUserController;
import controllers.user.UserController;
import domain.dtos.user.*;
import domain.models.enums.EnumTipoDocumento;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.*;

import javax.naming.ldap.Control;
import java.security.Provider;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FollowUserTest {

    private IUserController userController = ControllerFactory.getUserController();

    @BeforeEach
    void cleanDBAndSetUpOnce() {
        DBConnection.cleanDB();
        registerUsers();
    }

    private void registerUsers() {
        userController.registerCustomer( new BaseCustomerDTO() {{
            setNickname("customer1");
            setName("Juan");
            setSurname("Perez");
            setMail("c1@gmail.com");
            setPassword("password");
            setBirthDate(LocalDate.of(1990, 5, 20));
            setCitizenship("Uruguaya");
            setDocType(EnumTipoDocumento.CI);
            setNumDoc("12345678");
        }}, null);

        userController.registerCustomer( new BaseCustomerDTO() {{
            setNickname("customer2");
            setName("Maria");
            setSurname("Gomez");
            setMail("c2@gmail.com");
            setPassword("password");
            setBirthDate(LocalDate.of(1992, 8, 15));
            setCitizenship("Uruguaya");
            setDocType(EnumTipoDocumento.CI);
            setNumDoc("87654321");
        }}, null);

        userController.registerAirline( new BaseAirlineDTO() {{
            setNickname("airline1");
            setName("Aerolínea Uno");
            setMail("a1@gmail.com");
            setPassword("password");
            setDescription("La mejor aerolínea");
            setWeb("www.aerolinea1.com");
        }}, null);

        userController.registerAirline( new BaseAirlineDTO() {{
            setNickname("airline2");
            setName("Aerolínea Uno");
            setMail("a2@gmail.com");
            setPassword("password");
            setDescription("La mejor aerolínea");
            setWeb("www.aerolinea1.com");
        }}, null);
    }

    @Test
    @DisplayName("Follow User: Cliente debe poder seguir a otro cliente")
    void followUser_customerShouldFollowCustomer() {
        String followerNickname = "customer1";
        String followedNickname = "customer2";

        // When
        userController.followUser(followerNickname, followedNickname);

        // Then
        Boolean isFollowing = userController.isFollowing(followerNickname, followedNickname);
        Assertions.assertTrue(isFollowing);
    }

    @Test
    @DisplayName("Follow User: Cliente debe poder seguir a una aerolinea")
    void followUser_customerShouldFollowAirline() {
        String followerNickname = "customer1";
        String followedNickname = "airline1";

        // When
        userController.followUser(followerNickname, followedNickname);

        // Then
        Boolean isFollowing = userController.isFollowing(followerNickname, followedNickname);
        Assertions.assertTrue(isFollowing);
    }

    @Test
    @DisplayName("Follow User: Airline debe poder seguir a otra aerolinea")
    void followUser_airlineShouldFollowAirline() {
        String followerNickname = "airline1";
        String followedNickname = "airline2";

        // When
        userController.followUser(followerNickname, followedNickname);

        // Then
        Boolean isFollowing = userController.isFollowing(followerNickname, followedNickname);
        Assertions.assertTrue(isFollowing);
    }

    @Test
    @DisplayName("Follow User: Airline debe poder seguir a un cliente")
    void followUser_airlineShouldFollowCustomer() {
        String followerNickname = "airline1";
        String followedNickname = "customer1";

        // When
        userController.followUser(followerNickname, followedNickname);

        // Then
        Boolean isFollowing = userController.isFollowing(followerNickname, followedNickname);
        Assertions.assertTrue(isFollowing);
    }


    @Test
    @DisplayName("Unfollow User: Un usuario debe poder dejar de seguir a otro usuario")
    void unfollowUser_airlineShouldFollowCustomer() {
        String followerNickname = "customer1";
        String followedNickname = "airline1";

        // When
        userController.followUser(followerNickname, followedNickname);

        // Then
        Boolean isFollowing = userController.isFollowing(followerNickname, followedNickname);
        Assertions.assertTrue(isFollowing);

        // When
        userController.unfollowUser(followerNickname, followedNickname);

        // Then
        isFollowing = userController.isFollowing(followerNickname, followedNickname);
        Assertions.assertFalse(isFollowing);
    }
}
