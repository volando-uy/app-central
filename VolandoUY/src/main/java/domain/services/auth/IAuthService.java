package domain.services.auth;

import domain.dtos.user.LoginResponseDTO;

public interface IAuthService {
    /**
     * Valida las credenciales del usuario y devuelve un token si son correctas.
     *
     * @param nickname nickname del usuario (cliente o aerolínea)
     * @param password contraseña en texto plano
     * @return token JWT si las credenciales son válidas, null en caso contrario
     */
    LoginResponseDTO login(String nickname, String password);

    /**
     * Verifica si un token es válido (firma y expiración)
     *
     * @param token JWT recibido del cliente
     * @return true si es válido
     */
    boolean isAuthenticated(String token);

    /**
     * Extrae el usuario del token, si es válido
     *
     * @param token JWT
     * @return nickname del usuario
     */
    String getNicknameFromToken(String token);
}
