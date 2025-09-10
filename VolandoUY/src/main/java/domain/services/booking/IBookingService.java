package domain.services.booking;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
import domain.services.flight.IFlightService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.seat.ISeatService;
import domain.services.ticket.ITicketService;
import domain.services.user.IUserService;

import java.util.List;
import java.util.Map;

public interface IBookingService {

    BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO, Map<BaseTicketDTO, List<LuggageDTO>> tickets, String userNickname, String flightName);
/**
 *     @Setter
 *     private ISeatService seatService;
 *     @Setter
 *     private IFlightService flightService;
 *     @Setter
 *     private ITicketService ticketService;
 */
    void setSeatService(ISeatService seatService);
    void setFlightService(IFlightService flightService);
    void setTicketService(ITicketService ticketService);
    void setUserService(IUserService userService);
    List<BookFlightDTO> findDTOsByCustomerNickname(String nickname);
    List<BookFlightDTO> findAllBookFlightDetails(boolean detailed);
    BookFlight getFullBookingById(Long id);

}
