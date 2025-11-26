package controllers.booking;

import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.bookflight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;

import java.util.List;
import java.util.Map;

public interface IBookingController extends IBaseBookingController {
    BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO, Map<BaseTicketDTO, List<LuggageDTO>> tickets, String userNickname, String flightName);
    List<BookFlightDTO> getAllBookFlightsDetails();
    List<BaseBookFlightDTO> getAllBookFlightsSimpleDetails();

    List<BookFlightDTO> getBookFlightsDetailsByCustomerNickname(String nickname);
    List<BaseBookFlightDTO> getBookFlightsSimpleDetailsByCustomerNickname(String nickname);

    List<BookFlightDTO> getBookFlightsDetailsByFlightName(String flightName);
    List<BaseBookFlightDTO> getBookFlightsSimpleDetailsByFlightName(String nickname);

    BookFlightDTO getBookFlightDetailsById(Long id);
    BaseBookFlightDTO getBookFlightSimpleDetailsById(Long id);

    BaseBookFlightDTO completeBooking(Long bookingId);
    BaseBookFlightDTO cancelBooking(Long bookingId);


    String getCustomerNicknameByBookingId(Long bookingId);
}
