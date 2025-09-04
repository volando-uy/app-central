package infra.repository.flight;

import app.DBConnection;
import domain.models.flight.Flight;
import domain.models.user.Airline;
import jakarta.persistence.EntityManager;

import java.util.Arrays;
import java.util.List;

public class FlightRepository extends AbstractFlightRepository implements IFlightRepository {

    public FlightRepository() {
        super();
    }

    @Override
    public Flight getFlightByName(String flightName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            return em.createQuery("SELECT f FROM Flight f WHERE LOWER(f.name)=:name", Flight.class)
                    .setParameter("name", flightName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    public Flight getFullFlightByName(String flightName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            Flight flight = em.createQuery("SELECT f FROM Flight f WHERE LOWER(f.name)=:name", Flight.class)
                    .setParameter("name", flightName.toLowerCase())
                    .getResultStream()
                    .findFirst()
                    .orElse(null);

            if(flight != null){
                if(flight.getAirline() != null) {flight.getAirline().getName();}
                if(flight.getFlightRoute() != null) {flight.getFlightRoute().getName();}
                if(flight.getSeats() != null) {flight.getSeats().size();}
            }
            return flight;
        }
    }

    @Override
    public List<Flight> getAllByAirlineNickname(String airlineNickname) {
        try(EntityManager em= DBConnection.getEntityManager()){
            return em.createQuery("SELECT f FROM Flight f WHERE LOWER(f.airline.nickname)=:nickname", Flight.class)
                    .setParameter("nickname", airlineNickname.toLowerCase())
                    .getResultList();
        }
    }

    @Override
    public boolean existsByName(String name) {
        try(EntityManager em= DBConnection.getEntityManager()){
            Long count = em.createQuery("SELECT COUNT(f) FROM Flight f WHERE LOWER(f.name)=:name", Long.class)
                    .setParameter("name", name.toLowerCase())
                    .getSingleResult();
            return count > 0;
        }
    }

    public List<Flight> getFlightsByRouteName(String routeName) {
        try(EntityManager em= DBConnection.getEntityManager()){
            return em.createQuery("SELECT f FROM Flight f WHERE LOWER(f.flightRoute.name)=:name", Flight.class)
                    .setParameter("name", routeName.toLowerCase())
                    .getResultList();
        }
    }

    public void saveFlightAndAddToAirline(Flight flight, Airline airline) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            em.getTransaction().begin();
            Airline managedAirline = em.merge(airline); // Attach airline to session
            flight.setAirline(managedAirline); // Set the relationship
            em.persist(flight); // Persist the flight
            managedAirline.getFlights().add(flight); // Update the airline's collection
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }


}
