package infra.repository.user;

import app.DBConnection;
import domain.models.user.Airline;
import domain.models.user.Customer;
import infra.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class CustomerRepository extends AbstractUserRepository<Customer> {
    public CustomerRepository() {
        super(Customer.class);
    }
    @Override
    protected String getEntityName() {
        return this.getEntityClass().getSimpleName();
    }

    @Override
    protected Class<Customer> getEntityClass() {
        return Customer.class;
    }

    public Customer getCustomerByNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT c FROM Customer c WHERE LOWER(c.nickname) = :nickname", Customer.class)
                    .setParameter("nickname", nickname.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    public Customer getCustomerByEmail(String email) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT c FROM Customer c WHERE LOWER(c.mail) = :mail", Customer.class)
                    .setParameter("mail", email.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }



}
