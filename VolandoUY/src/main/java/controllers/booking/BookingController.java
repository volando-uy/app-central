package controllers.booking;

import domain.dtos.bookflight.BaseBookFlightDTO;
import domain.dtos.bookflight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.services.booking.IBookingService;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public class BookingController implements IBookingController {
    private IBookingService bookingService;


    @Override
    public BaseBookFlightDTO createBooking(BaseBookFlightDTO bookingDTO, Map<BaseTicketDTO, List<LuggageDTO>> tickets, String userNickname, String flightName) {
        return bookingService.createBooking(bookingDTO, tickets, userNickname, flightName);
    }

    @Override
    public List<BookFlightDTO> getAllBookFlightsDetails() {
        return bookingService.getAllBookFlightsDetails(true);
    }

    @Override
    public List<BaseBookFlightDTO> getAllBookFlightsSimpleDetails() {
        return bookingService.getAllBookFlightsDetails(false)
                .stream()
                .map(bf -> (BaseBookFlightDTO) bf)
                .toList();
    }

    @Override
    public List<BookFlightDTO> getBookFlightsDetailsByCustomerNickname(String nickname) {
        return bookingService.getAllBookFlightsDetailsByCustomerNickname(nickname, true);
    }

    @Override
    public List<BaseBookFlightDTO> getBookFlightsSimpleDetailsByCustomerNickname(String nickname) {
        return bookingService.getAllBookFlightsDetailsByCustomerNickname(nickname, false)
                .stream()
                .map(bf -> (BaseBookFlightDTO) bf)
                .collect(Collectors.toList());
    }

    @Override
    public List<BookFlightDTO> getBookFlightsDetailsByFlightName(String flightName) {
        return bookingService.getBookFlightsDetailsByFlightName(flightName, true);
    }

    @Override
    public List<BaseBookFlightDTO> getBookFlightsSimpleDetailsByFlightName(String nickname) {
        return bookingService.getBookFlightsDetailsByFlightName(nickname, false)
                .stream()
                .map(bf -> (BaseBookFlightDTO) bf)
                .collect(Collectors.toList());
    }

    @Override
    public BookFlightDTO getBookFlightDetailsById(Long id) {
        return bookingService.getBookFlightDetailsById(id, true);
    }

    @Override
    public BaseBookFlightDTO getBookFlightSimpleDetailsById(Long id) {
        return bookingService.getBookFlightDetailsById(id, false);
    }

    @Override
    public BaseBookFlightDTO completeBooking(Long bookingId) {
        return bookingService.completeBooking(bookingId);
    }

    @Override
    public BaseBookFlightDTO cancelBooking(Long bookingId) {
        return bookingService.cancelBooking(bookingId);
    }

    @Override
    public String getCustomerNicknameByBookingId(Long bookingId) {
        return bookingService.getCustomerNicknameByBookingId(bookingId);
    }
}
