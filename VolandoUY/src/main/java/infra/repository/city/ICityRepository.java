package infra.repository.city;

import domain.models.city.City;
import infra.repository.IBaseRepository;

import java.util.List;

public interface ICityRepository extends IBaseRepository<City> {
    City getCityByName(String name);
    boolean existsByName(String name);
    boolean existsAirportInCity(String cityName,String airportName);
    City getFullCityByName(String cityName);
    List<City> findAll();
    List<City> findFullAll();
}
