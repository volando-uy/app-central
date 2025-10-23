package utils;

import org.junit.jupiter.api.Test;
import shared.utils.JWTUtils;

import static org.junit.jupiter.api.Assertions.*;

public class JWTUtilsTest {

    @Test
    void generateToken_shouldReturnValidJWT() {
        // GIVEN
        String subject = "testUser";

        // WHEN
        String token = JWTUtils.generateToken(subject);

        // THEN
        assertNotNull(token);
        assertTrue(token.length() > 10);  // Básica: se generó algo
        assertTrue(JWTUtils.validateToken(token)); // El token debe ser válido
    }

    @Test
    void getSubjectFromToken_shouldReturnCorrectSubject() {
        // GIVEN
        String subject = "anotherUser";
        String token = JWTUtils.generateToken(subject);

        // WHEN
        String extracted = JWTUtils.getSubjectFromToken(token);

        // THEN
        assertEquals(subject, extracted);
    }

    @Test
    void validateToken_shouldReturnFalseForInvalidToken() {
        // GIVEN: token completamente inválido (ni siquiera con buena firma)
        String fakeToken = "abc.def.ghi";

        // WHEN
        boolean isValid = JWTUtils.validateToken(fakeToken);

        // THEN
        assertFalse(isValid);
    }

    @Test
    void validateToken_shouldReturnFalseForExpiredToken() throws InterruptedException {
        String token = JWTUtils.generateToken("expiringUser", 10); // 10 ms
        Thread.sleep(20);
        assertFalse(JWTUtils.validateToken(token));
    }
}
