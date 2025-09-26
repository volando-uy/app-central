package controllers.city;

import domain.dtos.city.BaseCityDTO;
import domain.dtos.city.CityDTO;
import domain.services.city.ICityService;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class CityController implements ICityController {
    private ICityService cityService;

    @Override
    public BaseCityDTO createCity(BaseCityDTO baseCity) {
        return this.cityService.createCity(baseCity);
    }



    @Override
    public boolean cityExists(String cityName) {
        return this.cityService.cityExists(cityName);
    }

    @Override
    public boolean isAirportInCity(String city, String airportName) {
        return this.cityService.isAirportInCity(city, airportName);
    }

    @Override
    public BaseCityDTO getCitySimpleDetailsByName(String cityName) {
        return this.cityService.getCityDetailsByName(cityName, false);
    }

    @Override
    public CityDTO getCityDetailsByName(String cityName) {
        return this.cityService.getCityDetailsByName(cityName, true);
    }

    @Override
    public List<String> getAllCitiesNames(){;
        return this.cityService.getAllCitiesNames();
    }
}
