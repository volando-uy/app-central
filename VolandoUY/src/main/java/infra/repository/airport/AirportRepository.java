package infra.repository.airport;

import app.DBConnection;
import domain.models.airport.Airport;
import jakarta.persistence.EntityManager;

public class AirportRepository extends AirportAbstractRepository implements IAirportRepository {
    public AirportRepository() {
        super();
    }

    @Override
    public Airport getAirportByCode(String code) {
        try(EntityManager em= DBConnection.getEntityManager()){
            return em.createQuery("SELECT a FROM Airport a WHERE LOWER(a.code)=:code", Airport.class)
                    .setParameter("code", code.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public boolean existsAirportByCode(String code) {
        try(EntityManager em= DBConnection.getEntityManager()){
            Long count = em.createQuery("SELECT COUNT(a) FROM Airport a WHERE LOWER(a.code)=:code", Long.class)
                    .setParameter("code", code.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }
}
