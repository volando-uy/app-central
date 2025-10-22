package factory;

import controllers.airport.AirportController;
import controllers.airport.IAirportController;
import controllers.auth.IAuthController;
import controllers.booking.BookingController;
import controllers.booking.IBookingController;
import controllers.buypackage.BuyPackageController;
import controllers.buypackage.IBuyPackageController;
import controllers.category.CategoryController;
import controllers.category.ICategoryController;
import controllers.city.CityController;
import controllers.city.ICityController;
import controllers.flight.FlightController;
import controllers.flight.IFlightController;
import controllers.flightroute.FlightRouteController;
import controllers.flightroute.IFlightRouteController;
import controllers.seat.ISeatController;
import controllers.seat.SeatController;
import controllers.ticket.ITicketController;
import controllers.ticket.TicketController;
import controllers.user.IUserController;
import controllers.user.UserController;
import controllers.flightroutepackage.FlightRoutePackageController;
import controllers.flightroutepackage.IFlightRoutePackageController;
import shared.utils.CustomModelMapper;


public class ControllerFactory {

    private static CustomModelMapper customModelMapper;

    private static IUserController userController;
    private static IFlightRouteController flightRouteController;
    private static IFlightController flightController;
    private static ICategoryController categoryController;
    private static ICityController cityController;
    private static IFlightRoutePackageController packageController;
    private static IAirportController airportController;
    private static ISeatController seatController;
    private static ITicketController ticketController;
    private static IBookingController bookingController;
    private static IBuyPackageController buyPackageController;
    private static IAuthController authController;

    // ############ MODEL MAPPER & CUSTOM MAPPERS ############

    public static CustomModelMapper getCustomModelMapper() {
        if (customModelMapper == null) {
            customModelMapper = new CustomModelMapper();
        }
        return customModelMapper;
    }


    // #############################################################


    // ############ USER CONTROLLER ############

    // Metodo para obtener el controlador de usuario, inicializándolo si es necesario
    public static IUserController getUserController() {
        if (userController == null) {
            userController = new UserController(ServiceFactory.getUserService());
        }
        return userController;
    }


    // #############################################################



    // ############ FLIGHT ROUTE CONTROLLER ############


    // Metodo para obtener el controlador de rutas de vuelo, inicializándolo si es necesario
    public static IFlightRouteController getFlightRouteController() {
        if (flightRouteController == null) {
            flightRouteController = new FlightRouteController(ServiceFactory.getFlightRouteService());
        }
        return flightRouteController;
    }


    // ############### CATEGORY CONTROLLER #################

    public static ICategoryController getCategoryController() {
        if (categoryController == null) {
            categoryController = new CategoryController(ServiceFactory.getCategoryService());
        }
        return categoryController;
    }

    // #############################################################



    // ############### FLIGHT CONTROLLER  #################

    public static IFlightController getFlightController() {
        if (flightController == null) {
            flightController = new FlightController(ServiceFactory.getFlightService());
        }
        return flightController;
    }

    // ##########################################


    // ############### FLIGHT ROUTE PACKAGES CONTROLLER #################


    public static IFlightRoutePackageController getFlightRoutePackageController() {
        if (packageController == null) {
            packageController = new FlightRoutePackageController(ServiceFactory.getFlightRoutePackageService());
        }
        return packageController;
    }

    // ##########################################


    // ############### CITY CONTROLLER  #################

    public static ICityController getCityController() {
        if (cityController == null) {
            cityController = new CityController(ServiceFactory.getCityService());
        }
        return cityController;
    }

    // ############### AIRPORT CONTROLLER  #################

    public static IAirportController getAirportController(){
        if (airportController == null){
            airportController = new AirportController(ServiceFactory.getAirportService());
        }
        return airportController;
    }

    // ############### SEAT CONTROLLER  #################

    public static ISeatController getSeatController(){
        if (seatController == null){
            seatController = new SeatController(ServiceFactory.getSeatService());
        }
        return seatController;
    }

    // ############### TICKET CONTROLLER  #################

    public static ITicketController getTicketController(){
        if (ticketController == null){
            ticketController = new TicketController(ServiceFactory.getTicketService());
        }
        return ticketController;
    }
    // ############### BOOKING CONTROLLER  #################
    public static IBookingController getBookingController(){
        if (bookingController == null){
            bookingController = new BookingController(ServiceFactory.getBookingService());
        }
        return bookingController;

    }

    public static IBuyPackageController getBuyPackageController() {
        if (buyPackageController == null) {
            buyPackageController = new BuyPackageController(ServiceFactory.getBuyPackageService());
        }
        return buyPackageController;
    }

    public static IAuthController getAuthController() {
        if (authController == null) {
            authController = new controllers.auth.AuthController();
        }
        return authController;
    }
}
