package controllers.booking;

import domain.dtos.bookFlight.BaseBookFlightDTO;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.dtos.luggage.LuggageDTO;
import domain.dtos.ticket.BaseTicketDTO;
import domain.models.bookflight.BookFlight;
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
    public List<BookFlightDTO> findAllBookFlightDetails() {
        return bookingService.findAllBookFlightDetails(true);
    }

    @Override
    public List<BaseBookFlightDTO> findAllBookFlightSimpleDetails() {
        return bookingService.findAllBookFlightDetails(false).stream().map(bf -> (BaseBookFlightDTO) bf).collect(Collectors.toList());
    }

    @Override
    public List<BookFlightDTO> findDTOsByCustomerNickname(String nickname) {
        return bookingService.findDTOsByCustomerNickname(nickname);
    }

    @Override
    public BookFlight getFullBookingById(Long id) {
        return bookingService.getFullBookingById(id);
    }

}
