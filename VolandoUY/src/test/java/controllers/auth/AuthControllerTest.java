package controllers.auth;

import domain.dtos.user.LoginResponseDTO;
import domain.dtos.user.CustomerDTO;
import domain.models.enums.EnumTipoDocumento;
import domain.services.auth.IAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    private IAuthService authService;
    private AuthController authController;

    @BeforeEach
    void setUp() {
        authService = mock(IAuthService.class);
        authController = new AuthController(authService);
    }

    @Test
    void login_shouldDelegateToAuthServiceAndReturnResponse() {
        // GIVEN
        String nickname = "testUser";
        String password = "password";
        CustomerDTO user = new CustomerDTO(
                nickname, "Name", "mail@mail.com", "hashedPass", null,
                "Surname", "UY", LocalDate.of(1990, 1, 1), "123", EnumTipoDocumento.CI
        );
        LoginResponseDTO expected = new LoginResponseDTO("token123", user);

        when(authService.login(nickname, password)).thenReturn(expected);

        // WHEN
        LoginResponseDTO result = authController.login(nickname, password);

        // THEN
        assertNotNull(result);
        assertEquals("token123", result.getToken());
        assertEquals(nickname, result.getUser().getNickname());
        verify(authService, times(1)).login(nickname, password);
    }

    @Test
    void isAuthenticated_shouldReturnTrueForValidToken() {
        // GIVEN
        String token = "valid.token";
        when(authService.isAuthenticated(token)).thenReturn(true);

        // WHEN
        boolean result = authController.isAuthenticated(token);

        // THEN
        assertTrue(result);
        verify(authService).isAuthenticated(token);
    }

    @Test
    void isAuthenticated_shouldReturnFalseForInvalidToken() {
        // GIVEN
        String token = "invalid.token";
        when(authService.isAuthenticated(token)).thenReturn(false);

        // WHEN
        boolean result = authController.isAuthenticated(token);

        // THEN
        assertFalse(result);
        verify(authService).isAuthenticated(token);
    }

    @Test
    void getNicknameFromToken_shouldReturnNicknameForValidToken() {
        // GIVEN
        String token = "valid.token";
        when(authService.getNicknameFromToken(token)).thenReturn("nick123");

        // WHEN
        String nickname = authController.getNicknameFromToken(token);

        // THEN
        assertEquals("nick123", nickname);
        verify(authService).getNicknameFromToken(token);
    }

    @Test
    void getNicknameFromToken_shouldReturnNullForInvalidToken() {
        // GIVEN
        String token = "invalid.token";
        when(authService.getNicknameFromToken(token)).thenReturn(null);

        // WHEN
        String nickname = authController.getNicknameFromToken(token);

        // THEN
        assertNull(nickname);
        verify(authService).getNicknameFromToken(token);
    }
}
