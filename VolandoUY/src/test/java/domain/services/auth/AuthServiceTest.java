package domain.services.auth;

import controllers.user.IUserController;
import domain.dtos.user.*;
import domain.models.enums.EnumTipoDocumento;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import shared.utils.JWTUtils;
import shared.utils.PasswordManager;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthServiceTest {

    private IUserController userController;
    private AuthService authService;

    @BeforeEach
    void setUp() {
        userController = mock(IUserController.class);
        authService = new AuthService(userController);
    }

    // VALID CUSTOMER
    @Test
    void login_shouldReturnTokenForValidCustomerCredentials() {
        String nickname = "validCustomer";
        String rawPassword = "customer123";
        String hashedPassword = PasswordManager.hashPassword(rawPassword);

        UserDTO user = new CustomerDTO(
                nickname, "Ana", "ana@test.com", hashedPassword, null,
                "Pérez", "Uruguay", LocalDate.of(1990, 5, 15),
                "12345678", EnumTipoDocumento.CI
        );

        when(userController.getUserSimpleDetailsByNickname(nickname)).thenReturn(user);

        LoginResponseDTO response = authService.login(nickname, rawPassword);

        assertNotNull(response);
        assertEquals(nickname, response.getUser().getNickname());
        assertTrue(JWTUtils.validateToken(response.getToken()));
    }

    // INVALID CUSTOMER
    @Test
    void login_shouldReturnNullForInvalidCustomerPassword() {
        String nickname = "invalidCustomer";
        String correctPassword = "correctPass";
        String wrongPassword = "wrongPass";
        String hashedPassword = PasswordManager.hashPassword(correctPassword);

        UserDTO user = new CustomerDTO(
                nickname, "Juan", "juan@test.com", hashedPassword, null,
                "Rodríguez", "Argentina", LocalDate.of(1985, 3, 22),
                "98765432", EnumTipoDocumento.CI
        );

        when(userController.getUserSimpleDetailsByNickname(nickname)).thenReturn(user);

        LoginResponseDTO response = authService.login(nickname, wrongPassword);

        assertNull(response);
    }

    // VALID AIRLINE
    @Test
    void login_shouldReturnTokenForValidAirlineCredentials() {
        String nickname = "validAirline";
        String rawPassword = "airline123";
        String hashedPassword = PasswordManager.hashPassword(rawPassword);

        UserDTO user = new AirlineDTO(
                nickname, "AirTest", "air@test.com", hashedPassword,
                null, "Aerolinea de prueba", "https://aerolinea.com"
        );

        when(userController.getUserSimpleDetailsByNickname(nickname)).thenReturn(user);

        LoginResponseDTO response = authService.login(nickname, rawPassword);

        assertNotNull(response);
        assertEquals(nickname, response.getUser().getNickname());
        assertTrue(JWTUtils.validateToken(response.getToken()));
    }

    // INVALID AIRLINE
    @Test
    void login_shouldReturnNullForInvalidAirlinePassword() {
        String nickname = "invalidAirline";
        String correctPassword = "correctPass";
        String wrongPassword = "wrongPass";
        String hashedPassword = PasswordManager.hashPassword(correctPassword);

        UserDTO user = new AirlineDTO(
                nickname, "AirTest", "air@test.com", hashedPassword,
                null, "Aerolinea de prueba", "https://aerolinea.com"
        );

        when(userController.getUserSimpleDetailsByNickname(nickname)).thenReturn(user);

        LoginResponseDTO response = authService.login(nickname, wrongPassword);

        assertNull(response);
    }

    // USER NOT FOUND
    @Test
    void login_shouldReturnNullIfUserDoesNotExist() {
        when(userController.getUserSimpleDetailsByNickname("nonexistent")).thenReturn(null);

        LoginResponseDTO response = authService.login("nonexistent", "somepass");

        assertNull(response);
    }

    // VALID TOKEN
    @Test
    void isAuthenticated_shouldReturnTrueForValidToken() {
        String token = JWTUtils.generateToken("testUser");

        assertTrue(authService.isAuthenticated(token));
    }

    // INVALID TOKEN
    @Test
    void isAuthenticated_shouldReturnFalseForInvalidToken() {
        String fakeToken = "not.a.real.token";

        assertFalse(authService.isAuthenticated(fakeToken));
    }

    // VALID TOKEN TO NICKNAME
    @Test
    void getNicknameFromToken_shouldReturnNicknameIfTokenValid() {
        String token = JWTUtils.generateToken("user123");

        String result = authService.getNicknameFromToken(token);

        assertEquals("user123", result);
    }

    // INVALID TOKEN TO NICKNAME
    @Test
    void getNicknameFromToken_shouldReturnNullIfTokenInvalid() {
        String invalidToken = "bad.token";

        String result = authService.getNicknameFromToken(invalidToken);

        assertNull(result);
    }
}
