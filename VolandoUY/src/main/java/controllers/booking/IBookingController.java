package controllers.booking;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.dtos.ticket.TicketDTO;
import domain.models.bookflight.BookFlight;
import domain.models.ticket.Ticket;

import java.util.List;
import java.util.Map;

public interface IBookingController {
    BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO, Map<BaseTicketDTO,List<LuggageDTO>> tickets, String userNickname, String flightName);

    List<BookFlightDTO> findAllBookFlightDetails();
    List<BaseBookFlightDTO> findAllBookFlightSimpleDetails();

}
