package infra.repository.airport;

import app.DBConnection;
import domain.models.airport.Airport;
import domain.models.city.City;
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

    public Airport getFullAirportByCode(String code) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Airport airport = em.createQuery("SELECT a FROM Airport a WHERE LOWER(a.code) = :code", Airport.class)
                    .setParameter("code", code.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Initialize city relationship
            if (airport != null && airport.getCity() != null) {
                airport.getCity().getName(); // Access a property to initialize the relationship
            }
            return airport;
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

    public void saveAirportAndAddToCity(Airport airport, City city) {
        if (city == null) {
            throw new IllegalArgumentException("No se puede agregar un aeropuerto a una ciudad nula.");
        }
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            city = em.merge(city);
            airport.setCity(city);
            em.persist(airport);
            city.getAirports().add(airport);
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }



}
