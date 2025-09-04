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
        try (EntityManager em = DBConnection.getEntityManager()){
            return em.find(Seat.class, seatId);
        }
    }

    @Override
    public List<Seat> getAllSeatsInFlight(Long flightId) {
        try (EntityManager em = DBConnection.getEntityManager()){
            Flight flight = em.find(Flight.class, flightId);
            if (flight != null) {
                return flight.getSeats();
            }
            return List.of();
        }
    }
}
