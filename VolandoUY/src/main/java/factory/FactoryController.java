package factory;

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

    private static IUserController userController;
    private static ModelMapper modelMapper;
    private static UserMapper userMapper;
    private static UserFactoryMapper userFactoryMapper;
    private static IUserService userService;

    private static IFlightRouteController flightRouteController;
    private static IFlightRouteService flightRouteService;

    private static IFlightController flightController;
    private static IFlightService flightService;

    private static ICategoryController categoryController;
    private static ICategoryService categoryService;

    private static ICityController cityController;
    private static ICityService cityService;

    private static IFlightRoutePackageController packageController;
    private static IFlightRoutePackageService packageService;


    // ############ USER CONTROLLER & SERVICE ############

    // Metodo para obtener el controlador de usuario, inicializándolo si es necesario
    public static IUserController getUserController() {
        if (userController == null) {
            userController = new UserController(getUserService());
        }
        return userController;
    }

    // Metodo para obtener el servicio de usuario, inicializándolo si es necesario
    public static IUserService getUserService() {
        if (userService == null) {
            userService = new UserService(getModelMapper(), getUserMapper());
        }
        return userService;
    }

    // #############################################################


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


    // ############ FLIGHT ROUTE CONTROLLER & SERVICE ############


    // Metodo para obtener el controlador de rutas de vuelo, inicializándolo si es necesario
    public static IFlightRouteController getFlightRouteController() {
        if (flightRouteController == null) {
            flightRouteController = new FlightRouteController(getFlightRouteService());
        }
        return flightRouteController;
    }

    // Metodo para obtener el servicio de rutas de vuelo, inicializándolo si es necesario
    public static IFlightRouteService getFlightRouteService() {
        if (flightRouteService == null) {
            flightRouteService = new FlightRouteService(getModelMapper(), getCategoryService(), getUserService(), getCityService());
        }
        return flightRouteService;
    }

    // ############### FLIGHT CONTROLLER & SERVICE #################

    public static ICategoryService getCategoryService() {
        if(categoryService == null) {
            categoryService = new CategoryService(getModelMapper());
        }
        return categoryService;
    }

    public static ICategoryController getCategoryController() {
        if(categoryController == null) {
            categoryController = new CategoryController(getCategoryService());
        }
        return categoryController;
    }
    // #############################################################

    // ############### FLIGHT CONTROLLER & SERVICE #################

    public static IFlightController getFlightController() {
        if (flightController == null) {
            flightController = new FlightController(getFlightService());
        }
        return flightController;
    }

    private static IFlightService getFlightService() {
        if (flightService == null) {
            flightService = new FlightService(getModelMapper());
        }
        return flightService;
    }


// ############### FLIGHT ROUTE PACKAGES CONTROLLER & SERVICE #################

    public static IFlightRoutePackageService getFlightRoutePackageService() {
        if (packageService == null) {
            packageService = new FlightRoutePackageService(getFlightRouteService(), getModelMapper());
        }
        return packageService;
    }

    public static IFlightRoutePackageController getFlightRoutePackageController() {
        if (packageController == null) {
            packageController = new FlightRoutePackageController(getFlightRoutePackageService());
        }
        return packageController;
    }

    // ############### CITY CONTROLLER & SERVICE #################

    public static ICityController getCityController() {
        if (cityController == null) {
            cityController = new CityController(getCityService());
        }
        return cityController;
    }

    public static ICityService getCityService() {
        if (cityService == null) {
            cityService = new CityService(getModelMapper());
        }
        return cityService;
    }

}
