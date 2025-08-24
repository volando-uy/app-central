package infra.repository.user;

import domain.models.user.User;
import domain.models.user.Customer;
import domain.models.user.Airline;
import infra.repository.BaseRepository;

import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository<User> implements IUserRepository {
    private final CustomerRepository customerRepo = new CustomerRepository();
    private final AirlineRepository airlineRepo = new AirlineRepository();

    public UserRepository() {
        super(User.class);
    }
    @Override
    public User getUserByEmail(String email) {
        User user = customerRepo.findByEmail(email);
        if (user == null) user = airlineRepo.findByEmail(email);
        return user;
    }

    @Override
    public User getUserByNickname(String nickname) {
        User user = customerRepo.findByNickname(nickname);
        if (user == null) user = airlineRepo.findByNickname(nickname);
        return user;
    }

    @Override
    public void save(User user) {
        if (user instanceof Customer) {
            customerRepo.save((Customer) user);
        } else if (user instanceof Airline) {
            airlineRepo.save((Airline) user);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no soportado");
        }
    }

    @Override
    public boolean existsByEmail(String email) {
        return customerRepo.existsByEmail(email) || airlineRepo.existsByEmail(email);
    }

    @Override
    public boolean existsByNickname(String nickname) {
        return customerRepo.existsByNickname(nickname) || airlineRepo.existsByNickname(nickname);
    }

    @Override
    public List<User> findAll() {
        List<User> all = new ArrayList<>();
        all.addAll(customerRepo.findAll());
        all.addAll(airlineRepo.findAll());
        return all;
    }
    public List<Airline> getAllAirlines() {
        return airlineRepo.findAll();
    }

}
