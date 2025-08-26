package infra.repository.flightroutepackage;

import domain.models.flightRoutePackage.FlightRoutePackage;
import infra.repository.BaseRepository;

public class AbstractFlightRoutePackageRepository extends BaseRepository<FlightRoutePackage> {
    public AbstractFlightRoutePackageRepository() {
        super(FlightRoutePackage.class);
    }
}
