package domain.services.auth;

import controllers.user.IUserController;
import domain.dtos.user.UserDTO;
import factory.ControllerFactory;
import shared.utils.JWTUtils;
import shared.utils.PasswordManager;

public class AuthService implements IAuthService{
    private final IUserController userController = ControllerFactory.getUserController();

    @Override
    public String login(String nickname, String password) {
        UserDTO user = userController.getUserSimpleDetailsByNickname(nickname);

        if (user != null && user.getPassword() != null &&
                PasswordManager.validatePassword(password, user.getPassword())) {
            return JWTUtils.generateToken(nickname);
        }

        return null;
    }

    @Override
    public boolean isAuthenticated(String token) {
        return JWTUtils.validateToken(token);
    }

    @Override
    public String getNicknameFromToken(String token) {
        if (isAuthenticated(token)) {
            return JWTUtils.getSubjectFromToken(token);
        }
        return null;
    }
}
