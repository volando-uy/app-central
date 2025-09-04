package domain.services.flight;

import domain.dtos.flight.BaseFlightDTO;
import domain.dtos.flight.FlightDTO;
import domain.dtos.flightRoute.FlightRouteDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.services.flightRoute.IFlightRouteService;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import lombok.AllArgsConstructor;
import lombok.Setter;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService implements IFlightService {

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    @Setter
    private IUserService userService;

    @Setter
    private IFlightRouteService flightRouteService;

    // Al sacar esto para el repo, hay que agregar
    // el @AllArgsConstructor y eliminar el constructor
    private final FlightRepository flightRepository;

    // Constructor
    public FlightService() {
        this.flightRepository = new FlightRepository();
    }

    @Override
    public BaseFlightDTO createFlight(BaseFlightDTO baseFlightDTO, String airlineNickname, String flightRouteName) {

        // Verificar si el vuelo ya existe
        Flight flight = customModelMapper.map(baseFlightDTO, Flight.class);
        if (_flightExists(flight)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_FLIGHT_ALREADY_EXISTS, flight.getName()));
        }

        // TODO: Verificar que la ruta de vuelo pertenezca a la aerolinea

        // Validar el vuelo
        ValidatorUtil.validate(flight);

        // Obtener la aerolínea por su nickname
        // Tira throw si no existe.
        Airline airline = userService.getAirlineByNickname(airlineNickname, true);

        // Obtener la ruta de vuelo por su nombre
        // Tira throw si no existe.
        FlightRoute flightRoute = flightRouteService.getFlightRouteByName(flightRouteName, true);

        flight.setAirline(airline);

        // Guardamos el vuelo y actualizamos la aerolínea
        flightRepository.saveFlightAndAddToAirline(flight, airline);

        return customModelMapper.map(flight, BaseFlightDTO.class);
    }


    @Override
    public List<FlightDTO> getAllFlights(boolean full) {
        return flightRepository.findAll().stream()
                .map(fl -> full ? customModelMapper.mapFullFlight(fl) : customModelMapper.map(fl, FlightDTO.class))
                .toList();
    }

    @Override
    public FlightDTO getFlightDetailsByName(String name, boolean full) {
        // Comprobamos que el vuelo existe
        // Tira throw si no existe
        Flight flight = this.getFlightByName(name, full);

        return full ? customModelMapper.mapFullFlight(flight) : customModelMapper.map(flight, FlightDTO.class);
    }

    @Override
    public Flight getFlightByName(String flightName, boolean full) {
        // Comprobamos que el vuelo existe
        Flight flight = full ? flightRepository.getFullFlightByName(flightName) : flightRepository.getFlightByName(flightName);
        if (flight == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_NOT_FOUND, flightName));
        }
        return flight;
    }

    @Override
    public List<FlightDTO> getAllFlightsByRouteName(String flightRouteName, boolean full) {
        return flightRepository.getFlightsByRouteName(flightRouteName).stream()
                .map(fl -> full ? customModelMapper.mapFullFlight(fl) : customModelMapper.map(fl, FlightDTO.class))
                .toList();
    }

    @Override
    public List<FlightDTO> getAllFlightsByAirlineNickname(String airlineNickname, boolean full) {
        // Comrpobamos que la aerolínea existe
        // Tira throw si no existe
        userService.getAirlineByNickname(airlineNickname, false);

        // Retornamos el DTO de todos los vuelos de la aerolinea
        return flightRepository.getAllByAirlineNickname(airlineNickname).stream()
                .map(fl -> full ? customModelMapper.mapFullFlight(fl) : customModelMapper.map(fl, FlightDTO.class))
                .toList();
    }

    private boolean _flightExists(Flight flight) {
        return flightRepository.existsByName(flight.getName());
    }
    
}
