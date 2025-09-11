package infra.repository.flightroute;

import app.DBConnection;
import domain.models.category.Category;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
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
            List<FlightRoute> flightRoutes = entityManager.createQuery("SELECT fr FROM FlightRoute fr LEFT JOIN FETCH fr.airline WHERE fr.airline.nickname = :nickname", FlightRoute.class)
                    .setParameter("nickname", airlineNickname)
                    .getResultList();

            for (FlightRoute fr : flightRoutes) {
                if (fr.getFlights() != null)
                    fr.getFlights().size();
                if (fr.getCategories() != null)
                    fr.getCategories().size(); // Initialize flights collection
                if (fr.getInPackages() != null)
                    fr.getInPackages().size(); // Initialize packages collection
                if (fr.getOriginCity() != null)
                    fr.getOriginCity().getName(); // Initialize origin city
                if (fr.getDestinationCity() != null)
                    fr.getDestinationCity().getName(); // Initialize destination city
                if (fr.getAirline() != null)
                    fr.getAirline().getName(); // Initialize airline
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
            FlightRoute flightRoute = entityManager.createQuery("SELECT fr FROM FlightRoute fr WHERE LOWER(fr.name) = LOWER(:name)", FlightRoute.class)
                    .setParameter("name", name)
                    .getSingleResult();

            if (flightRoute != null) {
                if (flightRoute.getFlights() != null)
                    flightRoute.getFlights().size(); // Initialize flights collection
                if (flightRoute.getCategories() != null)
                    flightRoute.getCategories().size(); // Initialize categories collection
                if (flightRoute.getInPackages() != null)
                    flightRoute.getInPackages().size(); // Initialize packages collection
                if (flightRoute.getOriginCity() != null)
                    flightRoute.getOriginCity().getName(); // Initialize origin city
                if (flightRoute.getDestinationCity() != null)
                    flightRoute.getDestinationCity().getName(); // Initialize destination city
                if (flightRoute.getAirline() != null)
                    flightRoute.getAirline().getName(); // Initialize airline
            }

            return flightRoute;
        }
    }

    @Override
    public void createFlightRoute(FlightRoute flightRoute, Airline airline) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();

            Airline managedAirline = em.merge(airline); // Attach airline to session

            // Add the relationships of flightroute
            flightRoute.setAirline(managedAirline);
            em.persist(flightRoute);

            // Add the flightroute to the relationships
            managedAirline.getFlightRoutes().add(flightRoute);
            for (Category c : flightRoute.getCategories()) {
                Category managedCategory = em.merge(c);
                managedCategory.getFlightRoutes().add(flightRoute);
            }
            em.merge(managedAirline);

            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<FlightRoute> getFullAllByPackageName(String packageName) {
        try (EntityManager entityManager = DBConnection.getEntityManager()) {
            List<FlightRoute> flightRoutes = entityManager.createQuery("SELECT fr FROM FlightRoute fr JOIN fr.inPackages frp WHERE LOWER(frp.name) = LOWER(:packageName)", FlightRoute.class)
                    .setParameter("packageName", packageName)
                    .getResultList();

            for (FlightRoute flightRoute : flightRoutes) {
                if (flightRoute != null) {
                    if (flightRoute.getFlights() != null)
                        flightRoute.getFlights().size(); // Initialize flights collection
                    if (flightRoute.getCategories() != null)
                        flightRoute.getCategories().size(); // Initialize categories collection
                    if (flightRoute.getInPackages() != null)
                        flightRoute.getInPackages().size(); // Initialize packages collection
                    if (flightRoute.getOriginCity() != null)
                        flightRoute.getOriginCity().getName(); // Initialize origin city
                    if (flightRoute.getDestinationCity() != null)
                        flightRoute.getDestinationCity().getName(); // Initialize destination city
                    if (flightRoute.getAirline() != null)
                        flightRoute.getAirline().getName(); // Initialize airline
                }
            }

            return flightRoutes;
        }
    }

    public List<FlightRoute> getAllByPackageName(String packageName) {
        try (EntityManager entityManager = DBConnection.getEntityManager()) {
            return entityManager.createQuery("SELECT fr FROM FlightRoute fr JOIN fr.inPackages frp WHERE LOWER(frp.name) = LOWER(:packageName)", FlightRoute.class)
                    .setParameter("packageName", packageName)
                    .getResultList();
        }
    }
}
