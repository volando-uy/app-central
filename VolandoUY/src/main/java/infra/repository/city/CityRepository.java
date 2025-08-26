package infra.repository.city;

import app.DBConnection;
import domain.models.city.City;
import jakarta.persistence.EntityManager;

public class CityRepository extends AbstractCityRepository implements ICityRepository {
    public CityRepository() {
        super();
    }

    @Override
    public City getCityByName(String name) {
        try(EntityManager em = DBConnection.getEntityManager()){
            em.getTransaction().begin();
            City city = em.createQuery("SELECT c FROM City c WHERE LOWER(c.name) = :name", City.class)
                    .setParameter("name", name.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
            em.getTransaction().commit();
            return city;
        }
    }

    @Override
    public boolean existsByName(String name) {
        try(EntityManager em = DBConnection.getEntityManager()){
            em.getTransaction().begin();
            Long count = em.createQuery("SELECT COUNT(c) FROM City c WHERE LOWER(c.name) = :name", Long.class)
                    .setParameter("name", name.toLowerCase())
                    .getSingleResult();
            em.getTransaction().commit();
            return count > 0;
        }
    }

    @Override
    public boolean existsAirportInCity(String cityName, String airpotyName) {
        try(EntityManager em = DBConnection.getEntityManager()){
            em.getTransaction().begin();
            Long count = em.createQuery("SELECT COUNT(a) FROM Airport a WHERE LOWER(a.city.name) = :cityName AND LOWER(a.name) = :airportName", Long.class)
                    .setParameter("cityName", cityName.toLowerCase())
                    .setParameter("airportName", airpotyName.toLowerCase())
                    .getSingleResult();
            em.getTransaction().commit();
            return count > 0;
        }
    }


}
