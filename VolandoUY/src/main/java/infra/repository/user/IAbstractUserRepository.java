package infra.repository.user;

import domain.models.user.User;
import infra.repository.IBaseRepository;

public interface IAbstractUserRepository<T extends User> extends IBaseRepository<T> {
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    T findByNickname(String nickname);
    T findByEmail(String email);
}
