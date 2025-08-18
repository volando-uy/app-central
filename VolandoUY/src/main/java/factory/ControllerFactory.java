package factory;

import controllers.flightRoute.FlightRouteController;
import controllers.flightRoute.IFlightRouteController;
import controllers.user.IUserController;
import controllers.user.UserController;
import domain.models.user.mapper.UserMapper;
import domain.services.flightRoute.FlightRouteService;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import org.modelmapper.ModelMapper;


public class ControllerFactory {

    private static IUserController usuarioController;
    private static ModelMapper modelMapper;
    private static UserMapper userMapper;
    private static UserFactoryMapper userFactoryMapper;
    private static IUserService usuarioService;

    private static IFlightRouteController flightRouteController;
    private static IFlightRouteService flightRouteService;


    // ############ USER CONTROLLER & SERVICE ############

    // Metodo para crear el controlador, se utiliza al inicio de la aplicación
    public static IUserController createUserController() {
        return new UserController(getUsuarioService());
    }

    // Overload que sirve para los tests, permitiendo inyectar mocks o instancias específicas
    public static IUserController createUserController(IUserService usuarioService) {
        return new UserController(usuarioService);
    }

    // Metodo para obtener el controlador de usuario, inicializándolo si es necesario
    public static IUserController getUsuarioController() {
        if (usuarioController == null) {
            usuarioController = createUserController();
        }
        return usuarioController;
    }

    // Metodo para obtener el servicio de usuario, inicializándolo si es necesario
    public static IUserService getUsuarioService() {
        if (usuarioService == null) {
            usuarioService = new UserService(getModelMapper(), getusuarioMapper());
        }
        return usuarioService;
    }

    // #############################################################


    // ############ MODEL MAPPER & CUSTOM MAPPERS ############

    public static ModelMapper getModelMapper() {
        if (modelMapper == null) {
            modelMapper = new ModelMapper();
        }
        return modelMapper;
    }

    public static UserMapper getusuarioMapper() {
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

    // Metodo para crear el controlador de rutas de vuelo, se utiliza al inicio de la aplicación
    public static IFlightRouteController createFlightRouteController() {
        return new FlightRouteController(getFlightRouteService());
    }

    // Overload que sirve para los tests, permitiendo inyectar mocks o instancias específicas
    public static IFlightRouteController createFlightRouteController(IFlightRouteService flightRouteService) {
        return new FlightRouteController(flightRouteService);
    }

    // Metodo para obtener el controlador de rutas de vuelo, inicializándolo si es necesario
    // Esto asegura que solo se cree una instancia del controlador
    public static IFlightRouteController getFlightRouteController() {
        if (flightRouteController == null) {
            flightRouteController = createFlightRouteController();
        }
        return flightRouteController;
    }

    // Metodo para obtener el servicio de rutas de vuelo, inicializándolo si es necesario
    public static IFlightRouteService getFlightRouteService() {
        if (flightRouteService == null) {
            flightRouteService = new FlightRouteService(getModelMapper());
        }
        return flightRouteService;
    }

    // #############################################################
}