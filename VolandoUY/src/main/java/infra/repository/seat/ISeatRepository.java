package infra.repository.seat;

import domain.models.enums.EnumTipoAsiento;
import domain.models.seat.Seat;

import java.util.List;

public interface ISeatRepository {
    Seat getSeatById(Long seatId);
    List<Seat> getAllSeatsInFlight(Long flightId);
    List<Seat> getAllSeatsByFlightName(String flightName);

    List<Seat> getLimitedAvailableSeatsByFlightNameAndSeatType(String flightName, int size, EnumTipoAsiento seatType);
}
