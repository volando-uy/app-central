package infra.repository.seat;

import domain.models.seat.Seat;

import java.util.List;

public interface ISeatRepository {
    Seat getSeatById(Long seatId);
    List<Seat> getAllSeatsInFlight(Long flightId);
}
