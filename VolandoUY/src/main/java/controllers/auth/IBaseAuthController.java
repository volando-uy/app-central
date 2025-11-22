package controllers.auth;

public interface IBaseAuthController {

    boolean isAuthenticated(String token);

    String getNicknameFromToken(String token);

    boolean isAirline(String token);

    boolean isCustomer(String token);
}
