package infra.repository.booking;

import app.DBConnection;
import domain.dtos.bookFlight.BookFlightDTO;
import domain.models.bookflight.BookFlight;
import domain.models.luggage.BasicLuggage;
import domain.models.luggage.ExtraLuggage;
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
                Seat managedSeat = em.merge(seats.get(i));

                ticket.setSeat(managedSeat);

                ticket.setBookFlight(bookFlight);

                em.persist(ticket);

                managedSeat.setTicket(ticket);
                managedSeat.setAvailable(false);
                em.merge(managedSeat);

                bookFlight.getTickets().add(ticket);
                em.merge(bookFlight);

                if (ticket.getBasicLuggages() != null) {
                    for (BasicLuggage b : ticket.getBasicLuggages()) {
                        b.setTicket(ticket);
                        if (b.getId() == null && !em.contains(b)) {
                            em.persist(b);
                        };
                    }
                }

                if (ticket.getExtraLuggages() != null) {
                    for (ExtraLuggage e : ticket.getExtraLuggages()) {
                        e.setTicket(ticket);
                        if (e.getId() == null && !em.contains(e)) {
                            em.persist(e);
                        }
                    }
                }

                em.merge(ticket);
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
                                    "ORDER BY bf.createdAt DESC",
                            BookFlight.class
                    )
                    .setParameter("nick", nickname)
                    .getResultList();

            List<BookFlightDTO> out = new java.util.ArrayList<>();
            for (BookFlight bf : list) {
                BookFlightDTO dto = new BookFlightDTO();
                dto.setId(bf.getId());
                dto.setTotalPrice(bf.getTotalPrice());
                dto.setCreatedAt(bf.getCreatedAt());
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
                                        "LEFT JOIN FETCH bf.customer c " +
                                        "LEFT JOIN FETCH bf.buyPackage bp " +
                                        "WHERE bf.id = :id", BookFlight.class)
                        .setParameter("id", id)
                        .getSingleResult();

                return bf;
            } catch (NoResultException ex) {
                return null;
            }
        }
    }
    public List<BookFlight> findByCustomerNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            return em.createQuery(
                            "SELECT DISTINCT bf FROM BookFlight bf JOIN FETCH bf.customer c WHERE c.nickname = :nickname", BookFlight.class)
                    .setParameter("nickname", nickname)
                    .getResultList();
        }
    }
    public List<BookFlight> findFullByFlightName(String flightName) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<BookFlight> bfList = em.createQuery(
                            "SELECT DISTINCT bf FROM BookFlight bf " +
                                    "LEFT JOIN FETCH bf.customer c " +
                                    "LEFT JOIN FETCH bf.tickets t " +
                                    "LEFT JOIN FETCH t.seat s " +
                                    "LEFT JOIN FETCH s.flight f " +        // join al vuelo
                                    "LEFT JOIN FETCH bf.buyPackage bp " +
                                    "WHERE f.name = :flightName", BookFlight.class)
                    .setParameter("flightName", flightName)
                    .getResultList();

            // Forzar inicialización
            for (BookFlight bf : bfList) {
                if (bf.getCustomer() != null)
                    bf.getCustomer().getNickname();
                if (bf.getTickets() != null)
                    bf.getTickets().size();
                if (bf.getBuyPackage() != null)
                    bf.getBuyPackage().getId();
            }

            return bfList;
        }
    }

    public List<BookFlight> findByFlightName(String flightName) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<BookFlight> bfList = em.createQuery(
                            "SELECT DISTINCT bf FROM BookFlight bf " +
                                    "LEFT JOIN FETCH bf.tickets t " +
                                    "LEFT JOIN FETCH t.seat s " +
                                    "LEFT JOIN FETCH s.flight f " +        // join al vuelo
                                    "WHERE f.name = :flightName", BookFlight.class)
                    .setParameter("flightName", flightName)
                    .getResultList();

            return bfList;
        }

    }

    public List<BookFlight> findFullByCustomerNickname(String nickname) {
        try (EntityManager em = DBConnection.getEntityManager()) {
            List<BookFlight> bfList = em.createQuery(
                            "SELECT DISTINCT bf FROM BookFlight bf LEFT JOIN FETCH bf.customer c WHERE c.nickname = :nickname", BookFlight.class)
                    .setParameter("nickname", nickname)
                    .getResultList();

            // Forzar inicialización
            for (BookFlight bf : bfList) {
                if (bf.getCustomer() != null)
                    bf.getCustomer().getNickname();
                if (bf.getTickets() != null)
                    bf.getTickets().size();
                if (bf.getBuyPackage() != null)
                    bf.getBuyPackage().getId();
            }

            return bfList;
        }
    }


}