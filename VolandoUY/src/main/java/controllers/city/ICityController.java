package controllers.city;

import domain.dtos.city.CityDTO;

import java.util.List;

public interface ICityController {
    CityDTO createCity(CityDTO city);
    CityDTO getCityByName(String cityName);
    boolean cityExists(String cityName);
    List<String> getAllCities();
}
