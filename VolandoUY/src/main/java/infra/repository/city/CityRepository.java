package infra.repository.city;

import app.DBConnection;
import domain.dtos.city.BaseCityDTO;
import domain.models.city.City;
import jakarta.persistence.EntityManager;

import java.util.List;

public class CityRepository extends AbstractCityRepository implements ICityRepository {
    public CityRepository() {
        super();
    }

    @Override
    public City getCityByName(String name) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery("SELECT c FROM City c WHERE LOWER(c.name) = :name", City.class)
                    .setParameter("name", name.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public City getFullCityByName(String cityName) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            City city = em.createQuery("SELECT c FROM City c WHERE LOWER(c.name) = :name", City.class)
                    .setParameter("name", cityName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Initialize airports collection
            if (city != null) {
                city.getAirports().size(); // Initialize airports collection
            }
            return city;
        }
    }

    @Override
    public boolean existsByName(String name) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(c) FROM City c WHERE LOWER(c.name) = :name", Long.class)
                    .setParameter("name", name.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }

    @Override
    public boolean existsAirportInCity(String cityName, String airportName) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Long count = em.createQuery("SELECT COUNT(a) FROM Airport a WHERE LOWER(a.city.name) = :cityName AND LOWER(a.name) = :airportName", Long.class)
                    .setParameter("cityName", cityName.toLowerCase())
                    .setParameter("airportName", airportName.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }

    @Override
    public List<City> findAll() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<City> cities = em.createQuery("SELECT c FROM City c", City.class)
                    .getResultStream()
                    .toList();

            return cities;
        }
    }

    @Override
    public List<City> findFullAll() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<City> cities = em.createQuery("SELECT c FROM City c", City.class)
                    .getResultStream()
                    .toList();
            for (City city : cities)
                city.getAirports().size();
            return cities;
        }
    }
}