package controllers.booking;

import app.adapters.dto.bookflight.SoapBaseBookFlightDTO;
import app.adapters.dto.bookflight.SoapBookFlightDTO;
import app.adapters.dto.ticket.TicketLuggageArray;
import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.bookflight.BookFlightDTO;

import java.util.List;

public interface IBookingSoapController extends IBaseBookingController{
    SoapBaseBookFlightDTO createBooking(SoapBaseBookFlightDTO bookingDTO, TicketLuggageArray ticketLuggages, String userNickname, String flightName);
    List<SoapBookFlightDTO> getAllBookFlightsDetails();
    List<SoapBaseBookFlightDTO> getAllBookFlightsSimpleDetails();

    List<SoapBookFlightDTO> getBookFlightsDetailsByCustomerNickname(String nickname);
    List<SoapBaseBookFlightDTO> getBookFlightsSimpleDetailsByCustomerNickname(String nickname);

    List<SoapBookFlightDTO> getBookFlightsDetailsByFlightName(String flightName);
    List<SoapBaseBookFlightDTO> getBookFlightsSimpleDetailsByFlightName(String nickname);

    SoapBookFlightDTO getBookFlightDetailsById(Long id);

    SoapBaseBookFlightDTO completeBooking(Long bookingId, String token);
    SoapBaseBookFlightDTO cancelBooking(Long bookingId);

}
