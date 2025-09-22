package infra.repository.booking;

import domain.dtos.bookFlight.BookFlightDTO;
import domain.models.bookflight.BookFlight;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;
import infra.repository.IBaseRepository;

import java.util.List;

public interface IBookingRepository extends IBaseRepository<BookFlight> {
    void saveBookflightWithTicketsAndAddToSeats(BookFlight bookFlight, List<Ticket> savedTickets, List<Seat> seats, Customer customer);
    BookFlight getFullBookingById(Long id);
    //List<BookFlightDTO> findDTOsByCustomerNickname(String nickname);
    List<BookFlight> findByCustomerNickname(String nickname);
    List<BookFlight> findFullByFlightName(String flightName);
    List<BookFlight> findFullAll();
    List<BookFlight> findByFlightName(String flightName);
    List<BookFlight> findFullByCustomerNickname(String nickname);
}
