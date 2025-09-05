package domain.services.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.models.seat.Seat;
import domain.services.ticket.ITicketService;

import java.util.List;

public interface ISeatService {
    Seat createSeatWithoutPersistance(BaseSeatDTO baseSeatDTO);

    void assignTicketToSeat(Long seatId, Long ticketId);

    void setTicketService(ITicketService ticketService);

    List<Seat> getAllSeatsByFlightName(String flightName);

    Seat getSeatById(Long seatId);

    boolean seatExists(Long seatId);


    List<Seat> getLimitedAvailableSeatsByFlightName(String flightName, int size);
}
