package domain.services.flight;

import domain.dtos.flight.FlightDTO;
import domain.models.flight.Flight;
import domain.models.user.Airline;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class FlightService implements IFlightService {

    private final ModelMapper modelMapper;

    // Al sacar esto para el repo, hay que agregar
    // el @AllArgsConstructor y eliminar el constructor
    private List<Flight> flights = new ArrayList<>();

    private List<Airline> airlines = new ArrayList<>();

    // Constructor
    public FlightService(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Override
    public FlightDTO createFlight(FlightDTO flight) {
        Flight originalFlight = modelMapper.map(flight, Flight.class);
        if (_flightExists(originalFlight)) {
            throw new UnsupportedOperationException("Flight already exists: " + originalFlight.getName());
        }
        flights.add(originalFlight);
        return flight;
    }

    @Override
    public List<FlightDTO> getAllFlights() {
        return flights.stream()
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();
    }

    @Override
    public FlightDTO getFlightByName(String name) {
        Flight flight = flights.stream()
                .filter(f -> f.getName().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
        if (flight == null) {
            return null;
        }
        return modelMapper.map(flight, FlightDTO.class);
    }

    @Override
    public List<FlightDTO> getAllFlightsByAirline(String airlineNickname) {
        Airline airline = airlines.stream()
                .filter(a -> a.getNickname().equalsIgnoreCase(airlineNickname))
                .findFirst()
                .orElse(null);
        if (airline == null) {
            throw new IllegalArgumentException("Airline not found: " + airlineNickname);
        }
        return flights.stream()
                .filter(flight -> flight.getAirline().equals(airline))
                .map(flight -> modelMapper.map(flight, FlightDTO.class))
                .toList();
    }

    private boolean _flightExists(Flight flight) {
        return flights.contains(flight);
    }


}
