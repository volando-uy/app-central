package infra.repository.user;

import domain.models.user.User;
import domain.models.user.Customer;
import domain.models.user.Airline;
import infra.repository.IBaseRepository;
import java.util.List;

public interface IUserRepository extends IBaseRepository<User> {
    User getUserByEmail(String email);
    User getUserByNickname(String nickname, boolean full);
    void save(User user);
    boolean existsByEmail(String email);
    boolean existsByNickname(String nickname);
    List<User> findAll();
    List<Airline> getAllAirlines();
    List<Customer> getAllCustomers();
    List<Airline> getFullAllAirlines();
    List<Customer> getFullAllCustomers();
}
