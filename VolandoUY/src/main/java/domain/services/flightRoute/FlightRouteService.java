package domain.services.flightRoute;

import domain.dtos.category.CategoryDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.user.IUserService;
import domain.services.user.UserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import infra.repository.flightroute.FlightRouteRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class FlightRouteService implements IFlightRouteService {

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    FlightRouteRepository flightRouteRepository;
    FlightRepository flightRepository;
    private ICategoryService categoryService;
    private ICityService cityService;
    private IUserService userService;

    public FlightRouteService() {
        this.userService = ServiceFactory.getUserService();
        this.categoryService = ServiceFactory.getCategoryService();
        this.cityService = ServiceFactory.getCityService();
        this.flightRouteRepository = new FlightRouteRepository();
        this.flightRepository = new FlightRepository();
    }


    @Override
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO) {
        // Comprobar que la ruta de vuelo no exista
        if (existFlightRoute(flightRouteDTO.getName())) {
            throw new UnsupportedOperationException(
                    String.format(ErrorMessages.ERR_FLIGHT_ROUTE_ALREADY_EXISTS, flightRouteDTO.getName()));
        }

        // Obtener todos las entidades relacionadas
        // Tira throw si ya existe
        Airline airline = userService.getAirlineByNickname(flightRouteDTO.getAirlineNickname());
        City originCity = cityService.getCityByName(flightRouteDTO.getOriginCityName());
        City destinationCity = cityService.getCityByName(flightRouteDTO.getDestinationCityName());

        // Crear la lista de las categorias (si es que hay)
        List<Category> categories = new ArrayList<>();
        if (flightRouteDTO.getCategories() != null) {
            for (String categoryName : flightRouteDTO.getCategories()) {
                Category category = categoryService.getCategoryByName(categoryName);
                if (category == null) {
                    throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName));
                }
                categories.add(category);
            }
        }

        // Crear la nueva ruta de vuelo
        FlightRoute flightRoute = customModelMapper.map(flightRouteDTO, FlightRoute.class);
        flightRoute.setOriginCity(originCity);
        flightRoute.setDestinationCity(destinationCity);
        flightRoute.setAirline(airline);
        flightRoute.setCategories(categories);
        flightRoute.setFlights(new ArrayList<>());

        // Validar antes del primer save
        ValidatorUtil.validate(flightRoute);

        // Guardar la ruta de vuelo
        flightRouteRepository.save(flightRoute);

        // Añadir la ruta de vuelo a la aerolinea
        airline.getFlightRoutes().add(flightRoute);
        userService.updateAirline(airline);

        // Devolver el DTO mapeado
        return customModelMapper.mapFlightRoute(flightRoute);
    }

    @Override
    public boolean existFlightRoute(String name) {
        return flightRouteRepository.existsByName(name);
    }

    @Override
    public List<FlightRouteDTO> getFlightRoutesDetailsByAirlineNickname(String airlineNickname) {
        return flightRouteRepository.getAllByAirlineNickname(airlineNickname).stream()
                .map(customModelMapper::mapFlightRoute)
                .toList();
    }


    @Override
    public FlightRoute getFlightRouteByName(String routeName) {
        // Comprobar que la ruta de vuelo exista
        FlightRoute flightRoute = flightRouteRepository.getByName(routeName);
        if (flightRoute == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, routeName));
        }
        return flightRoute;
    }

    @Override
    public FlightRouteDTO getFlightRouteDetailsByName(String routeName) {
        // Comprobar que la ruta de vuelo exista
        // Tira throw si no existe
        FlightRoute flightRoute = this.getFlightRouteByName(routeName);

        return customModelMapper.mapFlightRoute(flightRoute);
    }
}


