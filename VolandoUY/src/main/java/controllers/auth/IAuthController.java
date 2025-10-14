package controllers.auth;

public interface IAuthController {
    String login(String nickname, String password);

    boolean isAuthenticated(String token);

    String getNicknameFromToken(String token);
}