package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.models.airport.Airport;
import domain.services.city.ICityService;

public interface IAirportService {
    AirportDTO createAirport(AirportDTO airportDTO, String cityName);
    // void removeAirport(String code);
    // void updateAirport(String code, CityDTO city);
    Airport getAirportByCode(String code);
    AirportDTO getAirportDetailsByCode(String code);
    boolean airportExists(String code);

    void setCityService(ICityService cityService);
}
