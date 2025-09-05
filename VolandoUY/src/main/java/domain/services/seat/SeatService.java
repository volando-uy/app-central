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

import java.util.List;

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
    public Seat createSeatWithoutPersistance(BaseSeatDTO baseSeatDTO) {
        Seat seat = customModelMapper.map(baseSeatDTO, Seat.class);
        seat.setTicket(null);
        seat.setFlight(null);
        return seat;
    }

    @Override
    public void assignTicketToSeat(Long seatId, Long ticketId) {

    }

    @Override
    public Seat getSeatById(Long seatId) {
        return seatRepository.findByKey(seatId);
    }

    @Override
    public boolean seatExists(Long seatId) {
        return seatRepository.findByKey(seatId) != null;
    }

    @Override
    public List<Seat> getAllSeatsByFlightName(String flightName) {
        return seatRepository.getAllSeatsByFlightName(flightName);
    }

    @Override
    public List<Seat> getLimitedAvailableSeatsByFlightName(String flightName, int size) {
        return seatRepository.getLimitedAvailableSeatsByFlightName(flightName, size);
    }
}
