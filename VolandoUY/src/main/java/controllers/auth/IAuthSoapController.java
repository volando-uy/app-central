package controllers.auth;

import app.adapters.dto.auth.SoapLoginResponseDTO;

public interface IAuthSoapController extends IBaseAuthController {
    SoapLoginResponseDTO login(String nickname, String password);

}
