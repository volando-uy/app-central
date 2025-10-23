package controllers.auth;

import domain.dtos.user.LoginResponseDTO;

public interface IAuthController {
    LoginResponseDTO login(String nickname, String password);

    boolean isAuthenticated(String token);

    String getNicknameFromToken(String token);

    boolean isAirline(String token);

    boolean isCustomer(String token);
}