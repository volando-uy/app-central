package infra.repository.flightroute;

import domain.models.flightroute.FlightRoute;
import infra.repository.BaseRepository;

public abstract class AbstractFlightRouteRepository extends BaseRepository<FlightRoute> {
    public AbstractFlightRouteRepository(Class<FlightRoute> entityClass) {
        super(entityClass);
    }
}
