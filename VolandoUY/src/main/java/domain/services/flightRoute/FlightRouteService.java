package domain.services.flightRoute;

import domain.dtos.flightRoute.BaseFlightRouteDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.category.Category;
import domain.models.city.City;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.services.category.ICategoryService;
import domain.services.city.ICityService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import infra.repository.flightroute.FlightRouteRepository;
import lombok.Setter;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;

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
    public BaseFlightRouteDTO createFlightRoute(
            BaseFlightRouteDTO baseFlightRouteDTO,
            String originCityName,
            String destinationCityName,
            String airlineNickname,
            List<String> categoriesNames
    ) {
        // Comprobar que la ruta de vuelo no exista
        if (existFlightRoute(baseFlightRouteDTO.getName())) {
            throw new UnsupportedOperationException(
                    String.format(ErrorMessages.ERR_FLIGHT_ROUTE_ALREADY_EXISTS, baseFlightRouteDTO.getName()));
        }

        // Obtener todos las entidades relacionadas
        // Tira throw si ya existe
        Airline airline = userService.getAirlineByNickname(airlineNickname, true);
        City originCity = cityService.getCityByName(originCityName);
        City destinationCity = cityService.getCityByName(destinationCityName);

        // Crear la lista de las categorias (si es que hay)
        List<Category> categories = new ArrayList<>();
        if (categoriesNames != null) {
            for (String categoryName : categoriesNames) {
                Category category = categoryService.getCategoryByName(categoryName);
                if (category == null) {
                    throw new IllegalArgumentException(String.format(ErrorMessages.ERR_CATEGORY_NOT_FOUND, categoryName));
                }
                categories.add(category);
            }
        }

        // Crear la nueva ruta de vuelo
        FlightRoute flightRoute = customModelMapper.map(baseFlightRouteDTO, FlightRoute.class);
        flightRoute.setOriginCity(originCity);
        flightRoute.setDestinationCity(destinationCity);
        flightRoute.setAirline(airline);
        flightRoute.setCategories(categories);
        flightRoute.setFlights(new ArrayList<>());

        // Validar antes del primer save
        ValidatorUtil.validate(flightRoute);

        // Guardar la ruta de vuelo
        flightRouteRepository.saveFlightRouteAndAddToAirline(flightRoute, airline);

        // Devolver el DTO mapeado
        return customModelMapper.map(flightRoute, BaseFlightRouteDTO.class);
    }

    @Override
    public boolean existFlightRoute(String name) {
        return flightRouteRepository.existsByName(name);
    }

    @Override
    public List<FlightRouteDTO> getFlightRoutesDetailsByAirlineNickname(String airlineNickname, boolean full) {
        return flightRouteRepository.getFullAllByAirlineNickname(airlineNickname).stream()
                .map(fr -> full ? customModelMapper.mapFullFlightRoute(fr) : customModelMapper.map(fr, FlightRouteDTO.class))
                .toList();
    }


    @Override
    public FlightRoute getFlightRouteByName(String routeName, boolean full) {
        // Comprobar que la ruta de vuelo exista
        FlightRoute flightRoute = full ? flightRouteRepository.getFullByName(routeName) : flightRouteRepository.getByName(routeName);
        if (flightRoute == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, routeName));
        }
        return flightRoute;
    }

    @Override
    public FlightRouteDTO getFlightRouteDetailsByName(String routeName, boolean full) {
        // Comprobar que la ruta de vuelo exista
        // Tira throw si no existe
        FlightRoute flightRoute = this.getFlightRouteByName(routeName, full);

        if (full){
            return customModelMapper.mapFullFlightRoute(flightRoute);
        } else {
            return customModelMapper.map(flightRoute, FlightRouteDTO.class);
        }
    }
}


