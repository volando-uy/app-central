package controllers.city;

import domain.dtos.city.CityDTO;
public interface ICityController {
    CityDTO createCity(CityDTO city);
    CityDTO getCityByName(String cityName);
    boolean cityExists(String cityName);

    boolean isAirportInCity(String name, String airportName);

    CityDTO getCityDetailsByName(String name);
}
