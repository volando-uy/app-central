package domain.services.flight;

import domain.dtos.flight.FlightDTO;
import domain.models.flight.Flight;
import domain.models.flightRoute.FlightRoute;
import domain.models.user.Airline;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.CustomModelMapper;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
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

        // Validar el vuelo
        ValidatorUtil.validate(flight);

        // Obtener la aerolínea por su nickname
        // Tira throw si no existe.
        Airline airline = userService.getAirlineByNickname(flightDTO.getAirlineNickname());

        // Agregar el vuelo a la aerolínea y viceversa
        flight.setAirline(airline);
        airline.getFlights().add(flight);

        // Guardamos el vuelo y actualizamos la aerolínea
        flightRepository.save(flight);
        userService.updateAirline(airline);

        return customModelMapper.mapFlight(flight);
    }


    @Override
    public List<FlightDTO> getAllFlights() {
        return flightRepository.findAll().stream().map(customModelMapper::mapFlight).collect(Collectors.toList());
    }

    @Override
    public FlightDTO getFlightDetailsByName(String name) {
        // Comrpobamos que el vuelo existe
        // Tira throw si no existe
        Flight flight = this.getFlightByName(name);

        return customModelMapper.mapFlight(flight);
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
        return flightRepository.getFlightsByRouteName(routeName).stream().map(customModelMapper::mapFlight).toList();
    }

    @Override
    public List<FlightDTO> getAllFlightsByAirline(String airlineNickname) {
        // Comrpobamos que la aerolínea existe
        // Tira throw si no existe
        Airline airline = userService.getAirlineByNickname(airlineNickname);

        // Retornamos el DTO de todos los vuelos de la aerolinea
        return flightRepository.getAllByAirlineNickname(airlineNickname).stream().map(customModelMapper::mapFlight).toList();
    }

    private boolean _flightExists(Flight flight) {
        return flightRepository.existsByName(flight.getName());
    }
    
}
