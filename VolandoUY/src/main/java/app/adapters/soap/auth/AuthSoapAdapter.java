package app.adapters.soap.auth;

import app.adapters.soap.BaseSoapAdapter;
import controllers.auth.IAuthController;
import domain.dtos.user.LoginResponseDTO;
import jakarta.jws.WebMethod;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
public class AuthSoapAdapter extends BaseSoapAdapter implements IAuthController {

    private final IAuthController controller;

    public AuthSoapAdapter(IAuthController controller) {
        this.controller = controller;
    }

    @Override
    protected String getServiceName() {
        return "authService";
    }

    @Override
    @WebMethod
    public LoginResponseDTO login(String nickname, String password) {
        return controller.login(nickname, password);
    }

    @Override
    @WebMethod
    public String getNicknameFromToken(String token) {
        return controller.getNicknameFromToken(token);
    }

    @Override
    @WebMethod
    public boolean isAirline(String token) {
        return controller.isAirline(token);
    }

    @Override
    @WebMethod
    public boolean isCustomer(String token) {
        return controller.isCustomer(token);
    }


    @Override
    @WebMethod
    public boolean isAuthenticated(String token) {
        return controller.isAuthenticated(token);
    }


}
