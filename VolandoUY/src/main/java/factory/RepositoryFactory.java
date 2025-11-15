package factory;

import infra.repository.airport.AirportRepository;
import infra.repository.airport.IAirportRepository;
import infra.repository.booking.BookingRepository;
import infra.repository.booking.IBookingRepository;
import infra.repository.buypackage.BuyPackageRepository;
import infra.repository.buypackage.IBuyPackageRepository;
import infra.repository.category.CategoryRepository;
import infra.repository.category.ICategoryRepository;
import infra.repository.city.CityRepository;
import infra.repository.city.ICityRepository;
import infra.repository.flight.FlightRepository;
import infra.repository.flight.IFlightRepository;
import infra.repository.flightroute.FlightRouteRepository;
import infra.repository.flightroute.IFlightRouteRepository;
import infra.repository.flightroutepackage.FlightRoutePackageRepository;
import infra.repository.flightroutepackage.IFlightRoutePackageRepository;
import infra.repository.seat.ISeatRepository;
import infra.repository.seat.SeatRepository;
import infra.repository.ticket.ITicketRepository;
import infra.repository.ticket.TicketRepository;
import infra.repository.user.IUserRepository;
import infra.repository.user.UserRepository;
import infra.repository.user.follow.FollowRepository;
import infra.repository.user.follow.IFollowRepository;

public class RepositoryFactory {
    private static IUserRepository userRepository;
    private static ITicketRepository ticketRepository;
    private static ISeatRepository seatRepository;
    private static IFlightRoutePackageRepository flightRoutePackageRepository;
    private static IFlightRouteRepository flightRouteRepository;
    private static IFlightRepository flightRepository;
    private static ICityRepository cityRepository;
    private static ICategoryRepository categoryRepository;
    private static IBuyPackageRepository buyPackageRepository;
    private static IBookingRepository bookingRepository;
    private static IAirportRepository airportRepository;
    private static IFollowRepository followRepository;

    public static IUserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepository();
        }
        return userRepository;
    }

    public static ITicketRepository getTicketRepository() {
        if (ticketRepository == null) {
            ticketRepository = new TicketRepository();
        }
        return ticketRepository;
    }

    public static ISeatRepository getSeatRepository() {
        if (seatRepository == null) {
            seatRepository = new SeatRepository();
        }
        return seatRepository;
    }

    public static IFlightRoutePackageRepository getFlightRoutePackageRepository() {
        if (flightRoutePackageRepository == null) {
            flightRoutePackageRepository = new FlightRoutePackageRepository();
        }
        return flightRoutePackageRepository;
    }

    public static IFlightRouteRepository getFlightRouteRepository() {
        if (flightRouteRepository == null) {
            flightRouteRepository = new FlightRouteRepository();
        }
        return flightRouteRepository;
    }

    public static IFlightRepository getFlightRepository() {
        if (flightRepository == null) {
            flightRepository = new FlightRepository();
        }
        return flightRepository;
    }

    public static ICityRepository getCityRepository() {
        if (cityRepository == null) {
            cityRepository = new CityRepository();
        }
        return cityRepository;
    }

    public static ICategoryRepository getCategoryRepository() {
        if (categoryRepository == null) {
            categoryRepository = new CategoryRepository();
        }
        return categoryRepository;
    }
    public static IBuyPackageRepository getBuyPackageRepository() {
        if (buyPackageRepository == null) {
            buyPackageRepository = new BuyPackageRepository();
        }
        return buyPackageRepository;
    }
    public static IBookingRepository getBookingRepository(){
        if (bookingRepository == null) {
            bookingRepository = new BookingRepository();
        }
        return bookingRepository;
    }

    public static IAirportRepository getAirportRepository(){
        if (airportRepository == null) {
            airportRepository = new AirportRepository();
        }
        return airportRepository;
    }

    public static IFollowRepository getFollowRepository() {
        if (followRepository == null) {
            followRepository = new FollowRepository();
        }
        return followRepository;
    }
}
