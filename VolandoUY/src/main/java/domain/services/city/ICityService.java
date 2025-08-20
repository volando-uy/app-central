package domain.services.city;


import domain.dtos.city.CityDTO;

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
