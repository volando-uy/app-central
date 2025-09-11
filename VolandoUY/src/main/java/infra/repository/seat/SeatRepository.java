package infra.repository.seat;

import app.DBConnection;
import domain.models.enums.EnumTipoAsiento;
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
    public List<Seat> getLimitedAvailableSeatsByFlightNameAndSeatType(String flightName, int size, EnumTipoAsiento seatType) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT s FROM Seat s JOIN s.flight f WHERE f.name = :name AND s.isAvailable = true AND s.type = :seatType", Seat.class)
                    .setParameter("name", flightName)
                    .setParameter("seatType", seatType)
                    .setMaxResults(size)
                    .getResultList();
        }
    }

    public Seat getFullSeatById(Long id) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Seat seat = em.createQuery(
                            "SELECT s FROM Seat s " +
                                    "LEFT JOIN FETCH s.flight f " +
                                    "LEFT JOIN FETCH s.ticket t " +
                                    "WHERE s.id = :id", Seat.class)
                    .setParameter("id", id)
                    .getSingleResult();

            return seat;
        }
    }


    public Seat getFullSeatByTicketId(Long ticketId) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Seat seat = em.createQuery(
                            "SELECT s FROM Seat s " +
                                    "LEFT JOIN FETCH s.flight f " +
                                    "LEFT JOIN FETCH s.ticket t " +
                                    "WHERE t.id = :ticketId", Seat.class)
                    .setParameter("ticketId", ticketId)
                    .getSingleResult();

            return seat;
        }
    }

    public Seat getSeatByTicketId(Long ticketId) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            Seat seat = em.createQuery(
                            "SELECT s FROM Seat s " +
                                    "WHERE s.ticket.id = :ticketId", Seat.class)
                    .setParameter("ticketId", ticketId)
                    .getSingleResult();

            return seat;
        }
    }
}
