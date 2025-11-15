package infra.repository.user;

import domain.models.user.User;
import domain.models.user.Customer;
import domain.models.user.Airline;
import domain.models.user.follow.Follow;
import domain.models.user.follow.FollowKey;
import infra.repository.BaseRepository;
import infra.repository.user.follow.FollowRepository;
import infra.repository.user.follow.IFollowRepository;
import shared.constants.ErrorMessages;

import java.util.ArrayList;
import java.util.List;

public class UserRepository extends BaseRepository<User> implements IUserRepository {
    private final ICustomerRepository customerRepo = new CustomerRepository();
    private final IAirlineRepository airlineRepo = new AirlineRepository();
    private final IFollowRepository followRepo = new FollowRepository();

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
    public User getUserByNickname(String nickname, boolean full) {
        User user = full ? customerRepo.findFullByNickname(nickname) : customerRepo.findByNickname(nickname);
        if (user == null) user = full ? airlineRepo.findFullByNickname(nickname) : airlineRepo.findByNickname(nickname);
        return user;
    }

    @Override
    public void save(User user) {
        if (user instanceof Customer) {
            customerRepo.save((Customer) user);
        } else if (user instanceof Airline) {
            airlineRepo.save((Airline) user);
        } else {
            throw new IllegalArgumentException(ErrorMessages.ERR_USER_NOT_SUPPORTED);
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

    @Override
    public List<Airline> getAllAirlines() {
        return airlineRepo.findAll();
    }

    @Override
    public List<Customer> getAllCustomers() {return customerRepo.findAll();}

    @Override
    public List<Airline> getFullAllAirlines() {
        return airlineRepo.findFullAll();
    }

    @Override
    public List<Customer> getFullAllCustomers() {
        return customerRepo.findFullAll();
    }
}
