package infra.repository.user;

import domain.models.user.Customer;

import java.util.List;

public interface ICustomerRepository extends IAbstractUserRepository<Customer> {
    Customer getCustomerByNickname(String nickname);
    Customer getCustomerByEmail(String email);
    List<Customer> findFullAll();
    Customer findFullByNickname(String nickname);
}
