package infra.repository.booking;

import app.DBConnection;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.models.bookflight.BookFlight;
import domain.models.luggage.BasicLuggage;
import domain.models.seat.Seat;
import domain.models.ticket.Ticket;
import domain.models.user.Customer;
import jakarta.persistence.*;

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

            if (bookFlight.getCreated_at() == null) {
                bookFlight.setCreated_at(java.time.LocalDateTime.now());
            }

            // Seguridad: que la colección no sea null
            if (bookFlight.getTickets() == null) {
                bookFlight.setTickets(new java.util.ArrayList<>());
            }

            // Reatachar el customer y setear relaciones
            Customer managedCustomer = em.getReference(Customer.class, customer.getNickname());
            bookFlight.setCustomer(managedCustomer);

            // Persistir la reserva
            em.persist(bookFlight);

            managedCustomer.getBookedFlights().add(bookFlight);
            em.merge(managedCustomer);

            // Persistir tickets y relaciones seat/luggage ...
            for (int i = 0; i < savedTickets.size(); i++) {
                Ticket ticket = savedTickets.get(i);
                Seat detachedSeat = seats.get(i);

                Seat managedSeat = em.getReference(Seat.class, detachedSeat.getId());

                ticket.setSeat(managedSeat);
                managedSeat.setTicket(ticket);

                ticket.setBookFlight(bookFlight);
                bookFlight.getTickets().add(ticket);

                if (ticket.getBasicLuggages() != null) {
                    for (domain.models.luggage.BasicLuggage b : ticket.getBasicLuggages()) {
                        b.setTicket(ticket);
                        if (b.getId() == null && !em.contains(b)) em.persist(b);
                    }
                }
                if (ticket.getExtraLuggages() != null) {
                    for (domain.models.luggage.ExtraLuggage e : ticket.getExtraLuggages()) {
                        e.setTicket(ticket);
                        if (e.getId() == null && !em.contains(e)) em.persist(e);
                    }
                }

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

    @Override
    public List<BookFlightDTO> findDTOsByCustomerNickname(String nickname) {
        EntityManager em = DBConnection.getEntityManager();
        try {
            // Si TENÉS un constructor projection en BookFlightDTO(id, totalPrice, createdAt)
            // descomenta esta versión:
        /*
        return em.createQuery(
                "SELECT new domain.dtos.bookFlight.BookFlightDTO(" +
                "  bf.id, bf.totalPrice, bf.created_at" +
                ") " +
                "FROM BookFlight bf " +
                "WHERE bf.customer.nickname = :nick " +
                "ORDER BY bf.created_at DESC",
                BookFlightDTO.class
        )
        .setParameter("nick", nickname)
        .getResultList();
        */

            // Versión segura (sin depender de constructor en el DTO):
            List<BookFlight> list = em.createQuery(
                            "SELECT bf FROM BookFlight bf " +
                                    "WHERE bf.customer.nickname = :nick " +
                                    "ORDER BY bf.created_at DESC",
                            BookFlight.class
                    )
                    .setParameter("nick", nickname)
                    .getResultList();

            List<BookFlightDTO> out = new java.util.ArrayList<>();
            for (BookFlight bf : list) {
                BookFlightDTO dto = new BookFlightDTO();
                dto.setId(bf.getId());
                dto.setTotalPrice(bf.getTotalPrice());
                dto.setCreatedAt(bf.getCreated_at());
                out.add(dto);
            }
            return out;
        } finally {
            em.close();
        }
    }

    @Override
    public BookFlight getFullBookingById(Long id) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            try {
                BookFlight bf = em.createQuery(
                                "SELECT DISTINCT bf FROM BookFlight bf " +
                                        "LEFT JOIN FETCH bf.tickets t " +
                                        "LEFT JOIN FETCH t.seat s " +
                                        "LEFT JOIN FETCH s.flight f " +
                                        "LEFT JOIN FETCH f.flightRoute fr " +
                                        "LEFT JOIN FETCH bf.customer c " +
                                        "WHERE bf.id = :id", BookFlight.class)
                        .setParameter("id", id)
                        .getSingleResult();

                // Asegurar inicialización de colecciones (redundante por JOIN FETCH pero seguro)
                if (bf != null && bf.getTickets() != null) bf.getTickets().size();

                return bf;
            } catch (NoResultException ex) {
                return null;
            }
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