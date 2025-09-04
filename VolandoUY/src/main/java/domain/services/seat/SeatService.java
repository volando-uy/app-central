package domain.services.seat;

import domain.dtos.seat.BaseSeatDTO;
import domain.models.flight.Flight;
import domain.models.seat.Seat;
import domain.services.flight.IFlightService;
import domain.services.ticket.ITicketService;
import factory.ControllerFactory;
import infra.repository.seat.SeatRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import shared.utils.CustomModelMapper;

public class SeatService implements ISeatService {

    private SeatRepository seatRepository;

    @Setter
    private ITicketService ticketService;

    @Setter
    private IFlightService flightService;

    private CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    public SeatService() {
        this.seatRepository = new SeatRepository();
    }

    @Override
    public BaseSeatDTO createSeat(BaseSeatDTO baseSeatDTO, String flightName) {
        return null;
    }

    @Override
    public void assignTicketToSeat(Long seatId, Long ticketId) {

    }
}
