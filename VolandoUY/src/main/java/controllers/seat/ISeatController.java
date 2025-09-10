package controllers.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;

public interface ISeatController {

    SeatDTO getSeatDetailsById(Long id);
    BaseSeatDTO getSeatSimpleDetailsById(Long id);

    BaseSeatDTO getSeatSimpleDetailsByTicketId(Long ticketId);
    SeatDTO getSeatDetailsByTicketId(Long ticketId);
}
