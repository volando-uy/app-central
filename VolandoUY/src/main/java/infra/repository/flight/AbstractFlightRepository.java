package infra.repository.flight;

import domain.models.flight.Flight;
import infra.repository.BaseRepository;

public class AbstractFlightRepository extends BaseRepository<Flight> {
    public AbstractFlightRepository() {
        super(Flight.class);
    }
}
