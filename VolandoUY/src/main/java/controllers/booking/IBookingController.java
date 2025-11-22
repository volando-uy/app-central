package controllers.booking;

import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;

import java.util.List;
import java.util.Map;

public interface IBookingController extends IBaseBookingController {
    BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO, Map<BaseTicketDTO, List<LuggageDTO>> tickets, String userNickname, String flightName);
}
