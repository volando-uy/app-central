package infra.repository.airport;

import domain.models.airport.Airport;
import domain.models.city.City;
import infra.repository.IBaseRepository;

public interface IAirportRepository extends IBaseRepository<Airport> {
    Airport getAirportByCode(String code);
    Airport getFullAirportByCode(String code);
    boolean existsAirportByCode(String code);
    void saveAirportAndAddToCity(Airport airport, City city);
}
