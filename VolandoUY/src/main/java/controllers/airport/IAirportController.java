package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.airport.BaseAirportDTO;

import java.util.List;

public interface IAirportController {
    BaseAirportDTO createAirport(BaseAirportDTO baseAirportDTO, String cityName);

    BaseAirportDTO getAirportSimpleDetailsByCode(String code);
    AirportDTO getAirportDetailsByCode(String code);

    boolean airportExists(String code);

    List<BaseAirportDTO> getAllAirportsSimpleDetails();

    List<AirportDTO> getAllAirportsDetails();
}
