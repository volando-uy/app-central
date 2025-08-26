package infra.repository.airport;

import domain.models.airport.Airport;

public interface IAirportRepository {
    Airport getAirportByCode(String code);

    boolean existsAirportByCode(String code);
}
