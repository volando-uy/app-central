package infra.repository.user;

import domain.models.buypackage.BuyPackage;
import domain.models.flightRoutePackage.FlightRoutePackage;
import domain.models.user.Airline;
import domain.models.user.Customer;
import domain.models.user.User;

import java.util.List;

public interface IUserRepository {

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
