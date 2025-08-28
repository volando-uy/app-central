package infra.repository.airport;

import domain.models.airport.Airport;
import infra.repository.BaseRepository;
import infra.repository.category.AbstractCategoryRepository;

public class AirportAbstractRepository extends BaseRepository<Airport> {
    public AirportAbstractRepository() {
        super(Airport.class);
    }
}
