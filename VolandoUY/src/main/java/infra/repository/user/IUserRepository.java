package infra.repository.user;

import domain.models.user.User;

public interface IUserRepository {
    User getUserByEmail(String email);
    User getUserByNickname(String nickname);
    void save(User user);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    Iterable<User> findAll();
}
