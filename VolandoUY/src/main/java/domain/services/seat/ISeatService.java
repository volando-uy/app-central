package domain.services.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.models.enums.EnumTipoAsiento;
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


    List<Seat> getLimitedAvailableSeatsByFlightNameAndSeatType(String flightName, int size, EnumTipoAsiento seatType);

    SeatDTO getSeatDetailsById(Long id, boolean full);

    SeatDTO getSeatDetailsByTicketId(Long ticketId, boolean full);
}
