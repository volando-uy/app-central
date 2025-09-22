package infra.repository.flightroutepackage;

import app.DBConnection;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.buypackage.BuyPackage;
import domain.models.flightRoute.FlightRoute;
import domain.models.flightRoutePackage.FlightRoutePackage;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FlightRoutePackageRepository  extends AbstractFlightRoutePackageRepository implements IFlightRoutePackageRepository {
    public FlightRoutePackageRepository(){
        super();
    }

    @Override
    public FlightRoutePackage getFlightRoutePackageByName(String flightRoutePackageName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            return em.createQuery("SELECT frp FROM FlightRoutePackage frp WHERE LOWER(frp.name)=:name", FlightRoutePackage.class)
                    .setParameter("name", flightRoutePackageName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public FlightRoutePackage getFullFlightRoutePackageByName(String flightRoutePackageName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            FlightRoutePackage frp = em.createQuery("SELECT frp FROM FlightRoutePackage frp LEFT JOIN FETCH frp.flightRoutes WHERE LOWER(frp.name)=:name", FlightRoutePackage.class)
                    .setParameter("name", flightRoutePackageName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Initialize the attributes
            if (frp != null) {
                if (frp.getFlightRoutes() != null)
                    frp.getFlightRoutes().size(); // Load flightRoutes
                if (frp.getBuyPackages() != null)
                    frp.getBuyPackages().size(); // Load buyPackages
            }

            return frp;
        }
    }

    @Override
    public boolean existsByName(String packageName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            Long count = em.createQuery("SELECT COUNT(frp) FROM FlightRoutePackage frp WHERE LOWER(frp.name)=:name", Long.class)
                    .setParameter("name", packageName.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }
//    @Override
//    public List<BuyPackage> findByCustomerNickname(String nickname) {
//        try (EntityManager em = DBConnection.getEntityManager()) {
//            return em.createQuery(
//                            "SELECT DISTINCT bp FROM BuyPackage bp " +
//                                    "LEFT JOIN FETCH bp.customer c " +
//                                    "LEFT JOIN FETCH bp.bookFlights bf " +
//                                    "LEFT JOIN FETCH bp.flightRoutePackage frp " +
//                                    "WHERE c.nickname = :nickname", BuyPackage.class)
//                    .setParameter("nickname", nickname)
//                    .getResultList();
//        }
//    }

    @Override
    public List<FlightRoutePackage> findAllWithFlightRoutes() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<FlightRoutePackage> packages = em.createQuery(
                            "SELECT DISTINCT frp FROM FlightRoutePackage frp WHERE frp.flightRoutes IS NOT EMPTY", FlightRoutePackage.class)
                    .getResultList();

            return packages;
        }
    }

    @Override
    public List<FlightRoutePackage> findAllFullWithFlightRoutes() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<FlightRoutePackage> packages = em.createQuery(
                            "SELECT DISTINCT frp FROM FlightRoutePackage frp WHERE frp.flightRoutes IS NOT EMPTY", FlightRoutePackage.class)
                    .getResultList();

            // Initialize flightRoutes for each package
            for (FlightRoutePackage frp : packages) {
                if (frp.getFlightRoutes() != null)
                    frp.getFlightRoutes().size(); // Load flightRoutes
                if (frp.getBuyPackages() != null)
                    frp.getBuyPackages().size(); // Load buyPackages
            }

            return packages;
        }
    }

    @Override
    public FlightRoutePackage getFlightRoutePackageFullByName(String flightRoutePackageName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            FlightRoutePackage frp = em.createQuery("SELECT frp FROM FlightRoutePackage frp WHERE LOWER(frp.name)=:name", FlightRoutePackage.class)
                    .setParameter("name", flightRoutePackageName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Initialize the attributes
            if (frp != null) {
                if (frp.getFlightRoutes() != null)
                    frp.getFlightRoutes().size(); // Load flightRoutes
                if (frp.getBuyPackages() != null)
                    frp.getBuyPackages().size(); // Load buyPackages
            }

            return frp;
        }
    }

    @Override
    public void addFlightRouteToPackage(FlightRoute flightRoute, FlightRoutePackage flightRoutePackage) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            em.getTransaction().begin();

            // Reattach entities to the current persistence context
            FlightRoute managedFlightRoute = em.merge(flightRoute);
            FlightRoutePackage managedFlightRoutePackage = em.merge(flightRoutePackage);

            // Add the flight route to the package
            managedFlightRoutePackage.getFlightRoutes().add(managedFlightRoute);

            // Add the package to the flight route
            managedFlightRoute.getInPackages().add(flightRoutePackage);

            em.merge(managedFlightRoutePackage);
            em.merge(managedFlightRoute);

            em.getTransaction().commit();
        }
    }
}
