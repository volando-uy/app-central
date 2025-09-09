package infra.repository.booking;

import domain.models.bookflight.BookFlight;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;

import java.util.List;

public interface IBookingRepository {
    void saveBookflightWithTicketsAndAddToSeats(BookFlight bookFlight, List<Ticket> savedTickets, List<Seat> seats, Customer customer);

}
