package infra.repository.seat;

import domain.models.seat.Seat;

import java.util.List;

public interface ISeatRepository {
    Seat getSeatById(Long seatId);
    List<Seat> getAllSeatsInFlight(Long flightId);
    List<Seat> getAllSeatsByFlightName(String flightName);

    List<Seat> getLimitedAvailableSeatsByFlightName(String flightName, int size);
}
