package infra.repository.seat;

import app.DBConnection;
import domain.models.flight.Flight;
import domain.models.seat.Seat;
import jakarta.persistence.EntityManager;

import java.util.List;

public class SeatRepository extends AbstractSeatRepository implements ISeatRepository {
    public SeatRepository() {
        super();
    }

    @Override
    public Seat getSeatById(Long seatId) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.find(Seat.class, seatId);
        }
    }

    @Override
    public List<Seat> getAllSeatsInFlight(Long flightId) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM Seat s JOIN s.flight f WHERE f.id = :fid", Seat.class)
                    .setParameter("fid", flightId)
                    .getResultList();
        }
    }

    @Override
    public List<Seat> getAllSeatsByFlightName(String flightName) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM Seat s JOIN s.flight f WHERE f.name = :name AND s.isAvailable = true", Seat.class)
                    .setParameter("name", flightName)
                    .getResultList();
        }
    }

    @Override
    public List<Seat> getLimitedAvailableSeatsByFlightName(String flightName, int size) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM Seat s JOIN s.flight f WHERE f.name = :name AND s.isAvailable = true", Seat.class)
                    .setParameter("name", flightName)
                    .setMaxResults(size)
                    .getResultList();
        }
    }
}
