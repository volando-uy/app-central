package factory;

import controllers.airport.AirportController;
import controllers.airport.IAirportController;
import controllers.category.CategoryController;
import controllers.category.ICategoryController;
import controllers.city.CityController;
import controllers.city.ICityController;
import controllers.flight.FlightController;
import controllers.flight.IFlightController;
import controllers.flightRoute.FlightRouteController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import controllers.user.UserController;
import controllers.flightRoutePackage.FlightRoutePackageController;
import controllers.flightRoutePackage.IFlightRoutePackageController;
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
import org.modelmapper.ModelMapper;



public class ControllerFactory {

    private static ModelMapper modelMapper;
    private static UserMapper userMapper;
    private static UserFactoryMapper userFactoryMapper;

    private static IUserController userController;
    private static IFlightRouteController flightRouteController;
    private static IFlightController flightController;
    private static ICategoryController categoryController;
    private static ICityController cityController;
    private static IFlightRoutePackageController packageController;
    private static IAirportController airportController;


    // ############ MODEL MAPPER & CUSTOM MAPPERS ############

    public static ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }

    public static UserMapper getUserMapper() {
        if (userMapper == null) {
            userMapper = new UserMapper(getModelMapper());
        }
        return userMapper;
    }

    public static UserFactoryMapper getUsuarioFactoryMapper() {
        if (userFactoryMapper == null) {
            userFactoryMapper = new UserFactoryMapper(getModelMapper());
        }
        return userFactoryMapper;
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


    // ############### CITY CONTROLLER & SERVICE #################

    public static ICityController getCityController() {
        if (cityController == null) {
            cityController = new CityController(ServiceFactory.getCityService());
        }
        return cityController;
    }

    // ############### AIRPORT CONTROLLER & SERVICE #################

    public static IAirportController getAirportController(){
        if(airportController == null){
            airportController = new AirportController(ServiceFactory.getAirportService());
        }
        return airportController;
    }

}
