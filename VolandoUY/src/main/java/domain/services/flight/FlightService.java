package domain.services.flight;

import domain.dtos.flight.FlightDTO;
import domain.models.flight.Flight;
import domain.models.user.Airline;
import domain.services.user.IUserService;
import factory.ControllerFactory;
import factory.ServiceFactory;
import infra.repository.flight.FlightRepository;
import org.modelmapper.ModelMapper;
import shared.constants.ErrorMessages;
import shared.utils.ValidatorUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class FlightService implements IFlightService {

    private final ModelMapper modelMapper;

    private IUserService userService;

    // Al sacar esto para el repo, hay que agregar
    // el @AllArgsConstructor y eliminar el constructor
    private FlightRepository flightRepository;

    // Constructor
    public FlightService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userService = ServiceFactory.getUserService();
        this.flightRepository = new FlightRepository();
    }

    @Override
    public FlightDTO createFlight(FlightDTO flightDTO) {

        Flight originalFlight = modelMapper.map(flightDTO, Flight.class);
        // Verificar si el vuelo ya existe
        if (_flightExists(originalFlight)) {
            throw new UnsupportedOperationException(String.format(ErrorMessages.ERR_FLIGHT_ALREADY_EXISTS, originalFlight.getName()));
        }
        ValidatorUtil.validate(originalFlight);

        // Mapear Airline desde nickname
        Airline airline = userService.getAirlineByNickname(flightDTO.getAirlineNickname());
        // No es necesario esta validacion porque si la aerolinea no existe, ya throwea en getAirlineByNickname
//        if (airline == null) {
//            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRLINE_NOT_FOUND, flightDTO.getAirlineNickname()));
//        }

        // Agregar el vuelo a la aerolínea y viceversa
        originalFlight.setAirline(airline);
        airline.getFlights().add(originalFlight);

        // Agregar el vuelo a la lista de vuelos
//        flights.add(originalFlight);
        flightRepository.save(originalFlight);
//        System.out.println(flights);

        // Mapear el DTO del vuelo creado
        FlightDTO createdFlightDTO = modelMapper.map(originalFlight, FlightDTO.class);
        createdFlightDTO.setAirlineNickname(flightDTO.getAirlineNickname());

        return createdFlightDTO;
    }


    @Override
    public List<FlightDTO> getAllFlights() {
//        return flights.stream()
//                .map(flight -> modelMapper.map(flight, FlightDTO.class))
//                .toList();
        return flightRepository.findAll().stream().map(flight -> modelMapper.map(flight, FlightDTO.class)).collect(Collectors.toList());
    }

    @Override
    public FlightDTO getFlightDetailsByName(String name) {
//        Flight flight = flights.stream()
//                .filter(f -> f.getName().equalsIgnoreCase(name))
//                .findFirst()
//                .orElse(null);
//        if (flight == null) {
//            return null;
//        }
//        FlightDTO flightDTO = modelMapper.map(flight, FlightDTO.class);
//        return flightDTO;
        Flight flight = getFlightByName(name);
        return modelMapper.map(flight, FlightDTO.class);
    }

    @Override
    public Flight getFlightByName(String flightName) {
//        return flights.stream()
//                .filter(flight -> flight.getName().equalsIgnoreCase(flightName))
//                .findFirst()
//                .orElseThrow(() -> new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_NOT_FOUND, flightName)));
        Flight flight = flightRepository.getFlightByName(flightName);
        if (flight == null) {
            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_FLIGHT_NOT_FOUND, flightName));
        }
        return flight;
    }

    @Override
    public List<FlightDTO> getAllFlightsByAirline(String airlineNickname) {
        Airline airline = userService.getAirlineByNickname(airlineNickname);
        //No es necesario esta validacion porque si la aerolinea no existe, ya throwea en getAirlineByNickname
//        if (airline == null) {
//            throw new IllegalArgumentException(String.format(ErrorMessages.ERR_AIRLINE_NOT_FOUND, airlineNickname));
//        }
//        return flights.stream()
//                .filter(flight -> flight.getAirline().equals(airline))
//                .map(flight -> modelMapper.map(flight, FlightDTO.class))
//                .toList();
        return flightRepository.getAllByAirlineNickname(airlineNickname).stream().map(flight -> modelMapper.map(flight, FlightDTO.class)).collect(Collectors.toList());
    }

    private boolean _flightExists(Flight flight) {
//        return flights.stream()
//                .anyMatch(existingFlight -> existingFlight.getName().equalsIgnoreCase(flight.getName()));
        return flightRepository.existsByName(flight.getName());
    }
    
}
