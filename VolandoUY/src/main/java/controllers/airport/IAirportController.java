package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.models.city.City;

// controllers.airport.IAirportController
public interface IAirportController {
    AirportDTO createAirport(AirportDTO airportDTO, String cityName);
    AirportDTO getAirportByCode(String code);
    boolean airportExists(String code);
}
