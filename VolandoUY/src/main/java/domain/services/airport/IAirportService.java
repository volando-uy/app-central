package domain.services.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;
import domain.dtos.city.CityDTO;
import domain.models.airport.Airport;
import domain.services.city.ICityService;

import java.util.List;

public interface IAirportService {
    BaseAirportDTO createAirport(BaseAirportDTO airportDTO, String cityName);

    Airport getAirportByCode(String code, boolean full);

    AirportDTO getAirportDetailsByCode(String code, boolean full);

    boolean airportExists(String code);

    void setCityService(ICityService cityService);

    List<AirportDTO> getAllAirportsDetails(boolean full);
}
