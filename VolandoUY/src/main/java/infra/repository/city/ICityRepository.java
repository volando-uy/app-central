package infra.repository.city;

import domain.models.city.City;

public interface ICityRepository {
    City getCityByName(String name);
    boolean existsByName(String name);
    boolean existsAirportInCity(String cityName,String airportName);
}
