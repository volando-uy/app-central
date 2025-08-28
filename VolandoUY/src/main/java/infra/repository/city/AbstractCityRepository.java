package infra.repository.city;

import domain.models.city.City;
import infra.repository.BaseRepository;

public abstract class AbstractCityRepository extends BaseRepository<City> {
    public AbstractCityRepository() {
        super(City.class);
    }
}
