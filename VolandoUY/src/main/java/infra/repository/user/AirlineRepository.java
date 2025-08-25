package infra.repository.user;

import app.DBConnection;
import domain.models.user.Airline;
import domain.models.user.Customer;
import infra.repository.BaseRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class AirlineRepository extends AbstractUserRepository<Airline> {
    public AirlineRepository() {
        super(Airline.class);
    }

    @Override
    protected String getEntityName() {
        return this.getEntityClass().getSimpleName();
    }

    @Override
    protected Class<Airline> getEntityClass() {
        return Airline.class;
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
    public Airline getAirlineByEmail(String email) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT a FROM Airline a WHERE LOWER(a.mail) = :mail", Airline.class)
                    .setParameter("mail", email.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }
}
