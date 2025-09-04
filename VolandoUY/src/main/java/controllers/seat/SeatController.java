package controllers.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.services.seat.ISeatService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class SeatController implements ISeatController {
    private ISeatService seatService;

    @Override
    public BaseSeatDTO createSeat(BaseSeatDTO baseSeatDTO, String flightName) {
        return seatService.createSeat(baseSeatDTO, flightName);
    }

    @Override
    public void assignTicketToSeat(Long seatId, Long ticketId) {
        seatService.assignTicketToSeat(seatId, ticketId);
    }
}
