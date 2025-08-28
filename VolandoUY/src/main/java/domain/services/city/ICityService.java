package domain.services.city;


import domain.dtos.city.CityDTO;
import domain.models.city.City;

import java.util.List;

/**
 *     private List<Airport> airports;
 *     private String name;
 *     private String country;
 *     private double latitude;
 *     private double longitude;
 *
 */
public interface ICityService {
    CityDTO createCity(CityDTO city);
    City getCityByName(String cityName);
    CityDTO getCityDetailsByName(String cityName);
    boolean cityExists(String cityName);
    boolean isAirportInCity(String cityName, String airportName);
    List<String> getAllCities();

}
