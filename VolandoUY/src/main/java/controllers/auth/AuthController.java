package controllers.auth;

import domain.dtos.user.LoginResponseDTO;
import domain.services.auth.IAuthService;
import factory.ServiceFactory;

public class AuthController implements IAuthController {

    private IAuthService authService;
    public AuthController() {
        this.authService = ServiceFactory.getAuthService();
    }

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

    @Override
    public boolean isAirline(String token) {
        return authService.isAirline(token);
    }

    @Override
    public boolean isCustomer(String token) {
        return authService.isCustomer(token);
    }
}
