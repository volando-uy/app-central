package infra.repository.user;

import app.DBConnection;
import domain.models.user.Airline;
import domain.models.user.Customer;
import infra.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class AirlineRepository extends BaseRepository<Airline> {
    public AirlineRepository() {
        super(Airline.class);
    }

    public boolean existsByNickname(String nickname) {
        return findByKey(nickname) != null;
    }
    public boolean existsByEmail(String email) {
        Query query= DBConnection.getEntityManager().createQuery("SELECT EXISTS (SELECT 1 FROM Airline a WHERE LOWER(a.mail) = :mail)");
        query.setParameter("mail", email);
        return (Boolean) query.getSingleResult();
    }
    public Airline getAirlineByNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT a FROM Airline a WHERE LOWER(a.nickname) = :nickname", Airline.class)
                    .setParameter("nickname", nickname.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }
}
