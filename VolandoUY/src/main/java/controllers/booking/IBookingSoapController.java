package controllers.booking;

import app.adapters.dto.bookflight.SoapBaseBookFlightDTO;
import app.adapters.dto.ticket.TicketLuggageArray;
import domain.dtos.bookflight.BaseBookFlightDTO;

public interface IBookingSoapController extends IBaseBookingController{
    SoapBaseBookFlightDTO createBooking(SoapBaseBookFlightDTO bookingDTO, TicketLuggageArray ticketLuggages, String userNickname, String flightName);
}
