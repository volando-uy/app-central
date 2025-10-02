package factory;

import controllers.airport.AirportController;
import controllers.airport.IAirportController;
import controllers.booking.BookingController;
import controllers.booking.IBookingController;
import controllers.buyPackage.BuyPackageController;
import controllers.buyPackage.IBuyPackageController;
import controllers.category.CategoryController;
import controllers.category.ICategoryController;
import controllers.city.CityController;
import controllers.city.ICityController;
import controllers.flight.FlightController;
import controllers.flight.IFlightController;
import controllers.flightRoute.FlightRouteController;
import controllers.flightRoute.IFlightRouteController;
import controllers.seat.ISeatController;
import controllers.seat.SeatController;
import controllers.ticket.ITicketController;
import controllers.ticket.TicketController;
import controllers.user.IUserController;
import controllers.user.UserController;
import controllers.flightRoutePackage.FlightRoutePackageController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
import controllers.utils.IUtilsController;
import controllers.utils.UtilsController;
import domain.models.user.mapper.UserMapper;
import domain.services.category.CategoryService;
import domain.services.category.ICategoryService;
import domain.services.city.CityService;
import domain.services.city.ICityService;
import domain.services.flight.FlightService;
import domain.services.flight.IFlightService;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.flightRoutePackage.IFlightRoutePackageService;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import domain.services.flightRoutePackage.FlightRoutePackageService;
import org.hibernate.collection.spi.PersistentBag;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import shared.utils.CustomModelMapper;
import shared.utils.ImageProcessor;

import java.security.PrivateKey;
import java.util.ArrayList;
import java.util.List;


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
    private static IUtilsController utilsController;

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
        if(categoryController == null) {
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
        if(airportController == null){
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

    public static IUtilsController getUtilsController() {
        if (utilsController == null) {
            utilsController = new UtilsController(new ImageProcessor());
        }
        return utilsController;
    }
}
