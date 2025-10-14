package controllers.auth;

import controllers.user.IUserController;
import domain.dtos.user.UserDTO;
import domain.services.auth.IAuthService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import shared.utils.JWTUtils;
import shared.utils.PasswordManager;

public class AuthController implements IAuthController {

    private IAuthService authService= ServiceFactory.getAuthService();

    @Override
    public String login(String nickname, String password) {
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
