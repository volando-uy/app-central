package domain.services.auth;

import controllers.user.IUserController;
import domain.dtos.user.LoginResponseDTO;
import domain.dtos.user.UserDTO;
import factory.ControllerFactory;
import shared.utils.JWTUtils;
import shared.utils.PasswordManager;

public class AuthService implements IAuthService{
    private final IUserController userController;

    // Constructor original
    public AuthService() {
        this.userController = ControllerFactory.getUserController();
    }

    // Constructor para test
    public AuthService(IUserController userController) {
        this.userController = userController;
    }

    @Override
    public LoginResponseDTO login(String nickname, String password) {
        UserDTO user = this.userController.getUserSimpleDetailsByNickname(nickname);

        if (user != null && user.getPassword() != null &&
                PasswordManager.validatePassword(password, user.getPassword())) {

            String token = JWTUtils.generateToken(nickname);
            return new LoginResponseDTO(token, user);
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

    @Override
    public boolean isAirline(String token) {
        String nickname = getNicknameFromToken(token);
        UserDTO user = this.userController.getUserSimpleDetailsByNickname(nickname);
        return user != null && user.getClass().getSimpleName().equals("BaseAirlineDTO");
    }

    @Override
    public boolean isCustomer(String token) {
        return !isAirline(token);
    }
}
