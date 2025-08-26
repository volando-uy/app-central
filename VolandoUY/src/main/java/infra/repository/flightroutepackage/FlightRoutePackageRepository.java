package infra.repository.flightroutepackage;

import app.DBConnection;
import domain.models.flightRoutePackage.FlightRoutePackage;
import jakarta.persistence.EntityManager;

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
    public boolean existsByName(String packageName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            Long count = em.createQuery("SELECT COUNT(frp) FROM FlightRoutePackage frp WHERE LOWER(frp.name)=:name", Long.class)
                    .setParameter("name", packageName.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }
}
