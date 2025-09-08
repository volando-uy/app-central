package infra.repository.booking;

import app.DBConnection;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.models.bookflight.BookFlight;
import domain.models.luggage.BasicLuggage;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class BookingRepository extends AbstractBookingRepository implements IBookingRepository {
    public BookingRepository() {
        super();
    }

    @Override
    public void saveBookflightWithTicketsAndAddToSeats(BookFlight bookFlight,
                                                       List<Ticket> savedTickets,
                                                       List<Seat> seats, Customer customer) {
        EntityManager em = DBConnection.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            // Seguridad: que la colección no sea null
            if (bookFlight.getTickets() == null) {
                bookFlight.setTickets(new ArrayList<>());
            }

            // 4) Reatachar el customer y setear relaciones
            Customer managedCustomer = em.getReference(Customer.class, customer.getNickname());
            bookFlight.setCustomer(managedCustomer);

            // 1) Persistir la reserva (nueva)
            em.persist(bookFlight);

            customer.getBookedFlights().add(bookFlight);

            em.merge(customer);

            // 2) Por cada ticket, reatachar el seat y setear relaciones
            for (int i = 0; i < savedTickets.size(); i++) {
                Ticket ticket = savedTickets.get(i);
                Seat detachedSeat = seats.get(i);

                // Reattach Seat en ESTE EntityManager (no persist seats existentes)
                Seat managedSeat = em.getReference(Seat.class, detachedSeat.getId());

                // Relaciones bidireccionales
                ticket.setSeat(managedSeat);
                managedSeat.setTicket(ticket);

                ticket.setBookFlight(bookFlight);
                bookFlight.getTickets().add(ticket);

                // Si NO tenés cascade=ALL en Ticket -> Luggage, persisto manualmente
                // y aseguro el back-reference
                if (ticket.getBasicLuggages() != null) {
                    for (domain.models.luggage.BasicLuggage b : ticket.getBasicLuggages()) {
                        b.setTicket(ticket);
                        if (b.getId() == null && !em.contains(b)) {
                            em.persist(b);
                        }
                    }
                }
                if (ticket.getExtraLuggages() != null) {
                    for (domain.models.luggage.ExtraLuggage e : ticket.getExtraLuggages()) {
                        e.setTicket(ticket);
                        if (e.getId() == null && !em.contains(e)) {
                            em.persist(e);
                        }
                    }
                }

                // 3) Persistir el ticket (nuevo)
                em.persist(ticket);
            }


            tx.commit();
        } catch (RuntimeException e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<BookFlight> findFullAll() {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<BookFlight> bf = em.createQuery("SELECT DISTINCT bf FROM BookFlight bf " +
                    "LEFT JOIN FETCH bf.tickets t " +
                    "LEFT JOIN FETCH bf.customer", BookFlight.class).getResultList();
            for (BookFlight bf1 : bf) {
                if (bf1.getTickets() != null) {
                    for (Ticket t : bf1.getTickets()) {
                        if (t.getSeat() != null) {
                            t.getSeat().isAvailable();
                        }
                        if (t.getBasicLuggages() != null) {
                            t.getBasicLuggages().size();
                        }
                        if (t.getExtraLuggages() != null) {
                            t.getExtraLuggages().size();
                        }

                    }
                }
                if (bf1.getCustomer() != null) {
                    bf1.getCustomer().getNickname();
                }
            }
            return bf;

        }
    }
    public List<BookFlightDTO> findDTOsByCustomerNickname(String nickname) {
        // OJO: ajustá los nombres de campos/relaciones según tu entidad BookFlight
        // - b.createdAt  -> el nombre real en la ENTIDAD (no en el DTO)
        // - b.customer.nickname -> la relación/columna real hacia Customer
        // Si tu campo en la entidad se llama "created_at" o "creationDate", cambialo en la JPQL.
        String jpql =
                "select new domain.dtos.bookFlight.BookFlightDTO(" +
                        "       b.id, b.created_at, b.totalPrice) " +
                        "from BookFlight b " +
                        "join b.customer c " +
                        "where c.nickname = :nick " +
                        "order by b.created_at desc";

        try (EntityManager em = DBConnection.getEntityManager()) {
            TypedQuery<BookFlightDTO> q = em.createQuery(jpql, BookFlightDTO.class);
            q.setParameter("nick", nickname);
            return q.getResultList();
        }
    }
}

/**
 * EntityManager em = DBConnection.getEntityManager();
 * try {
 * em.getTransaction().begin();
 * <p>
 * // Persist seats first
 * for (Seat seat : seats) {
 * em.persist(seat);
 * }
 * <p>
 * Airline managedAirline = em.merge(airline); // Attach airline to session
 * FlightRoute managedFlightRoute = em.merge(flightRoute); // Attach flightRoute to session
 * flight.setAirline(managedAirline); // Set the relationship
 * flight.setFlightRoute(managedFlightRoute); // Set the relationship
 * flight.setSeats(seats);
 * <p>
 * em.persist(flight); // Persist the flight
 * <p>
 * managedAirline.getFlights().add(flight); // Update the airline's collection
 * em.merge(managedAirline);
 * <p>
 * managedFlightRoute.getFlights().add(flight); // Update the flightRoute's collection
 * em.merge(managedFlightRoute);
 * <p>
 * seats.forEach(seat -> {
 * seat.setFlight(flight); // Set the relationship
 * em.merge(seat); // Update the seat
 * });
 * em.getTransaction().commit();
 * } catch (Exception e) {
 * em.getTransaction().rollback();
 * throw e;
 * } finally {
 * em.close();
 */