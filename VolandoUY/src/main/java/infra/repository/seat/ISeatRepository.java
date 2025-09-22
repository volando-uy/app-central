package infra.repository.seat;

import domain.models.enums.EnumTipoAsiento;
import domain.models.seat.Seat;
import infra.repository.IBaseRepository;

import java.util.List;

public interface ISeatRepository extends IBaseRepository<Seat> {
    Seat getSeatById(Long seatId);
    List<Seat> getAllSeatsInFlight(Long flightId);
    List<Seat> getAllSeatsByFlightName(String flightName);

    List<Seat> getLimitedAvailableSeatsByFlightNameAndSeatType(String flightName, int size, EnumTipoAsiento seatType);
    Seat getFullSeatById(Long id);
    Seat getFullSeatByTicketId(Long ticketId);
    Seat getSeatByTicketId(Long ticketId);
}
