package controllers.seat;

import domain.dtos.seat.BaseSeatDTO;

public interface ISeatController {

    BaseSeatDTO createSeat(BaseSeatDTO baseSeatDTO, String flightName);

    void assignTicketToSeat(Long seatId, Long ticketId);
}
