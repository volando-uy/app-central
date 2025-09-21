package infra.repository.user;

import app.DBConnection;
import domain.models.user.Airline;
import jakarta.persistence.EntityManager;
import java.util.List;

public class AirlineRepository extends AbstractUserRepository<Airline> implements IAirlineRepository {

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

    @Override
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

    @Override
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

    @Override
    public List<Airline> findFullAll() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<Airline> airlines = em.createQuery(
                            "SELECT a FROM Airline a", Airline.class)
                    .getResultList();

            // Evitar lazy loading posterior
            for (Airline a : airlines) {
                a.getFlights().size();
                a.getFlightRoutes().size();
            }

            return airlines;
        }
    }

    @Override
    public Airline findFullByNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Airline airline = em.createQuery(
                            "SELECT a FROM Airline a WHERE LOWER(a.nickname) = :nickname", Airline.class)
                    .setParameter("nickname", nickname.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if (airline != null) {
                airline.getFlights().size();
                airline.getFlightRoutes().size();
            }

            return airline;
        }
    }
}
