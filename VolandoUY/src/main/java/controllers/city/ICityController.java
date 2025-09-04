package controllers.city;

import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;

import java.util.List;

public interface ICityController {
    BaseCityDTO createCity(BaseCityDTO baseCity);
    boolean cityExists(String cityName);

    boolean isAirportInCity(String name, String airportName);

    BaseCityDTO getCitySimpleDetailsByName(String cityName);
    CityDTO getCityDetailsByName(String cityName);

    List<String> getAllCities();
}
