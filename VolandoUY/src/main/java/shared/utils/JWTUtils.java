package shared.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.io.Decoders;

import javax.crypto.SecretKey;
import java.util.Date;

public class JWTUtils {

    // Clave secreta en base64 (mínimo 256 bits para HS256 = 32 bytes)
    //️ Usá un valor real en producción. Podés generar con openssl o JWT.io
    private static final String SECRET_KEY_BASE64 = "HbWSzbEN5vj6zidANsQu/NERhSav43vZVAdUQ4+okjQ=   ";

    // Clave derivada
    private static final SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(SECRET_KEY_BASE64));

    // Expiración del token: 6 horas
    private static final long EXPIRATION_TIME_MS = 6 * 60 * 60 * 1000;

    // Generar JWT con subject
    public static String generateToken(String subject) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_TIME_MS);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)  // No se necesita especificar algoritmo con `jjwt` 0.13+
                .compact();
    }
    // Sobrecarga para tests
    public static String generateToken(String subject, long customExpirationMillis) {
        Date now = new Date();
        Date expiry = new Date(now.getTime() + customExpirationMillis);

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiry)
                .signWith(key)
                .compact();
    }

    // Extraer subject (por ejemplo, el userId o email)
    public static String getSubjectFromToken(String token) {
        JwtParser parser = Jwts.parser()
                .verifyWith(key)
                .build();

        Jws<Claims> jws = parser.parseSignedClaims(token);
        return jws.getPayload().getSubject();
    }

    // Validar token (firma, expiración, etc.)
    public static boolean validateToken(String token) {
        try {
            JwtParser parser = Jwts.parser()
                    .verifyWith(key)
                    .build();

            parser.parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Firma inválida, token expirado, etc.
            return false;
        }
    }
}
