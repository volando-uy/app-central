package factory;

import controllers.booking.IBookingController;
import controllers.seat.ISeatController;
import domain.services.airport.AirportService;
import domain.services.airport.IAirportService;
import domain.services.auth.AuthService;
import domain.services.auth.IAuthService;
import domain.services.booking.BookingService;
import domain.services.booking.IBookingService;
import domain.services.buyPackage.BuyPackageService;
import domain.services.buyPackage.IBuyPackageService;
import domain.services.category.CategoryService;
import domain.services.category.ICategoryService;
import domain.services.city.CityService;
import domain.services.city.ICityService;
import domain.services.flight.FlightService;
import domain.services.flight.IFlightService;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.seat.ISeatService;
import domain.services.seat.SeatService;
import domain.services.ticket.ITicketService;
import domain.services.ticket.TicketService;
import domain.services.user.IUserService;
import domain.services.user.UserService;

public class ServiceFactory {
    private static IUserService userService;
    private static IFlightRouteService flightRouteService;
    private static IFlightService flightService;
    private static ICategoryService categoryService;
    private static ICityService cityService;
    private static IFlightRoutePackageService packageService;
    private static IAirportService airportService;
    private static ISeatService seatService;
    private static ITicketService ticketService;
    private static IBookingService bookingService;
    private static IBuyPackageService buyPackageService;
    private static IAuthService authService;
    // ############ USER SERVICE ###########

    // Metodo para obtener el servicio de usuario, inicializándolo si es necesario
    public static IUserService getUserService() {
        if (userService == null) {
            userService = new UserService();
            userService.setFlightRoutePackageService(getFlightRoutePackageService());
        }
        return userService;
    }

    // ##########################################


    // ############ FLIGHT ROUTE SERVICE ############

    // Metodo para obtener el servicio de rutas de vuelo, inicializándolo si es necesario
    public static IFlightRouteService getFlightRouteService() {
        if (flightRouteService == null) {
            flightRouteService = new FlightRouteService();
            flightRouteService.setCategoryService(getCategoryService());
            flightRouteService.setAirportService(getAirportService());
            flightRouteService.setUserService(getUserService());
        }
        return flightRouteService;
    }

    // ##########################################


    // ############ CATEGORY SERVICE ############

    public static ICategoryService getCategoryService() {
        if (categoryService == null) {
            categoryService = new CategoryService();
        }
        return categoryService;
    }

    // ##########################################


    // ############ FLIGHT SERVICE ############

    public static IFlightService getFlightService() {
        if (flightService == null) {
            flightService = new FlightService();
            flightService.setUserService(getUserService());
            flightService.setFlightRouteService(getFlightRouteService());
            flightService.setSeatService(getSeatService());
        }
        return flightService;
    }

    // ##########################################


    // ############ FLIGHT ROUT PACKAGE SERVICE ############

    public static IFlightRoutePackageService getFlightRoutePackageService() {
        if (packageService == null) {
            packageService = new FlightRoutePackageService();
            packageService.setFlightRouteService(getFlightRouteService());
        }
        return packageService;
    }

    // ##########################################


    // ############ CITY SERVICE ############

    public static ICityService getCityService() {
        if (cityService == null) {
            cityService = new CityService();
        }
        return cityService;
    }

    // ##########################################

    // ############ AIRPORT SERVICE ############
    public static IAirportService getAirportService() {
        if (airportService == null) {
            airportService = new AirportService();
            airportService.setCityService(getCityService());
        }
        return airportService;
    }

    // ############ SEAT SERVICE ############
    public static ISeatService getSeatService() {
        if (seatService == null) {
            seatService = new SeatService();
            seatService.setTicketService(getTicketService());
        }
        return seatService;
    }

    // ############ TICKET SERVICE ############
    public static ITicketService getTicketService() {
        if (ticketService == null) {
            ticketService = new TicketService();
        }
        return ticketService;
    }

    // ############ BOOKING SERVICE ############
    public static IBookingService getBookingService() {
        if (bookingService == null) {
            bookingService = new BookingService();
            bookingService.setFlightService(getFlightService());
            bookingService.setSeatService(getSeatService());
            bookingService.setTicketService(getTicketService());
            bookingService.setUserService(getUserService());
            bookingService.setBookingRepository(RepositoryFactory.getBookingRepository());
        }

        return bookingService;
    }

    // ############ BUYPACKAGE SERVICE ############
    public static IBuyPackageService getBuyPackageService() {
        if (buyPackageService == null) {
            buyPackageService = new BuyPackageService();
            buyPackageService.setUserService(getUserService());
            buyPackageService.setFlightRoutePackageService(getFlightRoutePackageService());
        }
        return buyPackageService;
    }

    // ############ AUTH SERVICE ############
    public static IAuthService getAuthService() {
        if (authService == null) {
            authService = new AuthService();
        }
        return authService;
    }

}
