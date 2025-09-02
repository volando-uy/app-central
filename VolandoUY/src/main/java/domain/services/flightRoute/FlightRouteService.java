package domain.services.flightRoute;

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
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import infra.repository.flightroute.FlightRouteRepository;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Setter
public class FlightRouteService implements IFlightRouteService {

    FlightRouteRepository flightRouteRepository;
    FlightRepository flightRepository;
    private ModelMapper modelMapper;
    private ICategoryService categoryService;
    private ICityService cityService;
    private IUserService userService;

    public FlightRouteService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userService = ServiceFactory.getUserService();
        this.categoryService = ServiceFactory.getCategoryService();
        this.cityService = ServiceFactory.getCityService();
        this.flightRouteRepository = new FlightRouteRepository();
        this.flightRepository = new FlightRepository();
    }

    @Override
    public List<FlightDTO> getFlightsByRouteName(String routeName) {
        FlightRoute flightRoute = getFlightRouteByName(routeName);
        System.out.println("getFlights de la ruta: " + routeName + ", vuelos encontrados: " + flightRoute.getFlights().size());
        System.out.println("getFlights de la ruta: " + routeName + ", vuelos encontrados: " + flightRoute.getFlights());
        return flightRoute.getFlights().stream()
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();
    }

    @Override
    public boolean existFlightRoute(String name) {
        return flightRouteRepository.existsByName(name);
    }

    @Override
    public List<FlightRouteDTO> getAllFlightRoutesDetailsByAirlineNickname(String airlineNickname) {
        return flightRouteRepository.getAllByAirlineNickname(airlineNickname).stream()
                .map(this::mapFlightRouteToDTO)
                .collect(Collectors.toList());
    }
    @Override
    public FlightRouteDTO createFlightRoute(FlightRouteDTO flightRouteDTO) {
        // 1) Existencia
        if (existFlightRoute(flightRouteDTO.getName())) {
            throw new UnsupportedOperationException(
                    String.format(ErrorMessages.ERR_FLIGHT_ROUTE_ALREADY_EXISTS, flightRouteDTO.getName()));
        }

        // 2) Relacionadas
        Airline airline = userService.getAirlineByNickname(flightRouteDTO.getAirlineNickname());

        City originCity = cityService.getCityByName(flightRouteDTO.getOriginCityName());
        if (originCity == null) throw new IllegalArgumentException("Ciudad de origen no encontrada: " + flightRouteDTO.getOriginCityName());

        City destinationCity = cityService.getCityByName(flightRouteDTO.getDestinationCityName());
        if (destinationCity == null) throw new IllegalArgumentException("Ciudad de destino no encontrada: " + flightRouteDTO.getDestinationCityName());

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

        // 3) Crear la ruta (sin vuelos todavía)
        FlightRoute flightRoute = new FlightRoute();
        flightRoute.setName(flightRouteDTO.getName());
        flightRoute.setDescription(flightRouteDTO.getDescription());
        flightRoute.setCreatedAt(flightRouteDTO.getCreatedAt());
        flightRoute.setPriceTouristClass(flightRouteDTO.getPriceTouristClass());
        flightRoute.setPriceBusinessClass(flightRouteDTO.getPriceBusinessClass());
        flightRoute.setPriceExtraUnitBaggage(flightRouteDTO.getPriceExtraUnitBaggage());
        flightRoute.setOriginCity(originCity);
        flightRoute.setDestinationCity(destinationCity);
        flightRoute.setAirline(airline);
        flightRoute.setCategories(categories);
        flightRoute.setFlights(new ArrayList<>()); // arranco vacío

        // Validar antes del primer save
        ValidatorUtil.validate(flightRoute);

        // Guardar/mergear ruta para tenerla managed
        flightRoute = flightRouteRepository.saveOrUpdate(flightRoute);

        // 4) Vincular vuelos existentes (lado dueño) y mergear cada uno
        List<Flight> attachedFlights = new ArrayList<>();
        if (flightRouteDTO.getFlightsNames() != null) {
            for (String fname : flightRouteDTO.getFlightsNames()) {
                Flight f = flightRepository.getFlightByName(fname);
                if (f == null) {
                    throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_NOT_FOUND, fname));
                }
                // seteo FK en el dueño
                f.setFlightRoute(flightRoute);
                f = flightRepository.saveOrUpdate(f); // MERGE
                attachedFlights.add(f);
            }
        }

        // 5) Reflejar lado inverso y mergear la ruta (consistencia en memoria)
        flightRoute.setFlights(attachedFlights);
        flightRoute = flightRouteRepository.saveOrUpdate(flightRoute);

        // 6) Mantener relación con aerolínea (en memoria)
        airline.getFlightRoutes().add(flightRoute);

        // 7) DTO de salida
        FlightRouteDTO resultDTO = new FlightRouteDTO();
        resultDTO.setName(flightRoute.getName());
        resultDTO.setDescription(flightRoute.getDescription());
        resultDTO.setCreatedAt(flightRoute.getCreatedAt());
        resultDTO.setPriceTouristClass(flightRoute.getPriceTouristClass());
        resultDTO.setPriceBusinessClass(flightRoute.getPriceBusinessClass());
        resultDTO.setPriceExtraUnitBaggage(flightRoute.getPriceExtraUnitBaggage());
        resultDTO.setOriginCityName(flightRoute.getOriginCity().getName());
        resultDTO.setDestinationCityName(flightRoute.getDestinationCity().getName());
        resultDTO.setAirlineNickname(flightRoute.getAirline().getNickname());
        resultDTO.setCategories(flightRoute.getCategories().stream().map(Category::getName).toList());
        resultDTO.setFlightsNames(flightRoute.getFlights().stream().map(Flight::getName).toList());
        return resultDTO;
    }

    @Override
    public FlightRoute getFlightRouteByName(String routeName) {
        FlightRoute flightRoute = flightRouteRepository.getByName(routeName);
        System.out.println("Buscando ruta de vuelo: " + routeName + ", encontrada: " + (flightRoute != null));
        if (flightRoute == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_ROUTE_NOT_FOUND, routeName));
        }
        return flightRoute;
    }

    @Override
    public FlightRouteDTO getFlightRouteDetailsByName(String routeName) {
        FlightRoute flightRoute = getFlightRouteByName(routeName);

        FlightRouteDTO flightRouteDTO = modelMapper.map(flightRoute, FlightRouteDTO.class);

        // Setear las categorías por nombre
        flightRouteDTO.setCategories(
                flightRoute.getCategories().stream()
                        .map(Category::getName)
                        .toList()
        );

        // Setear los nombres de las ciudades
        flightRouteDTO.setOriginCityName(flightRoute.getOriginCity().getName());
        flightRouteDTO.setDestinationCityName(flightRoute.getDestinationCity().getName());

        // Setear los nombres de los vuelos
        flightRouteDTO.setFlightsNames(
                flightRoute.getFlights().stream()
                        .map(Flight::getName)
                        .toList()
        );

        return flightRouteDTO;
    }

    private FlightRouteDTO mapFlightRouteToDTO(FlightRoute flightRoute) {
        FlightRouteDTO dto = modelMapper.map(flightRoute, FlightRouteDTO.class);
        dto.setCategories(flightRoute.getCategories().stream()
                .map(Category::getName)
                .toList());
        dto.setOriginCityName(flightRoute.getOriginCity().getName());
        dto.setDestinationCityName(flightRoute.getDestinationCity().getName());
        dto.setAirlineNickname(flightRoute.getAirline().getNickname());
        return dto;
    }

}


