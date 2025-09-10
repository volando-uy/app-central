package controllers.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.dtos.seat.SeatDTO;
import domain.services.seat.ISeatService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SeatController implements ISeatController {
    private ISeatService seatService;


    @Override
    public SeatDTO getSeatDetailsById(Long id) {
        return seatService.getSeatDetailsById(id, true);
    }

    @Override
    public BaseSeatDTO getSeatSimpleDetailsById(Long id) {
        return seatService.getSeatDetailsById(id, false);
    }

    @Override
    public BaseSeatDTO getSeatSimpleDetailsByTicketId(Long ticketId) {
        return seatService.getSeatDetailsByTicketId(ticketId, false);
    }

    @Override
    public SeatDTO getSeatDetailsByTicketId(Long ticketId) {
        return seatService.getSeatDetailsByTicketId(ticketId, true);

    }
}
