package infra.repository.flightroutepackage;

import app.DBConnection;
import domain.dtos.flightRoutePackage.FlightRoutePackageDTO;
import domain.models.buypackage.BuyPackage;
import domain.models.flightRoutePackage.FlightRoutePackage;
import jakarta.persistence.EntityManager;

import java.util.List;

public class FlightRoutePackageRepository  extends AbstractFlightRoutePackageRepository implements IFlightRoutePackageRepository{
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
                frp.getFlightRoutes().size(); // Load flightRoutes
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
    public List<BuyPackage> findByCustomerNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT DISTINCT bp FROM BuyPackage bp " +
                                    "LEFT JOIN FETCH bp.customer c " +
                                    "LEFT JOIN FETCH bp.bookFlights bf " +
                                    "LEFT JOIN FETCH bp.flightRoutePackage frp " +
                                    "WHERE c.nickname = :nickname", BuyPackage.class)
                    .setParameter("nickname", nickname)
                    .getResultList();
        }
    }

    public List<FlightRoutePackage> findAllWithFlightRoutes() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<FlightRoutePackage> packages = em.createQuery(
                            "SELECT DISTINCT frp FROM FlightRoutePackage frp WHERE frp.flightRoutes IS NOT EMPTY", FlightRoutePackage.class)
                    .getResultList();

            return packages;
        }
    }

    public List<FlightRoutePackage> findAllFullWithFlightRoutes() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<FlightRoutePackage> packages = em.createQuery(
                            "SELECT DISTINCT frp FROM FlightRoutePackage frp WHERE frp.flightRoutes IS NOT EMPTY", FlightRoutePackage.class)
                    .getResultList();

            // Initialize flightRoutes for each package
            for (FlightRoutePackage frp : packages) {
                frp.getFlightRoutes().size(); // Load flightRoutes
                frp.getBuyPackages().size(); // Load buyPackages
            }

            return packages;
        }
    }

    public FlightRoutePackage getFlightRoutePackageFullByName(String flightRoutePackageName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            FlightRoutePackage frp = em.createQuery("SELECT frp FROM FlightRoutePackage frp WHERE LOWER(frp.name)=:name", FlightRoutePackage.class)
                    .setParameter("name", flightRoutePackageName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            // Initialize the attributes
            if (frp != null) {
                frp.getFlightRoutes().size(); // Load flightRoutes
                frp.getBuyPackages().size(); // Load buyPackages
            }

            return frp;
        }
    }
}
