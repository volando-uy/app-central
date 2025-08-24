package infra.repository.user;

import app.DBConnection;
import domain.models.user.Customer;
import infra.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class CustomerRepository extends BaseRepository<Customer> {
    public CustomerRepository() {
        super(Customer.class);
    }
    public boolean existsByNickname(String nickname) {
        return findByKey(nickname) != null;
    }
    public boolean existsByEmail(String email) {
        Query query= DBConnection.getEntityManager().createQuery("SELECT EXISTS (SELECT 1 FROM Customer c WHERE LOWER(c.mail) = :mail)");
        query.setParameter("mail", email);
        return (Boolean) query.getSingleResult();
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



}
