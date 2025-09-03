package domain.services.flight;

import domain.dtos.flight.FlightDTO;
import domain.models.flight.Flight;
import domain.models.user.Airline;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.List;
import java.util.stream.Collectors;

public class FlightService implements IFlightService {

    private final CustomModelMapper customModelMapper = ControllerFactory.getCustomModelMapper();

    private IUserService userService;

    // Al sacar esto para el repo, hay que agregar
    // el @AllArgsConstructor y eliminar el constructor
    private FlightRepository flightRepository;

    // Constructor
    public FlightService() {
        this.userService = ServiceFactory.getUserService();
        this.flightRepository = new FlightRepository();
    }

    @Override
    public FlightDTO createFlight(FlightDTO flightDTO) {

        // Verificar si el vuelo ya existe
        Flight flight = customModelMapper.map(flightDTO, Flight.class);
        if (_flightExists(flight)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_FLIGHT_ALREADY_EXISTS, flight.getName()));
        }

        // TODO: Verificar que la ruta de vuelo pertenezca a la aerolinea

        // Validar el vuelo
        ValidatorUtil.validate(flight);

        // Obtener la aerolínea por su nickname
        // Tira throw si no existe.
        Airline airline = userService.getAirlineByNickname(flightDTO.getAirlineNickname(), true);

        flight.setAirline(airline);

        // Guardamos el vuelo y actualizamos la aerolínea
        flightRepository.saveFlightAndAddToAirline(flight, airline);

        return customModelMapper.mapFullFlight(flight);
    }


    @Override
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream().map(customModelMapper::mapFullFlight).collect(Collectors.toList());
    }

    @Override
    public FlightDTO getFlightDetailsByName(String name) {
        // Comrpobamos que el vuelo existe
        // Tira throw si no existe
        Flight flight = this.getFlightByName(name);

        return customModelMapper.mapFullFlight(flight);
    }

    @Override
    public Flight getFlightByName(String flightName) {
        // Comrpobamos que el vuelo existe
        Flight flight = flightRepository.getFlightByName(flightName);
        if (flight == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_NOT_FOUND, flightName));
        }

        return flight;
    }

    @Override
    public List<FlightDTO> getFlightsByRouteName(String routeName) {
        return flightRepository.getFlightsByRouteName(routeName).stream().map(customModelMapper::mapFullFlight).toList();
    }

    @Override
    public List<FlightDTO> getAllFlightsByRouteName(String flightRouteName) {
        return flightRepository.getFlightsByRouteName(flightRouteName).stream().map(customModelMapper::mapFullFlight).toList();
    }

    @Override
    public List<FlightDTO> getAllFlightsByAirline(String airlineNickname) {
        // Comrpobamos que la aerolínea existe
        // Tira throw si no existe
        Airline airline = userService.getAirlineByNickname(airlineNickname, false);

        // Retornamos el DTO de todos los vuelos de la aerolinea
        return flightRepository.getAllByAirlineNickname(airlineNickname).stream().map(customModelMapper::mapFullFlight).toList();
    }

    private boolean _flightExists(Flight flight) {
        return flightRepository.existsByName(flight.getName());
    }
    
}
