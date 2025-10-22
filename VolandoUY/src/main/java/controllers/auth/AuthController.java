package controllers.auth;

import domain.dtos.user.LoginResponseDTO;
import domain.services.auth.IAuthService;

public class AuthController implements IAuthController {

    private final IAuthService authService;

    public AuthController(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    public LoginResponseDTO login(String nickname, String password) {
        return authService.login(nickname, password);
    }

    @Override
    public boolean isAuthenticated(String token) {
        return authService.isAuthenticated(token);
    }

    @Override
    public String getNicknameFromToken(String token) {
        return authService.getNicknameFromToken(token);
    }
}
