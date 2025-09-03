package infra.repository.flightroute;

import app.DBConnection;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FlightRouteRepository extends AbstractFlightRouteRepository implements IFlightRouteRepository {
    public FlightRouteRepository() {
        super(FlightRoute.class);
    }

    @Override
    public boolean existsByName(String name) {
        try (EntityManager entityManager = DBConnection.getEntityManager()) {
            Long count = entityManager.createQuery("SELECT COUNT(fr) FROM FlightRoute fr WHERE fr.name = :name", Long.class)
                    .setParameter("name", name)
                    .getSingleResult();
            return count > 0;
        }
    }

    @Override
    public List<FlightRoute> getAllByAirlineNickname(String airlineNickname) {
        try (EntityManager entityManager = DBConnection.getEntityManager()) {
            return entityManager.createQuery("SELECT fr FROM FlightRoute fr WHERE fr.airline.nickname = :nickname", FlightRoute.class)
                    .setParameter("nickname", airlineNickname)
                    .getResultList();
        }
    }

    @Override
    public List<FlightRoute> getFullAllByAirlineNickname(String airlineNickname) {
        try (EntityManager entityManager = DBConnection.getEntityManager()) {
            List<FlightRoute> flightRoutes = entityManager.createQuery("SELECT fr FROM FlightRoute fr LEFT JOIN FETCH fr.destinationCity LEFT JOIN FETCH fr.originCity LEFT JOIN FETCH fr.airline WHERE fr.airline.nickname = :nickname", FlightRoute.class)
                    .setParameter("nickname", airlineNickname)
                    .getResultList();

            for (FlightRoute fr : flightRoutes) {
                fr.getFlights().size();
                fr.getCategories().size(); // Initialize flights collection
            }

            return flightRoutes;
        }
    }

    @Override
    public FlightRoute getByName(String name) {
        try (EntityManager entityManager = DBConnection.getEntityManager()) {
            return entityManager.createQuery("SELECT fr FROM FlightRoute fr WHERE LOWER(fr.name) = LOWER(:name)", FlightRoute.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
    }

    public FlightRoute getFullByName(String name) {
        try (EntityManager entityManager = DBConnection.getEntityManager()) {
            FlightRoute flightRoute = entityManager.createQuery("SELECT fr FROM FlightRoute fr LEFT JOIN FETCH fr.destinationCity LEFT JOIN FETCH fr.originCity LEFT JOIN FETCH fr.airline WHERE LOWER(fr.name) = LOWER(:name)", FlightRoute.class)
                    .setParameter("name", name)
                    .getSingleResult();
            flightRoute.getFlights().size(); // Initialize flights collection
            flightRoute.getCategories().size(); // Initialize categories collection
            return flightRoute;
        }
    }

    @Override
    public void saveFlightRouteAndAddToAirline(FlightRoute flightRoute, Airline airline) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            Airline managedAirline = em.merge(airline); // Attach airline to session
            flightRoute.setAirline(managedAirline);
            em.persist(flightRoute);
            managedAirline.getFlightRoutes().add(flightRoute); // Safe: session is open
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
