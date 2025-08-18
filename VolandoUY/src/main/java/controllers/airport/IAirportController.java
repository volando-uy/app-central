package controllers.airport;

import domain.dtos.airport.AirportDTO;
import domain.dtos.city.CityDTO;
import domain.models.city.City;

// controllers.airport.IAirportController
public interface IAirportController {
    void addAirport(CityDTO city, String name, String code);
    void removeAirport(String code);
    void updateAirport(String code, CityDTO city);
    AirportDTO getAirportByCode(String code);
    AirportDTO getAirportByName(String name);
    boolean airportExists(String code);
}
