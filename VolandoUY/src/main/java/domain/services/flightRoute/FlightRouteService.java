package domain.services.flightRoute;

import domain.dtos.category.CategoryDTO;
import domain.dtos.city.CityDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.dtos.user.AirlineDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightRouteService implements IFlightRouteService {
    List<FlightRoute> flightRouteList;

    private ICategoryService categoryService;
    private ICityService cityService;
    private ModelMapper modelMapper;
    private IUserService userService;

    public FlightRouteService(ModelMapper modelMapper, ICategoryService categoryService, IUserService userService, ICityService cityService) {
        this.flightRouteList = new ArrayList<>();
        this.modelMapper = modelMapper;
        this.categoryService = categoryService;
        this.userService = userService;
        this.cityService = cityService;
    }

    @Override
    public boolean existFlightRoute(String name) {
        return this.flightRouteList.stream().anyMatch(flightRoute -> flightRoute.getName().equalsIgnoreCase(name));
    }

    @Override
    public List<FlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname) {
        return this.flightRouteList.stream()
                .filter(route -> route.getAirline().getNickname().equalsIgnoreCase(airlineNickname))
                .map(route -> {
                    // Mapear la ruta de vuelo a FlightRouteDTO
                    FlightRouteDTO flightRouteDTO = modelMapper.map(route, FlightRouteDTO.class);
                    // Setear las categorias
                    flightRouteDTO.setCategories(route.getCategories().stream()
                            .map(Category::getName)
                            .toList());
                    // Setear las ciudades de origen y destino
                    flightRouteDTO.setOriginCityName(route.getOriginCity().getName());
                    flightRouteDTO.setDestinationCityName(route.getDestinationCity().getName());
                    return flightRouteDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO) {
        // Crear la ruta de vuelo
        FlightRoute flightRoute = modelMapper.map(flightRouteDTO, FlightRoute.class);
        // Comprobar que la ruta de vuelo no exista
        if (existFlightRoute(flightRouteDTO.getName())) {
            throw new UnsupportedOperationException("Ya existe una ruta con el nombre: " + flightRouteDTO.getName());
        }
        ValidatorUtil.validate(flightRoute);

        // Tira throw si no existe
        Airline airline = userService.getAirlineByNickname(flightRouteDTO.getAirlineNickname());
        flightRoute.setAirline(airline);

        // Asignar las categorías a la ruta de vuelo
        flightRoute.setCategories(new ArrayList<>());
        if (flightRouteDTO.getCategories() != null) {
            flightRouteDTO.getCategories().forEach(categoryName -> {
                Category category = categoryService.getCategoryByName(categoryName);
                if (category != null) {
                    // Si existe, agregarla a la ruta de vuelo
                    flightRoute.getCategories().add(category);
                } else {
                    // Si no existe, lanzar una excepción
                    throw new IllegalArgumentException("La categoría " + categoryName + " no existe.");
                }
            });
        }

        // Añadir las ciudades de origen y destino
        City originCity = cityService.getCityByName(flightRouteDTO.getOriginCityName());
        City destinationCity = cityService.getCityByName(flightRouteDTO.getDestinationCityName());
        flightRoute.setOriginCity(originCity);
        flightRoute.setDestinationCity(destinationCity);

        // Inicializar la lista de vuelos
        flightRoute.setFlights(new ArrayList<>());

        // Agregar la ruta de vuelo a la lista de rutas de vuelo
        flightRouteList.add(flightRoute);

        // Añadir la ruta de vuelo a la aerolinea
        airline.getFlightRoutes().add(flightRoute);

        // Mapear la ruta de vuelo a FlightRouteDTO
        flightRouteDTO = modelMapper.map(flightRoute, FlightRouteDTO.class);
        // Setear la lista de nombres de categorias
        flightRouteDTO.setCategories(flightRoute.getCategories().stream()
                .map(Category::getName)
                .toList());
        // Setear los nombres de las ciudades de origen y destino
        flightRouteDTO.setOriginCityName(flightRoute.getOriginCity().getName());
        flightRouteDTO.setDestinationCityName(flightRoute.getDestinationCity().getName());

        return flightRouteDTO;
    }

    @Override
    public FlightRoute getFlightRouteByName(String routeName) {
        return this.flightRouteList.stream()
                .filter(route -> route.getName().equalsIgnoreCase(routeName))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, routeName)));
    }

    @Override
    public FlightRouteDTO getFlightRouteDetailsByName(String routeName) {
        return this.flightRouteList.stream()
                .filter(route -> route.getName().equalsIgnoreCase(routeName))
                .findFirst()
                .map(route -> {
                    // Mapear la ruta de vuelo a FlightRouteDTO
                    FlightRouteDTO flightRouteDTO = modelMapper.map(route, FlightRouteDTO.class);
                    // Setear las categorias
                    flightRouteDTO.setCategories(route.getCategories().stream()
                            .map(Category::getName)
                            .toList());
                    // Setear las ciudades de origen y destino
                    flightRouteDTO.setOriginCityName(route.getOriginCity().getName());
                    flightRouteDTO.setDestinationCityName(route.getDestinationCity().getName());
                    return flightRouteDTO;
                })
                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, routeName)));
    }
}
