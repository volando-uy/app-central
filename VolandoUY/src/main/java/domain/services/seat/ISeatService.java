package domain.services.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.services.ticket.ITicketService;

public interface ISeatService {
    BaseSeatDTO createSeat(BaseSeatDTO baseSeatDTO, String flightName);

    void assignTicketToSeat(Long seatId, Long ticketId);

    void setTicketService(ITicketService ticketService);
}
