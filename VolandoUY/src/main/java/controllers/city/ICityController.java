package controllers.city;

import domain.dtos.city.CityDTO;
public interface ICityController {
    CityDTO createCity(CityDTO city);
    void updateCity(CityDTO city);
    void deleteCity(String cityName);
    CityDTO getCity(String cityName);
    boolean cityExists(String cityName);
    void addAirportToCity(String cityName, String airportName, String airportCode);
    void removeAirportFromCity(String cityName, String airportName);
    void updateAirportInCity(String cityName, String airportName, String newAirportName,
                             String newAirportCode);
    CityDTO getCityWithAirports(String cityName);
    boolean isAirportInCity(String cityName, String airportName);
}
