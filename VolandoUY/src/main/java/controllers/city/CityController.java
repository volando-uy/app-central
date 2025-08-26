package controllers.city;

import domain.dtos.city.CityDTO;
import domain.services.city.ICityService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CityController implements ICityController {
    private ICityService cityService;

    @Override
    public CityDTO createCity(CityDTO city) {
        return this.cityService.createCity(city);
    }

    @Override
    public CityDTO getCityByName(String cityName) {
        return this.cityService.getCityDetailsByName(cityName);
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
    public CityDTO getCityDetailsByName(String name) {
        return this.cityService.getCityDetailsByName(name);
    }
}
