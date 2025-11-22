package controllers.auth;

import domain.dtos.user.LoginResponseDTO;

public interface IAuthController extends IBaseAuthController {
    LoginResponseDTO login(String nickname, String password);

}