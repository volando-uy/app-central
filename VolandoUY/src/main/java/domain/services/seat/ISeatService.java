package domain.services.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.models.seat.Seat;
import domain.services.ticket.ITicketService;

public interface ISeatService {
    Seat createSeatWithoutPersistance(BaseSeatDTO baseSeatDTO);

    void assignTicketToSeat(Long seatId, Long ticketId);

    void setTicketService(ITicketService ticketService);
}
