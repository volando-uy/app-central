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
import infra.repository.booking.IBookingRepository;

import java.util.List;
import java.util.Map;

public interface IBookingService {

    BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO, Map<BaseTicketDTO, List<LuggageDTO>> tickets, String userNickname, String flightName);
    List<BookFlightDTO> getAllBookFlightsDetailsByCustomerNickname(String nickname, boolean full);

    List<BookFlightDTO> getAllBookFlightsDetails(boolean full);
    List<BookFlightDTO> getBookFlightsDetailsByFlightName(String flightName, boolean full);

    BookFlightDTO getBookFlightDetailsById(Long id, boolean full);

    void setSeatService(ISeatService seatService);
    void setFlightService(IFlightService flightService);
    void setTicketService(ITicketService ticketService);
    void setUserService(IUserService userService);
    void setBookingRepository(IBookingRepository bookingRepository);
}
